package main;

import java.util.ArrayList;

/**
 * Represents a hand of cards, which contains the player's best 5-card hand,
 * the rank of the hand, and the score for tie-breaking.
 */
public class Hand {
    private ArrayList<Card> bestHand; // best 5-card hand
    private HandRank handRank; // rank of hand
    private int score = 0; // score of hand, used for tie-breaking

    /**
     * Constructor for Hand with score.
     * 
     * @param handRank Rank of hand
     * @param bestHand Best 5-card hand
     * @param score    Score of hand, used for tie-breaking, higher score wins
     */
    public Hand(HandRank handRank, ArrayList<Card> bestHand, int score) {
        this.handRank = handRank;
        this.bestHand = new ArrayList<>(bestHand);
        this.score = score;
    }

    /**
     * Copy constructor for Hand.
     * 
     * @param hand Hand to copy
     */
    public Hand(Hand hand) {
        this.handRank = hand.getHandRank();
        this.bestHand = hand.getBestHand();
        this.score = hand.getScore();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HandRank getHandRank() {
        return this.handRank;
    }

    public ArrayList<Card> getBestHand() {
        return new ArrayList<>(this.bestHand);
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        String str = "[";
        for (Card card : this.bestHand) {
            str += card.toString() + " ";
        }
        str = str.substring(0, str.length() - 1); // remove last space
        str += "] ";
        str += this.handRank.toString();
        return str;
    }
}
