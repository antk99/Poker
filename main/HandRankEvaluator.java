package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import utils.Utils;

/**
 * This class will be used to evaluate the rank of a poker hand.
 * It will determine the best 5-card hand from a list of 7 cards,
 * and return the Hand object that represents the best possible Hand.
 */
public class HandRankEvaluator {

    private final List<Card> cards; // list of 7 cards - 2 player cards + 5 community cards

    private final HashMap<Rank, Integer> rankCounts; // number of times each rank appears in the hand
    private final HashMap<Suit, Integer> suitCounts; // number of times each suit appears in the hand

    private boolean hasFlush;
    private int maxNOfAKind; // max number of cards with the same rank

    public HandRankEvaluator(ArrayList<Card> cards) {
        ArrayList<Card> cardsCopy = new ArrayList<>(cards);
        cardsCopy.sort(Card.sortByRankComparatorDESC);
        this.cards = Collections.unmodifiableList(cardsCopy);

        this.rankCounts = getRankCounts(this.cards);
        // get max value in rankCounts
        this.maxNOfAKind = this.rankCounts.values().stream().max(Integer::compare).get();

        this.suitCounts = getSuitCounts(this.cards);
        // get max value in suitCounts
        this.hasFlush = this.suitCounts.values().stream().max(Integer::compare).get() >= 5;
    }

    /**
     * Evaluates the hand rank of a list of 7 cards. Sets the Hand on the player.
     */
    public Hand evaluate() {

        // checks in order of highest to lowest hand rank
        Hand hand = getRoyalStraightFlush();
        if (hand != null)
            return hand;
        hand = getFourOfAKind();
        if (hand != null)
            return hand;
        hand = getFullHouse();
        if (hand != null)
            return hand;
        hand = getFlush();
        if (hand != null)
            return hand;
        hand = getStraight();
        if (hand != null)
            return hand;
        hand = getThreeOfAKind();
        if (hand != null)
            return hand;
        hand = getTwoPair();
        if (hand != null)
            return hand;
        hand = getPair();
        if (hand != null)
            return hand;
        hand = getHighCard();
        return hand;
    }

    /**
     * Generates a HashMap that contains the number of times each rank appears in
     * the list of cards.
     * 
     * @param allCards list of 7 cards: 2 player cards + 5 community cards
     * @return HashMap of Rank, Integer
     */
    private static HashMap<Rank, Integer> getRankCounts(List<Card> allCards) {
        HashMap<Rank, Integer> rankCounts = new HashMap<>();
        for (Card card : allCards) {
            if (rankCounts.containsKey(card.getRank())) {
                rankCounts.put(card.getRank(), rankCounts.get(card.getRank()) + 1);
            } else {
                rankCounts.put(card.getRank(), 1);
            }
        }
        return rankCounts;
    }

    /**
     * Generates a HashMap that contains the number of times each suit appears in
     * the list of cards.
     * 
     * @param allCards list of 7 cards: 2 player cards + 5 community cards
     * @return HashMap of Suit, Integer
     */
    private static HashMap<Suit, Integer> getSuitCounts(List<Card> allCards) {
        HashMap<Suit, Integer> suitCounts = new HashMap<>();
        for (Card card : allCards) {
            if (suitCounts.containsKey(card.getSuit())) {
                suitCounts.put(card.getSuit(), suitCounts.get(card.getSuit()) + 1);
            } else {
                suitCounts.put(card.getSuit(), 1);
            }
        }
        return suitCounts;
    }

