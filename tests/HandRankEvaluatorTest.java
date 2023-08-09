package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Card;
import main.Hand;
import main.HandRank;
import main.HandRankEvaluator;
import main.Rank;
import main.Suit;

public class HandRankEvaluatorTest {

        private ArrayList<Card> cards;
        private static Method getRoyalStraightFlush;
        private static Method getFourOfAKind;
        private static Method getFullHouse;
        private static Method getFlush;
        private static Method getStraight;
        private static Method getThreeOfAKind;
        private static Method getTwoPair;
        private static Method getPair;
        private static Method getHighCard;

        @BeforeClass
        public static void setupClass() throws Exception {
                getRoyalStraightFlush = HandRankEvaluator.class.getDeclaredMethod("getRoyalStraightFlush");
                getRoyalStraightFlush.setAccessible(true);

                getFourOfAKind = HandRankEvaluator.class.getDeclaredMethod("getFourOfAKind");
                getFourOfAKind.setAccessible(true);

                getFullHouse = HandRankEvaluator.class.getDeclaredMethod("getFullHouse");
                getFullHouse.setAccessible(true);

                getFlush = HandRankEvaluator.class.getDeclaredMethod("getFlush");
                getFlush.setAccessible(true);

                getStraight = HandRankEvaluator.class.getDeclaredMethod("getStraight");
                getStraight.setAccessible(true);

                getThreeOfAKind = HandRankEvaluator.class.getDeclaredMethod("getThreeOfAKind");
                getThreeOfAKind.setAccessible(true);

                getTwoPair = HandRankEvaluator.class.getDeclaredMethod("getTwoPair");
                getTwoPair.setAccessible(true);

                getPair = HandRankEvaluator.class.getDeclaredMethod("getPair");
                getPair.setAccessible(true);

                getHighCard = HandRankEvaluator.class.getDeclaredMethod("getHighCard");
                getHighCard.setAccessible(true);
        }

        @Before
        public void setup() {
                this.cards = new ArrayList<>();
        }

