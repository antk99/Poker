package main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a player in a game of poker.
 */
public class Player {
    private final int playerid;
    private Card[] dealtCards = new Card[2]; // 2 cards dealt to player
    private Hand hand;

    public Player(int playerid) {
        this.playerid = playerid;
    }

    /**
     * Copy constructor for player.
     * 
     * @param player Player to copy
     */
    public Player(Player player) {
        this.playerid = player.playerid;
        this.dealtCards = player.dealtCards;
        this.hand = new Hand(player.hand);
    }

    /**
     * Sets the 2 cards dealt to the player and clears the hand.
     * 
     * @param card1
     * @param card2
     */
    public void dealCards(Card card1, Card card2) {
        this.hand = null;
        this.dealtCards[0] = card1;
        this.dealtCards[1] = card2;
    }

    /**
     * Sets the hand rank and the best 5-card hand.
     * 
     * @param handRank the hand rank
     * @param hand     the best 5-card hand
     */
    public void setHand(Hand hand) {
        this.hand = new Hand(hand);
    }

    public String toString() {
        String baseString = "P" + this.playerid;
        return this.hand == null ? baseString
                : baseString + " [" + this.dealtCards[0] + " " + this.dealtCards[1] + "] " + this.hand.toString();
    }

    public Hand getHand() {
        return this.hand;
    }

    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(this.dealtCards));
        return cards;
    }

    public int getPlayerid() {
        return this.playerid;
    }
}