    /**
     * Checks if the hand has either a royal flush or a straight flush.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the royal/straight flush, or null if the hand does
     *         not have a royal/straight flush.
     */
    private Hand getRoyalStraightFlush() {
        if (!this.hasFlush) {
            return null;
        }

        // get the cards that are part of the flush in DESC order
        ArrayList<Card> flushCards = new ArrayList<>();
        for (Card card : this.cards) {
            if (this.suitCounts.get(card.getSuit()) >= 5) {
                flushCards.add(card);
            }
        }

        // check if the flush cards form a straight
        ArrayList<Card> straightFlushCards = get5ConsecutiveCards(flushCards);
        if (straightFlushCards != null) {
            Rank[] lowestHighestRank = Utils.getLowestHighestRank(straightFlushCards);
            Rank lowestRank = lowestHighestRank[0];
            Rank highestRank = lowestHighestRank[1];
            // differentiate between a straight flush and a royal flush
            if (highestRank == Rank.ACE && lowestRank == Rank.TEN) {
                return new Hand(HandRank.ROYAL_FLUSH, straightFlushCards, getStraightScore(straightFlushCards));
            } else {
                return new Hand(HandRank.STRAIGHT_FLUSH, straightFlushCards, getStraightScore(straightFlushCards));
            }
        }

        return null;
    }

    /**
     * Gets the score for a straight by getting the highest rank in the straight.
     * 
     * @pre the cards form a straight
     * @param cards list of 5 cards that form a straight
     * @return score for the straight
     */
    private static int getStraightScore(ArrayList<Card> cards) {
        Rank[] lowestHighestRank = Utils.getLowestHighestRank(cards);
        Rank lowestRank = lowestHighestRank[0];
        Rank highestRank = lowestHighestRank[1];

        int score = highestRank.ordinal();

        // check if the straight is a wheel (A2345)
        if (highestRank == Rank.ACE && lowestRank == Rank.TWO) {
            score = Rank.FIVE.ordinal();
        }
        return score;
    }

    /**
     * Checks if the hand has a four of a kind.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best 4-of-a-kind, or null if the hand does not
     *         have a 4-of-a-kind.
     */
    private Hand getFourOfAKind() {
        if (this.maxNOfAKind != 4) {
            return null;
        }

        ArrayList<Card> best5Cards = new ArrayList<>();
        int numKickers = 1;
        Rank fourOfAKindRank = null;
        Rank kickerRank = null;
        for (Card card : this.cards) {
            if (this.rankCounts.get(card.getRank()) == 4) {
                best5Cards.add(card);
                fourOfAKindRank = card.getRank();
            } else if (numKickers > 0) {
                best5Cards.add(card);
                kickerRank = card.getRank();
                numKickers--;
            }
        }

        return new Hand(HandRank.FOUR_OF_A_KIND, best5Cards, getFourOfAKindScore(fourOfAKindRank, kickerRank));
    }

    /**
     * Gets the score for a four of a kind by multiplying the rank of the four of a
     * kind by 100 and adding the rank of the kicker.
     * 
     * @param fourOfAKindRank rank of the four of a kind
     * @param kickerRank      rank of the kicker
     * @return score for the four of a kind
     */
    private static int getFourOfAKindScore(Rank fourOfAKindRank, Rank kickerRank) {
        int score = fourOfAKindRank.ordinal() * 100 + kickerRank.ordinal();
        return score;
    }

    /**
     * Checks if the hand has a full house.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best full house, or null if the hand does not
     *         have a full house.
     */
    private Hand getFullHouse() {
        if (this.maxNOfAKind < 3) {
            return null;
        }

        // get the highest rank that appears 3 times
        Rank threeOfAKindRank = null;
        for (Card card : this.cards) {
            if (this.rankCounts.get(card.getRank()) >= 3) {
                threeOfAKindRank = card.getRank();
                break;
            }
        }

        // get the highest rank that appears at least 2 times and is not the same as
        // the three of a kind rank
        Rank twoOfAKindRank = null;
        for (Card card : this.cards) {
            if (card.getRank() != threeOfAKindRank && this.rankCounts.get(card.getRank()) >= 2) {
                twoOfAKindRank = card.getRank();
                break;
            }
        }

        if (twoOfAKindRank == null) {
            return null;
        }

        // get the best 5 cards that are part of the full house
        ArrayList<Card> best5Cards = new ArrayList<>();
        int numThreeOfAKind = 3;
        int numTwoOfAKind = 2;
        for (Card card : this.cards) {
            if (best5Cards.size() == 5) {
                break;
            }

            if (card.getRank() == threeOfAKindRank && numThreeOfAKind > 0) {
                best5Cards.add(card);
                numThreeOfAKind--;
            } else if (card.getRank() == twoOfAKindRank && numTwoOfAKind > 0) {
                best5Cards.add(card);
                numTwoOfAKind--;
            }
        }

        return new Hand(HandRank.FULL_HOUSE, best5Cards, getFullHouseScore(threeOfAKindRank, twoOfAKindRank));
    }

