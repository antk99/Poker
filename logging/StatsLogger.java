package logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import logging.interfaces.PokerLogger;
import main.Card;
import main.Player;
import main.Rank;

/**
 * Logs the statistics for each possible set of hole cards.
 */
public class StatsLogger implements PokerLogger {

    protected final HashMap<Rank, HashMap<Rank, StatsLog>> suitedStats = new HashMap<>(); // For suited hole cards
    protected final HashMap<Rank, HashMap<Rank, StatsLog>> unsuitedStats = new HashMap<>(); // For unsuited hole cards

    /**
     * Adds a hand to the log.
     * 
     * @param communityCards The community cards
     * @param players        The players who played the hand
     * @param winners        The winners of the hand
     */
    public void addLog(ArrayList<Card> communityCards, ArrayList<Player> players, ArrayList<Player> winners) {
        HashSet<Integer> winnersSet = new HashSet<>();
        for (Player winner : winners) {
            winnersSet.add(winner.getPlayerid());
        }

        for (Player player : players) {
            boolean isWinner = winnersSet.contains(player.getPlayerid());
            for (Player winner : winners) {
                if (player.getPlayerid() == winner.getPlayerid()) {
                    isWinner = true;
                    break;
                }
            }

            ArrayList<Card> holeCards = player.getCards();
            Collections.sort(holeCards, Card.sortByRankComparatorDESC); // Sort the cards by rank DESC
            Rank rank1 = holeCards.get(0).getRank(); // Rank of the higher card
            Rank rank2 = holeCards.get(1).getRank(); // Rank of the lower card
            boolean isSuited = holeCards.get(0).getSuit() == holeCards.get(1).getSuit();

            HashMap<Rank, HashMap<Rank, StatsLog>> stats = isSuited ? suitedStats : unsuitedStats;

            if (!stats.containsKey(rank1)) {
                stats.put(rank1, new HashMap<>());
            }

            HashMap<Rank, StatsLog> stats2 = stats.get(rank1);
            if (!stats2.containsKey(rank2)) {
                stats2.put(rank2, new StatsLog());
            }

            StatsLog statsLog = stats2.get(rank2);
            statsLog.addStat(isWinner);
        }
    }

}
