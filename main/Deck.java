package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Deck class. Represents a deck of cards. The deck is initialized with 52 cards
 * and is shuffled. The deck can be sorted by rank or suit. The deck can be
 * drawn from.
 */
public class Deck {

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

    private ArrayList<Card> cards = new ArrayList<>();

    /**
     * Constructor for Deck. Creates a new deck with 52 cards and shuffles it.
     */
    public Deck() {
        this.initDeckAndShuffle();
    }

    /**
     * Initializes the deck with 52 cards and shuffles it.
     */
    private void initDeckAndShuffle() {
        this.cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(Card.get(rank, suit));
            }
        }
        Collections.shuffle(this.cards);
    }

    /**
     * Draws the top card from the deck. Assumes the deck is not empty.
     * 
     * @throws IllegalStateException if the deck is empty
     * @return Card object
     */
    public Card draw() {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return this.cards.remove(0);
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }
}
