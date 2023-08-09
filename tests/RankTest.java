package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.Rank;

public class RankTest {

    @Test
    public void testRank_Two() {
        assertEquals(Rank.TWO.ordinal(), 0);
    }

    @Test
    public void testRank_Three() {
        assertEquals(Rank.THREE.ordinal(), 1);
    }

    @Test
    public void testRank_Four() {
        assertEquals(Rank.FOUR.ordinal(), 2);
    }

    @Test
    public void testRank_Five() {
        assertEquals(Rank.FIVE.ordinal(), 3);
    }

    @Test
    public void testRank_Six() {
        assertEquals(Rank.SIX.ordinal(), 4);
    }

    @Test
    public void testRank_Seven() {
        assertEquals(Rank.SEVEN.ordinal(), 5);
    }

    @Test
    public void testRank_Eight() {
        assertEquals(Rank.EIGHT.ordinal(), 6);
    }

    @Test
    public void testRank_Nine() {
        assertEquals(Rank.NINE.ordinal(), 7);
    }

    @Test
    public void testRank_Ten() {
        assertEquals(Rank.TEN.ordinal(), 8);
    }

    @Test
    public void testRank_Jack() {
        assertEquals(Rank.JACK.ordinal(), 9);
    }

    @Test
    public void testRank_Queen() {
        assertEquals(Rank.QUEEN.ordinal(), 10);
    }

    @Test
    public void testRank_King() {
        assertEquals(Rank.KING.ordinal(), 11);
    }

    @Test
    public void testRank_Ace() {
        assertEquals(Rank.ACE.ordinal(), 12);
    }

}
