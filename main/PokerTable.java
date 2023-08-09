package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PokerTable {

    private static void printProgress(int currentIteration, int totalIterations) {
        if (currentIteration % ((5 / 100.0) * totalIterations) == 0)
            System.out.println(currentIteration / (float) totalIterations * 100 + "% complete");
    }

    public static void main(String[] args) throws IOException {
        int num_hands = 10000000; // number of hands to simulate
        int num_players = 8; // number of players at the table
        Dealer dealer = new Dealer();

        // Stores { card1: { card2: [# times dealt, # times won] } }
        HashMap<Rank, HashMap<Rank, int[]>> stats = new HashMap<>();

        for (int i = 0; i < num_players; i++) {
            dealer.addPlayer(new Player(i));
        }

        for (int i = 0; i < num_hands; i++) {
            printProgress(i, num_hands);

            // Start a new hand
            dealer.shuffle(); // shuffle the deck
            dealer.deal(); // deal 2 cards to each player
            dealer.dealFlop(); // deal 3 cards to the community
            dealer.dealTurn(); // deal 1 card to the community
            dealer.dealRiver(); // deal 1 card to the community

            // Get the winners
            ArrayList<Player> winners = dealer.determineWinner();

            // Update stats
            for (Player player : dealer.getPlayers()) {
                boolean isWinner = winners.contains(player);
                int value = isWinner ? 1 : 0;
                ArrayList<Card> pCards = player.getCards();
                pCards.sort(Deck.sortByRankComparatorDESC);

                Card card1 = pCards.get(0);
                Card card2 = pCards.get(1);
                if (stats.containsKey(card1.getRank())) {
                    HashMap<Rank, int[]> stats2 = stats.get(card1.getRank());
                    if (stats2.containsKey(card2.getRank())) {
                        int[] arr = stats2.get(card2.getRank());
                        stats2.put(card2.getRank(), new int[] { arr[0] + 1, arr[1] + value });
                    } else {
                        int[] arr = { 1, value };
                        stats2.put(card2.getRank(), arr);
                    }
                } else {
                    int[] arr = { 1, value };
                    HashMap<Rank, int[]> map = new HashMap<>();
                    map.put(card2.getRank(), arr);
                    stats.put(card1.getRank(), map);
                }
            }
        }

        String s = "Card 1,Card 2,Frequency,Wins,Win %\n";
        for (Rank c1 : stats.keySet()) {
            for (Rank c2 : stats.get(c1).keySet()) {
                int[] arr = stats.get(c1).get(c2);
                float pct = arr[1] / (float) arr[0] * 100;
                s += c1.toString() + "," + c2.toString() + "," + arr[0] + "," + arr[1] + ","
                        + pct
                        + "\n";
            }
        }
        Files.write(Paths.get("C:/Users/me/Desktop/output.csv"), s.getBytes());
    }
}
