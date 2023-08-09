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

}
