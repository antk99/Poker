package main;

import java.util.Comparator;

/**
 * Card class. Represents a playing card. The Card class follows the Flyweight
 * pattern to ensure that only one instance of each card is created. The Card
 * class is immutable. Card equality corresponds to Object equality since there
 * is only one instance of each card.
 */
public class Card {
    // Static store of all cards that have been created.
    private static final Card[][] CARD_STORE = new Card[Suit.values().length][Rank.values().length];
    static {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                CARD_STORE[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
            }
        }
    }

    private Rank rank = null;
    private Suit suit = null;

    /**
     * Private constructor to prevent instantiation of Card objects. Use the static
     * get() method to get a Card object for a given rank and suit.
     * 
     * @param rank
     * @param suit
     */
    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Follows the Flyweight pattern to ensure that only one instance of each card
     * is created. Cards are created at the time of the class's initialization and
     * stored in the CARD_STORE. This method returns the card from the CARD_STORE
     * that corresponds to the given rank and suit.
     * 
     * @param rank Rank of the card
     * @param suit Suit of the card
     * @return Card object for the given rank and suit.
     */
    public static Card get(Rank rank, Suit suit) {
        return CARD_STORE[suit.ordinal()][rank.ordinal()];
    }

    @Override
    public String toString() {
        return this.rank.toString() + this.suit.toString();
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    // Comparator for sorting cards by rank in ascending order
    public static final Comparator<Card> sortByRankComparatorASC = new Comparator<Card>() {
        @Override
        public int compare(Card c1, Card c2) {
            if (c1.getRank() != c2.getRank())
                return c1.getRank().ordinal() - c2.getRank().ordinal();
            else
                return c1.getSuit().ordinal() - c2.getSuit().ordinal();
        }
    };

    // Comparator for sorting cards by rank in descending order
    public static final Comparator<Card> sortByRankComparatorDESC = new Comparator<Card>() {
        @Override
        public int compare(Card c1, Card c2) {
            if (c1.getRank() != c2.getRank())
                return c2.getRank().ordinal() - c1.getRank().ordinal();
            else
                return c1.getSuit().ordinal() - c2.getSuit().ordinal();
        }
    };

    // Comparator for sorting cards by suit
    public static final Comparator<Card> sortBySuitComparator = new Comparator<Card>() {
        @Override
        public int compare(Card c1, Card c2) {
            if (c1.getSuit() != c2.getSuit())
                return c1.getSuit().ordinal() - c2.getSuit().ordinal();
            else
                return c1.getRank().ordinal() - c2.getRank().ordinal();
        }
    };
}
