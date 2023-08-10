package logging.interfaces;

import java.util.ArrayList;

import main.Card;
import main.Player;

/**
 * Interface for logging classes.
 */
public interface PokerLogger {

    /**
     * Adds a log to the log.
     */
    public void addLog(ArrayList<Card> communityCards, ArrayList<Player> players, ArrayList<Player> winners);
}