    /**
     * Gets the score for a full house by multiplying the rank of the three of a
     * kind by 100 and adding the rank of the two of a kind.
     * 
     * @param threeOfAKindRank rank of the three of a kind
     * @param twoOfAKindRank   rank of the two of a kind
     * @return score for the full house
     */
    private static int getFullHouseScore(Rank threeOfAKindRank, Rank twoOfAKindRank) {
        int score = threeOfAKindRank.ordinal() * 100 + twoOfAKindRank.ordinal();
        return score;
    }

    /**
     * Checks if the hand has a flush.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best flush, or null if the hand does not have a
     *         flush.
     */
    private Hand getFlush() {
        if (!this.hasFlush) {
            return null;
        }

        ArrayList<Card> best5Cards = new ArrayList<>();
        for (Card card : this.cards) {
            if (best5Cards.size() == 5) {
                break;
            }

            if (this.suitCounts.get(card.getSuit()) >= 5) {
                best5Cards.add(card);
            }
        }

        return new Hand(HandRank.FLUSH, best5Cards, getFlushScore(best5Cards));
    }

    /**
     * Gets the score for a flush by multiplying the rank of each card by 100 raised
     * to the power of the card's index in the hand (sorted by rank ASC).
     * 
     * @pre the cards form a flush
     * @param cards cards in the flush
     * @return score for the flush
     */
    private static int getFlushScore(ArrayList<Card> cards) {
        cards.sort(Card.sortByRankComparatorASC);
        int score = 0;
        for (int i = 0; i < cards.size(); i++) {
            score += cards.get(i).getRank().ordinal() * Math.pow(100, i);
        }
        return score;
    }

    /**
     * Checks if the hand has a straight.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best straight, or null if the hand does not have
     *         a straight.
     */
    private Hand getStraight() {
        ArrayList<Card> best5Cards = get5ConsecutiveCards(this.cards);
        if (best5Cards != null) {
            return new Hand(HandRank.STRAIGHT, best5Cards, getStraightScore(best5Cards));
        }

        return null;
    }

    /**
     * Checks if the hand has a three of a kind.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best three of a kind, or null if the hand does
     *         not have a three of a kind.
     */
    private Hand getThreeOfAKind() {
        if (this.maxNOfAKind != 3) {
            return null;
        }

        // get the highest rank that appears 3 times
        Rank threeOfAKindRank = null;
        for (Card card : this.cards) {
            if (this.rankCounts.get(card.getRank()) >= 3) {
                threeOfAKindRank = card.getRank();
                break;
            }
        }

        // get the best 5 cards that are part of the three of a kind
        ArrayList<Card> best5Cards = new ArrayList<>();
        int numThreeOfAKind = 3;
        int numKickers = 2;
        for (Card card : this.cards) {
            if (best5Cards.size() == 5) {
                break;
            }

            if (card.getRank() == threeOfAKindRank && numThreeOfAKind > 0) {
                best5Cards.add(card);
                numThreeOfAKind--;
            } else if (card.getRank() != threeOfAKindRank && numKickers > 0) {
                best5Cards.add(card);
                numKickers--;
            }
        }

        return new Hand(HandRank.THREE_OF_A_KIND, best5Cards, getThreeOfAKindScore(threeOfAKindRank, best5Cards));
    }

