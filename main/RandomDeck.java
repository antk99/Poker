package main;

import java.util.Collections;

/**
 * RandomDeck class. Represents a deck of cards. The deck is initialized with 52
 * cards and is shuffled. The deck can be drawn from.
 */
public class RandomDeck extends OrderedDeck {

    public RandomDeck() {
        super();
    }

    @Override
    public void reset() {
        super.reset();
        Collections.shuffle(this.cards);
    }
}
