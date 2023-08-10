package main;

import java.util.ArrayList;

import main.interfaces.CardSource;

/**
 * Poker dealer that deals cards, and determines the winner.
 */
public class Dealer {

    private final CardSource cardSource;
    private final ArrayList<Card> communityCards = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();

    /**
     * Constructor for Dealer.
     */
    public Dealer(CardSource cardSource) {
        this.cardSource = cardSource;
    }

    /**
     * Constructor for Dealer that also takes in a list of players.
     */
    public Dealer(CardSource cardSource, ArrayList<Player> players) {
        this.cardSource = cardSource;
    }

    /**
     * Returns the list of players at the table.
     * 
     * @return List of players at the table
     */
    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    /**
     * Returns the list of community cards.
     * 
     * @return List of community cards
     */
    public ArrayList<Card> getCommunityCards() {
        return new ArrayList<Card>(communityCards);
    }

    /**
     * Adds a player to the list of players.
     *
     * @param player Player to add
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Deals 2 cards to all players.
     */
    public void deal() {
        for (Player player : this.players) {
            player.dealCards(this.cardSource.draw(), this.cardSource.draw());
        }
    }

    /**
     * Shuffles the deck and clears the community cards.
     */
    public void shuffle() {
        this.cardSource.reset();
        this.communityCards.clear();
    }

    /**
     * Deals n community cards.
     * 
     * @param n Number of community cards to deal
     * @return List of community cards
     */
    private ArrayList<Card> dealCommunityCards(int n) {
        for (int i = 0; i < n; i++) {
            this.communityCards.add(this.cardSource.draw());
        }
        return new ArrayList<Card>(communityCards);
    }

    /**
     * Deals the flop.
     *
     * @return List of community cards
     */
    public ArrayList<Card> dealFlop() {
        return dealCommunityCards(3);
    }

    /**
     * Deals the turn.
     *
     * @return List of community cards
     */
    public ArrayList<Card> dealTurn() {
        return dealCommunityCards(1);
    }

    /**
     * Deals the river.
     *
     * @return List of community cards
     */
    public ArrayList<Card> dealRiver() {
        return dealCommunityCards(1);
    }

    /**
     * Determines the winner of the hand.
     *
     * @return List of players that won
     */
    public ArrayList<Player> determineWinner() {
        ArrayList<Player> winners = new ArrayList<>();
        HandRank winningHandRank = HandRank.HIGH_CARD;

        // Determine the winning hand rank & the winner(s)
        for (Player player : this.players) {
            ArrayList<Card> allCards = new ArrayList<>(this.communityCards);
            allCards.addAll(player.getCards());

            HandRankEvaluator evaluator = new HandRankEvaluator(allCards);
            Hand playerHand = evaluator.evaluate();
            player.setHand(playerHand);

            // If the player's hand rank is better than the current winning hand rank,
            // then the player is the new winner
            if (playerHand.getHandRank().ordinal() > winningHandRank.ordinal()) {
                winners.clear();
                winners.add(player);
                winningHandRank = playerHand.getHandRank();
            }
            // If the player's hand rank is the same as the current winning hand rank,
            // then the player is potentially a winner --> need to compare the hands
            else if (playerHand.getHandRank() == winningHandRank) {
                winners.add(player);
            }
        }

        return breakTie(winners);
    }

    /**
     * Breaks a tie between players who have the same hand rank.
     * 
     * @param players List of players who have the same hand rank
     * @return List of players who won
     */
    private static ArrayList<Player> breakTie(ArrayList<Player> players) {
        if (players.size() == 1) {
            return players;
        }

        // Multiple players have the same hand rank --> need to break the tie
        // based on the Hand's score
        int maxScore = 0;
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            int currScore = player.getHand().getScore();
            if (currScore > maxScore) {
                winners.clear();
                winners.add(player);
                maxScore = currScore;
            } else if (currScore == maxScore) {
                winners.add(player);
            }
        }
        return winners;
    }

    /**
     * Returns a string representation of the dealer.
     *
     * @return String representation of the dealer
     */
    @Override
    public String toString() {
        return "Dealer{" +
                "deck=" + this.cardSource +
                ", players=" + this.players +
                '}';
    }

}