        @Test
        public void testRoyalFlush_Valid() throws Exception {
                Suit suit = Suit.CLUBS;

                // 2D, 9C, 10C, JC, QC, KC, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, suit),
                                Card.get(Rank.NINE, suit),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.JACK, suit),
                                Card.get(Rank.KING, suit),
                                Card.get(Rank.QUEEN, suit),
                                Card.get(Rank.TEN, suit)
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.ACE, suit),
                                Card.get(Rank.KING, suit),
                                Card.get(Rank.QUEEN, suit),
                                Card.get(Rank.JACK, suit),
                                Card.get(Rank.TEN, suit)
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getRoyalStraightFlush.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.ROYAL_FLUSH, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testRoyalFlush_Invalid() throws Exception {
                Suit suit = Suit.CLUBS;

                // AD, KD, QD, JD, 10C, QC, JC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.DIAMONDS),
                                Card.get(Rank.JACK, Suit.DIAMONDS),
                                Card.get(Rank.JACK, suit),
                                Card.get(Rank.QUEEN, suit),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.TEN, suit)
                };
                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getRoyalStraightFlush.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testStraightFlush_Valid() throws Exception {
                Suit suit = Suit.CLUBS;

                // 8C, 9C, 10C, JC, QC, KD, AD
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.DIAMONDS),
                                Card.get(Rank.QUEEN, suit),
                                Card.get(Rank.JACK, suit),
                                Card.get(Rank.TEN, suit),
                                Card.get(Rank.NINE, suit),
                                Card.get(Rank.EIGHT, suit)
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.EIGHT, suit),
                                Card.get(Rank.NINE, suit),
                                Card.get(Rank.TEN, suit),
                                Card.get(Rank.JACK, suit),
                                Card.get(Rank.QUEEN, suit)
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getRoyalStraightFlush.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.STRAIGHT_FLUSH, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testStraightFlush_ValidWheelStraight() throws Exception {
                Suit suit = Suit.CLUBS;

                // AC, 2C, 3C, 4C, 5C, 6D, 7D
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, suit),
                                Card.get(Rank.TWO, suit),
                                Card.get(Rank.THREE, suit),
                                Card.get(Rank.FOUR, suit),
                                Card.get(Rank.FIVE, suit),
                                Card.get(Rank.SIX, Suit.DIAMONDS),
                                Card.get(Rank.SEVEN, Suit.DIAMONDS)
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.ACE, suit),
                                Card.get(Rank.TWO, suit),
                                Card.get(Rank.THREE, suit),
                                Card.get(Rank.FOUR, suit),
                                Card.get(Rank.FIVE, suit)
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getRoyalStraightFlush.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.STRAIGHT_FLUSH, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testStraightFlush_ValidNotWheel() throws Exception {
                Suit suit = Suit.CLUBS;

                // AC, 2C, 3C, 4C, 5C, 6C, 7D
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, suit),
                                Card.get(Rank.TWO, suit),
                                Card.get(Rank.THREE, suit),
                                Card.get(Rank.FOUR, suit),
                                Card.get(Rank.FIVE, suit),
                                Card.get(Rank.SIX, suit),
                                Card.get(Rank.SEVEN, Suit.DIAMONDS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.TWO, suit),
                                Card.get(Rank.THREE, suit),
                                Card.get(Rank.FOUR, suit),
                                Card.get(Rank.FIVE, suit),
                                Card.get(Rank.SIX, suit),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getRoyalStraightFlush.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.STRAIGHT_FLUSH, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testStraightFlush_Invalid() throws Exception {
                Suit suit = Suit.CLUBS;

                // 8D, 9C, 10C, JC, QC, KD, AD
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.DIAMONDS),
                                Card.get(Rank.QUEEN, suit),
                                Card.get(Rank.JACK, suit),
                                Card.get(Rank.TEN, suit),
                                Card.get(Rank.NINE, suit),
                                Card.get(Rank.EIGHT, Suit.DIAMONDS)
                };
                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getRoyalStraightFlush.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testFourOfAKind_Valid() throws Exception {
                // AD, AS, AH, AC, 8S, 9S, 10S
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.TEN, Suit.SPADES),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.HEARTS),
                                Card.get(Rank.EIGHT, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.HEARTS),
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.TEN, Suit.SPADES),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getFourOfAKind.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.FOUR_OF_A_KIND, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testFourOfAKind_Invalid() throws Exception {
                // AD, AS, AH, 10D, 10H, 10S, 2C
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.TEN, Suit.DIAMONDS),
                                Card.get(Rank.TEN, Suit.HEARTS),
                                Card.get(Rank.ACE, Suit.HEARTS),
                                Card.get(Rank.TEN, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.CLUBS),
                };
                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getFourOfAKind.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testFullHouse_Valid() throws Exception {
                // 2D, 2S, 2H, 10S, 10H, 10C, 9S
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.TEN, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.SPADES),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.HEARTS),
                                Card.get(Rank.TEN, Suit.HEARTS),
                                Card.get(Rank.TEN, Suit.CLUBS),
                };

                // Expected best 5-card hand
                Card[] oracle = new Card[] {
                                Card.get(Rank.TWO, Suit.HEARTS),
                                Card.get(Rank.TWO, Suit.SPADES),
                                Card.get(Rank.TEN, Suit.HEARTS),
                                Card.get(Rank.TEN, Suit.SPADES),
                                Card.get(Rank.TEN, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getFullHouse.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.FULL_HOUSE, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testFullHouse_Invalid() throws Exception {
                // AC, AS, AD, 2C, KC, QC, 10C
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.CLUBS),
                                Card.get(Rank.TEN, Suit.CLUBS),
                };
                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getFullHouse.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testFlush_Valid() throws Exception {
                // AC, 3C, KC, 5S, 7C, 10C, QC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.CLUBS),
                                Card.get(Rank.FIVE, Suit.SPADES),
                                Card.get(Rank.SEVEN, Suit.CLUBS),
                                Card.get(Rank.TEN, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.CLUBS),
                                Card.get(Rank.TEN, Suit.CLUBS),
                                Card.get(Rank.SEVEN, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getFlush.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.FLUSH, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testFlush_Invalid() throws Exception {
                // AC, 3C, KC, 5S, 7S, 10S, QS
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.CLUBS),
                                Card.get(Rank.FIVE, Suit.SPADES),
                                Card.get(Rank.SEVEN, Suit.SPADES),
                                Card.get(Rank.TEN, Suit.SPADES),
                                Card.get(Rank.QUEEN, Suit.SPADES),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getFlush.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testStraight_Valid() throws Exception {
                // 8C, 9C, 10C, JD, QC, KC, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.EIGHT, Suit.CLUBS),
                                Card.get(Rank.NINE, Suit.CLUBS),
                                Card.get(Rank.TEN, Suit.CLUBS),
                                Card.get(Rank.JACK, Suit.DIAMONDS),
                                Card.get(Rank.QUEEN, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.CLUBS),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.CLUBS),
                                Card.get(Rank.JACK, Suit.DIAMONDS),
                                Card.get(Rank.TEN, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getStraight.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.STRAIGHT, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testStraight_ValidNotWheel() throws Exception {
                // AC, 2D, 3S, 4H, 5C, 6D, KS
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.FOUR, Suit.HEARTS),
                                Card.get(Rank.FIVE, Suit.CLUBS),
                                Card.get(Rank.SIX, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.SIX, Suit.DIAMONDS),
                                Card.get(Rank.FIVE, Suit.CLUBS),
                                Card.get(Rank.FOUR, Suit.HEARTS),
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getStraight.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.STRAIGHT, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testStraight_ValidWheelStraight() throws Exception {
                // AS, 2D, 3S, 4S, 5S, JC, KD
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.FOUR, Suit.SPADES),
                                Card.get(Rank.FIVE, Suit.SPADES),
                                Card.get(Rank.JACK, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.DIAMONDS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.FIVE, Suit.SPADES),
                                Card.get(Rank.FOUR, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.SPADES),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getStraight.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.STRAIGHT, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testStraight_Invalid() throws Exception {
                // 2C, 3C, 4C, 5C, 7C, 8D, 9D
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.FOUR, Suit.CLUBS),
                                Card.get(Rank.FIVE, Suit.CLUBS),
                                Card.get(Rank.SEVEN, Suit.CLUBS),
                                Card.get(Rank.EIGHT, Suit.DIAMONDS),
                                Card.get(Rank.NINE, Suit.DIAMONDS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getStraight.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testStraight_InvalidWheel() throws Exception {
                // AS, 2D, 3D, 4D, 6C, 6H, KC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.DIAMONDS),
                                Card.get(Rank.FOUR, Suit.DIAMONDS),
                                Card.get(Rank.SIX, Suit.CLUBS),
                                Card.get(Rank.SIX, Suit.HEARTS),
                                Card.get(Rank.KING, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getStraight.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testThreeOfAKind_Valid() throws Exception {
                // 2C, 2D, KS, 3C, 3D, 3S, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.THREE, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.SPADES),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getThreeOfAKind.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.THREE_OF_A_KIND, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testThreeOfAKind_Invalid() throws Exception {
                // AC, AD, 2C, 2D, 3S, 3D, KH
                Card[] initialCards = new Card[] {
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.ACE, Suit.DIAMONDS),
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.HEARTS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getThreeOfAKind.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testTwoPair_Valid() throws Exception {
                // 2C, 2D, KS, 3C, 3D, AS, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.THREE, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.THREE, Suit.DIAMONDS),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getTwoPair.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.TWO_PAIR, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testTwoPair_Valid2() throws Exception {
                // 2C, 2D, KS, 3C, QD, AS, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getTwoPair.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.TWO_PAIR, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testTwoPair_Invalid() throws Exception {
                // 2C, 2D, KS, 3C, QD, 9S, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getTwoPair.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testPair_Valid() throws Exception {
                // 2C, 2D, KS, 3C, QD, 9S, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.TWO, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getPair.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.PAIR, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        @Test
        public void testPair_Invalid() throws Exception {
                // 2C, 5D, KS, 3C, QD, 9S, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.FIVE, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getPair.invoke(evaluator);

                assertNull(hand);
        }

        @Test
        public void testHighCard_Valid() throws Exception {
                // 2C, 5D, KS, 3C, QD, 9S, AC
                Card[] initialCards = new Card[] {
                                Card.get(Rank.TWO, Suit.CLUBS),
                                Card.get(Rank.FIVE, Suit.DIAMONDS),
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.THREE, Suit.CLUBS),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.ACE, Suit.CLUBS),
                };

                Card[] oracle = new Card[] {
                                Card.get(Rank.KING, Suit.SPADES),
                                Card.get(Rank.QUEEN, Suit.DIAMONDS),
                                Card.get(Rank.ACE, Suit.CLUBS),
                                Card.get(Rank.NINE, Suit.SPADES),
                                Card.get(Rank.FIVE, Suit.DIAMONDS),
                };

                HandRankEvaluator evaluator = getHandRankEvaluator(initialCards);
                Hand hand = (Hand) getHighCard.invoke(evaluator);

                assertNotNull(hand);
                assertEquals(HandRank.HIGH_CARD, hand.getHandRank());
                assertEquals(buildHashet(oracle), buildHashet(hand.getBestHand()));
        }

        private HandRankEvaluator getHandRankEvaluator(Card[] initialCards) throws Exception {
                this.populateCards(initialCards);
                HandRankEvaluator evaluator = new HandRankEvaluator(new ArrayList<Card>(this.cards));

                return evaluator;
        }

        private void populateCards(Card[] cards) {
                this.cards.addAll(Arrays.asList(cards));
                Collections.shuffle(this.cards);
        }

        private static HashSet<Card> buildHashet(List<Card> cards) {
                return new HashSet<Card>(cards);
        }

        private static HashSet<Card> buildHashet(Card[] cards) {
                return new HashSet<Card>(Arrays.asList(cards));
        }
}
