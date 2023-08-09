package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.Suit;

public class SuitTest {

    @Test
    public void testSuit_Spades() {
        assertEquals(Suit.SPADES.ordinal(), 0);
    }

    @Test
    public void testSuit_Hearts() {
        assertEquals(Suit.HEARTS.ordinal(), 1);
    }

    @Test
    public void testSuit_Clubs() {
        assertEquals(Suit.CLUBS.ordinal(), 2);
    }

    @Test
    public void testSuit_Diamonds() {
        assertEquals(Suit.DIAMONDS.ordinal(), 3);
    }
}
