package simulations;

import java.io.IOException;
import java.util.*;

import logging.FileHandLogger;
import logging.FileStatsLogger;
import main.Dealer;
import main.Player;
import main.RandomDeck;
import utils.Utils;

/**
 * Simulates a game of poker and outputs the results to a CSV file.
 */
public class MonteCarloSimulation {
    public static void main(String[] args) throws IOException {
        int num_hands = 10000000; // number of hands to simulate
        int num_players = 8; // number of players at the table
        Dealer dealer = new Dealer(new RandomDeck());
        FileHandLogger logs = new FileHandLogger("C:/Users/me/Desktop/PokerHandLogs.txt");
        FileStatsLogger stats = new FileStatsLogger("C:/Users/me/Desktop/PokerStats.txt");

        for (int i = 0; i < num_players; i++) {
            dealer.addPlayer(new Player(i));
        }

        for (int i = 0; i < num_hands; i++) {
            Utils.printProgress(i, num_hands);

            // Start a new hand
            dealer.shuffle(); // shuffle the deck
            dealer.deal(); // deal 2 cards to each player
            dealer.dealFlop(); // deal 3 cards to the community
            dealer.dealTurn(); // deal 1 card to the community
            dealer.dealRiver(); // deal 1 card to the community

            // Get the winners
            ArrayList<Player> winners = dealer.determineWinner();
            logs.addLog(dealer.getCommunityCards(), dealer.getPlayers(), winners);
            stats.addLog(dealer.getCommunityCards(), dealer.getPlayers(), winners);
        }

        logs.printLogsToFile();
        stats.printStatsToFile();
    }
}
