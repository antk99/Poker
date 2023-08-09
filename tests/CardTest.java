package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Constructor;

import org.junit.Test;

import main.Card;
import main.Rank;
import main.Suit;

public class CardTest {

    @Test
    public void testCard() {
        Card c1 = Card.get(Rank.ACE, Suit.CLUBS);
        Card c2 = Card.get(Rank.ACE, Suit.CLUBS);

        assertEquals(c1, c2);
        assertSame(c1, c2);
    }

    @Test
    public void testCard_Constructor() throws Exception {
        Constructor<?> cardConstructor = Card.class.getDeclaredConstructors()[0];
        cardConstructor.setAccessible(true);

        Card c1 = Card.get(Rank.ACE, Suit.CLUBS);
        Card c2 = (Card) cardConstructor.newInstance(Rank.ACE, Suit.CLUBS);

        assertNotEquals(c1, c2);
        assertNotSame(c1, c2);
    }
}