    /**
     * Gets the score for a three of a kind by multiplying the rank of the three of
     * a kind by 100^2 and adding the rank of the kickers weighted by their ASC
     * position.
     * 
     * @pre the cards form a three of a kind w/ 2 kickers
     * @param threeOfAKindRank rank of the three of a kind
     * @param best5Cards       best 5 cards in the hand
     * @return score for the three of a kind
     */
    private static int getThreeOfAKindScore(Rank threeOfAKindRank, ArrayList<Card> best5Cards) {
        best5Cards.sort(Card.sortByRankComparatorASC);
        int score = threeOfAKindRank.ordinal() * (int) Math.pow(100, 2);
        for (int i = 0, j = 0; i < best5Cards.size(); i++) {
            if (best5Cards.get(i).getRank() != threeOfAKindRank) {
                score += best5Cards.get(i).getRank().ordinal() * Math.pow(100, j);
                j++;
            }
        }
        return score;
    }

    /**
     * Checks if the hand has two pair.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best two pair, or null if the hand does not have
     *         two pair.
     */
    private Hand getTwoPair() {
        if (this.maxNOfAKind != 2) {
            return null;
        }

        // check if there are at least 2 pairs
        int numPairs = 0;
        for (Rank rank : this.rankCounts.keySet()) {
            if (this.rankCounts.get(rank) == 2) {
                numPairs++;
            }
        }
        if (numPairs < 2) {
            return null;
        }

        // get the highest 2 pairs and the best kicker
        ArrayList<Card> best5Cards = new ArrayList<>();
        Rank primaryPairRank = null;
        Rank secondaryPairRank = null;
        Rank kickerRank = null;
        for (Card card : this.cards) {
            if (best5Cards.size() == 4) {
                best5Cards.add(card);
                if (kickerRank == null)
                    kickerRank = card.getRank();
                break;
            }

            if (this.rankCounts.get(card.getRank()) == 2) {
                best5Cards.add(card);
                Rank currRank = card.getRank();

                if (primaryPairRank == null)
                    primaryPairRank = currRank;
                else if (secondaryPairRank == null && currRank != primaryPairRank)
                    secondaryPairRank = currRank;

            } else if (kickerRank == null) {
                best5Cards.add(card);
                kickerRank = card.getRank();
            }
        }

        return new Hand(HandRank.TWO_PAIR, best5Cards, getTwoPairScore(primaryPairRank, secondaryPairRank, kickerRank));
    }

    /**
     * Gets the score for a two pair by multiplying the rank of the primary pair by
     * 100^2, the rank of the secondary pair by 100^1, and the rank of the kicker by
     * 100^0.
     * 
     * @pre the cards form a two pair w/ 1 kicker
     * @param primaryPairRank   rank of the primary pair (higher rank)
     * @param secondaryPairRank rank of the secondary pair (lower rank)
     * @param kickerRank        rank of the kicker
     * @return score for the two pair
     */
    private static int getTwoPairScore(Rank primaryPairRank, Rank secondaryPairRank, Rank kickerRank) {
        int score = primaryPairRank.ordinal() * (int) Math.pow(100, 2);
        score += secondaryPairRank.ordinal() * (int) Math.pow(100, 1);
        score += kickerRank.ordinal() * (int) Math.pow(100, 0);
        return score;
    }

    /**
     * Checks if the hand has a pair.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best pair, or null if the hand does not have a
     *         pair.
     */
    private Hand getPair() {
        if (this.maxNOfAKind != 2) {
            return null;
        }

        // get the highest rank that appears 2 times
        Rank pairRank = null;
        for (Card card : this.cards) {
            if (this.rankCounts.get(card.getRank()) == 2) {
                pairRank = card.getRank();
                break;
            }
        }

        // get the pair and the three highest kickers
        ArrayList<Card> best5Cards = new ArrayList<>();
        int numPair = 2;
        int numKickers = 3;
        for (Card card : this.cards) {
            if (best5Cards.size() == 5) {
                break;
            }

            if (card.getRank() == pairRank && numPair > 0) {
                best5Cards.add(card);
                numPair--;
            } else if (numKickers > 0) {
                best5Cards.add(card);
                numKickers--;
            }
        }

        return new Hand(HandRank.PAIR, best5Cards, getPairScore(pairRank, best5Cards));
    }

