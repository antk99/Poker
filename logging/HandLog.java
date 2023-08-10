package logging;

import java.util.ArrayList;
import java.util.HashSet;

import main.Card;
import main.Player;

/**
 * A log of a hand played in a game.
 */
public class HandLog {
    private final String log;

    public HandLog(ArrayList<Card> communityCards, ArrayList<Player> players, ArrayList<Player> winners) {
        HashSet<Integer> winnersSet = new HashSet<>();
        for (Player winner : winners) {
            winnersSet.add(winner.getPlayerid());
        }

        String str = "Community Cards:\n[";
        for (Card card : communityCards) {
            str += card.toString() + " ";
        }
        str = str.trim();
        str += "]\n\nPlayers:\n";
        for (Player player : players) {
            boolean isWinner = winnersSet.contains(player.getPlayerid());

            str += player.toString();
            if (isWinner) {
                str += " (Winner)";
            }
            str += "\n";
        }

        this.log = str;
    }

    @Override
    public String toString() {
        return this.log;
    }
}
