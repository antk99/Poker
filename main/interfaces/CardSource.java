package main.interfaces;

import main.Card;

/**
 * CardSource interface. Represents a source of cards.
 */
public interface CardSource {
    /**
     * Draws a card from the source. The card is removed from the source & returned.
     * 
     * @return Card object drawn from the source.
     * @throws IllegalStateException If there are no cards left in the source.
     */
    public Card draw() throws IllegalStateException;

    /**
     * Returns the number of cards left in the source.
     * 
     * @return Number of cards left in the source.
     */
    public int cardsLeft();

    /**
     * Resets the source to its initial state.
     */
    public void reset();
}
