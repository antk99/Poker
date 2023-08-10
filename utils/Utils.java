package utils;

import java.util.ArrayList;

import main.Card;
import main.Rank;

public class Utils {

    /**
     * Returns the lowest and highest rank in the list of cards.
     * 
     * @param cards List of cards
     * @return [lowest rank, highest rank]
     */
    public static Rank[] getLowestHighestRank(ArrayList<Card> cards) {
        Rank lowestRank = Rank.ACE;
        Rank highestRank = Rank.TWO;

        for (Card card : cards) {
            if (card.getRank().ordinal() < lowestRank.ordinal()) {
                lowestRank = card.getRank();
            }

            if (card.getRank().ordinal() > highestRank.ordinal()) {
                highestRank = card.getRank();
            }
        }

        return new Rank[] { lowestRank, highestRank };
    }

    /**
     * Prints the progress of a loop. Prints a message every 5% of the total loop
     * iterations.
     * 
     * @param currentIteration The current iteration of the loop
     * @param totalIterations  The total number of iterations in the loop
     */
    public static void printProgress(int currentIteration, int totalIterations) {
        if (currentIteration % ((5 / 100.0) * totalIterations) == 0) {
            int percentComplete = (int) (currentIteration / (float) totalIterations * 100);
            System.out.println(percentComplete + "% complete");
        }
    }
}
