package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.HandRank;

public class HandRankTest {

    @Test
    public void testHandRank_HighCard() {
        assertEquals(HandRank.HIGH_CARD.ordinal(), 0);
    }

    @Test
    public void testHandRank_Pair() {
        assertEquals(HandRank.PAIR.ordinal(), 1);
    }

    @Test
    public void testHandRank_TwoPair() {
        assertEquals(HandRank.TWO_PAIR.ordinal(), 2);
    }

    @Test
    public void testHandRank_ThreeOfAKind() {
        assertEquals(HandRank.THREE_OF_A_KIND.ordinal(), 3);
    }

    @Test
    public void testHandRank_Straight() {
        assertEquals(HandRank.STRAIGHT.ordinal(), 4);
    }

    @Test
    public void testHandRank_Flush() {
        assertEquals(HandRank.FLUSH.ordinal(), 5);
    }

    @Test
    public void testHandRank_FullHouse() {
        assertEquals(HandRank.FULL_HOUSE.ordinal(), 6);
    }

    @Test
    public void testHandRank_FourOfAKind() {
        assertEquals(HandRank.FOUR_OF_A_KIND.ordinal(), 7);
    }

    @Test
    public void testHandRank_StraightFlush() {
        assertEquals(HandRank.STRAIGHT_FLUSH.ordinal(), 8);
    }

    @Test
    public void testHandRank_RoyalFlush() {
        assertEquals(HandRank.ROYAL_FLUSH.ordinal(), 9);
    }

    @Test
    public void testHandRank_CompareTo() {
        assertEquals(HandRank.HIGH_CARD.compareTo(HandRank.HIGH_CARD), 0);
        assertTrue(HandRank.HIGH_CARD.compareTo(HandRank.PAIR) < 0);
        assertTrue(HandRank.PAIR.compareTo(HandRank.HIGH_CARD) > 0);
    }

}