    /**
     * Gets the score for a pair by multiplying the rank of the pair by 100^3 and
     * adding the rank of the kickers weighted by their ASC position.
     * 
     * @param pairRank   rank of the pair
     * @param best5Cards best 5 cards in the hand (pair + 3 kickers)
     * @return score for the pair
     */
    private static int getPairScore(Rank pairRank, ArrayList<Card> best5Cards) {
        best5Cards.sort(Card.sortByRankComparatorASC);
        int score = pairRank.ordinal() * (int) Math.pow(100, 3);
        for (int i = 0, j = 0; i < best5Cards.size(); i++) {
            if (best5Cards.get(i).getRank() != pairRank) {
                score += best5Cards.get(i).getRank().ordinal() * Math.pow(100, j);
                j++;
            }
        }
        return score;
    }

    /**
     * Checks if the hand has a high card.
     * 
     * @pre this.cards is sorted in DESCENDING order
     * @return Hand containing the best high card.
     */
    private Hand getHighCard() {
        ArrayList<Card> best5Cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            best5Cards.add(this.cards.get(i));
        }
        return new Hand(HandRank.HIGH_CARD, best5Cards, getHighCardScore(best5Cards));
    }

    /**
     * Gets the score for a high card by adding the rank of the cards weighted by
     * their ASC position.
     * 
     * @param best5Cards best 5 cards in the hand
     * @return score for the high card
     */
    private int getHighCardScore(ArrayList<Card> best5Cards) {
        best5Cards.sort(Card.sortByRankComparatorASC);
        int score = 0;
        for (int i = 0; i < best5Cards.size(); i++) {
            score += best5Cards.get(i).getRank().ordinal() * Math.pow(100, i);
        }
        return score;
    }

    /**
     * Returns the highest 5 consecutive cards from the given list of cards.
     *
     * @param cards the list of cards to check
     * @return the highest 5 consecutive cards from the given list of cards, or null
     *         if no 5 consecutive cards exist
     * @throws IllegalArgumentException if the given list of cards is not sorted by
     *                                  rank in descending order
     */
    private static ArrayList<Card> get5ConsecutiveCards(List<Card> cards) {
        // check if cards are sorted by rank in descending order
        for (int i = 0; i < cards.size() - 1; i++) {
            Card currCard = cards.get(i);
            Card nextCard = cards.get(i + 1);
            if (currCard.getRank().ordinal() < nextCard.getRank().ordinal()) {
                throw new IllegalArgumentException("Cards must be sorted by rank in descending order");
            }
        }

        ArrayList<Card> cardsCopy = new ArrayList<>(cards);
        ArrayList<Card> best5Cards = new ArrayList<>();
        int numConsecutive = 0;
        for (int i = 0; i < cardsCopy.size() - 1; i++) {
            Card currCard = cardsCopy.get(i);
            Card nextCard = cardsCopy.get(i + 1);
            if (currCard.getRank().ordinal() == nextCard.getRank().ordinal() + 1) {
                numConsecutive++;
                best5Cards.add(currCard);
                if (numConsecutive == 4) {
                    best5Cards.add(nextCard);
                    return best5Cards;
                }
            } else if (currCard.getRank() == nextCard.getRank()) {
                // do nothing
            } else {
                numConsecutive = 0;
                best5Cards.clear();
            }
        }

        // No 5 consecutive cards found, check for wheel straight

        Rank[] wheelStraightRanks = {
                Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE
        };

        ArrayList<Card> wheelStraight = new ArrayList<>();
        HashSet<Rank> ranks = new HashSet<>();
        for (Card card : cardsCopy) {
            for (Rank rank : wheelStraightRanks) {
                if (card.getRank() == rank && !ranks.contains(rank)) {
                    wheelStraight.add(card);
                    ranks.add(rank);
                    break;
                }
            }
        }
        if (wheelStraight.size() == wheelStraightRanks.length) {
            return wheelStraight;
        }

        // Neither straight nor wheel straight found
        return null;
    }

}
