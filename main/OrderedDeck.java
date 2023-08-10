package main;

import java.util.ArrayList;

import main.interfaces.CardSource;

/**
 * OrderedDeck class. Represents a deck of cards. The deck is initialized with
 * 52 cards and is sorted by rank and suit. The deck can be drawn from.
 */
public class OrderedDeck implements CardSource {

    protected final ArrayList<Card> cards = new ArrayList<>();

    public OrderedDeck() {
        this.reset();
    }

    @Override
    public Card draw() throws IllegalStateException {
        if (this.cards.isEmpty())
            throw new IllegalStateException("Cannot draw from an empty deck");

        return this.cards.remove(this.cards.size() - 1); // remove & return the last card
    }

    @Override
    public int cardsLeft() {
        return this.cards.size();
    }

    @Override
    public void reset() {
        this.cards.clear();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(Card.get(rank, suit));
            }
        }
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }
}
