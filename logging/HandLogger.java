package logging;

import java.util.ArrayList;

import logging.interfaces.PokerLogger;
import main.Card;
import main.Player;

/**
 * Logs the hands played in a game.
 */
public class HandLogger implements PokerLogger {
    protected final ArrayList<HandLog> handLogs = new ArrayList<>();

    /**
     * Adds a hand to the log. This includes the cards in each player's hand, the
     * community cards, all the bets for that hand (todo) and the winner of the
     * hand.
     * 
     * @param communityCards The community cards
     * @param players        The players who played the hand
     * @param winners        The winners of the hand
     */
    public void addLog(ArrayList<Card> communityCards, ArrayList<Player> players, ArrayList<Player> winners) {
        HandLog handLog = new HandLog(communityCards, players, winners);
        this.handLogs.add(handLog);
    }

}
