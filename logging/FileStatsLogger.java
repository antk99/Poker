package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import main.HandRank;
import main.Rank;

/**
 * Wrapper class for StatsLogger that writes all the logs to a file.
 */
public class FileStatsLogger extends StatsLogger {

    private String rootFilename;

    public FileStatsLogger(String filename) {
        // Get the root filename without the extension
        this.rootFilename = filename.substring(0, filename.lastIndexOf('.')) + " ";
    }

    /**
     * Writes all the stats to a file.
     */
    public void printStatsToFile() throws IOException {
        String csvHeaders = "Rank 1,Rank 2,Number Dealt,Number Won,Win Percentage\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.rootFilename + "combined.txt"))) {
            writer.write(csvHeaders);

            // Write the combined stats
            for (Rank rank1 : this.unsuitedStats.keySet()) {
                for (Rank rank2 : this.unsuitedStats.get(rank1).keySet()) {
                    StatsLog unsuitedStatsLog = this.unsuitedStats.get(rank1).get(rank2);

                    int numDealt = unsuitedStatsLog.getNumDealt();
                    int numWon = unsuitedStatsLog.getNumWon();

                    // Get the suited stats
                    try {
                        StatsLog suitedStatsLog = this.suitedStats.get(rank1).get(rank2);
                        numDealt += suitedStatsLog.getNumDealt();
                        numWon += suitedStatsLog.getNumWon();
                    } catch (Exception e) {
                        // Do nothing
                    }

                    double winPercentage = (double) numWon / numDealt;

                    writer.write(rank1 + "," + rank2 + "," + numDealt + "," + numWon + "," + winPercentage + "\n");
                }
            }
            writer.close();
            System.out.println("Logs written to " + this.rootFilename + "combined.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.rootFilename + "suited.txt"))) {
            writer.write(csvHeaders);

            // Write the suited stats
            for (Rank rank1 : this.suitedStats.keySet()) {
                for (Rank rank2 : this.suitedStats.get(rank1).keySet()) {
                    StatsLog suitedStatsLog = this.suitedStats.get(rank1).get(rank2);

                    int numDealt = suitedStatsLog.getNumDealt();
                    int numWon = suitedStatsLog.getNumWon();

                    double winPercentage = (double) numWon / numDealt;

                    writer.write(rank1 + "," + rank2 + "," + numDealt + "," + numWon + "," + winPercentage + "\n");
                }
            }
            writer.close();
            System.out.println("Logs written to " + this.rootFilename + "suited.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.rootFilename + "unsuited.txt"))) {
            writer.write(csvHeaders);

            // Write the unsuited stats
            for (Rank rank1 : this.unsuitedStats.keySet()) {
                for (Rank rank2 : this.unsuitedStats.get(rank1).keySet()) {
                    StatsLog unsuitedStatsLog = this.unsuitedStats.get(rank1).get(rank2);

                    int numDealt = unsuitedStatsLog.getNumDealt();
                    int numWon = unsuitedStatsLog.getNumWon();

                    double winPercentage = (double) numWon / numDealt;

                    writer.write(rank1 + "," + rank2 + "," + numDealt + "," + numWon + "," + winPercentage + "\n");
                }
            }
            writer.close();
            System.out.println("Logs written to " + this.rootFilename + "unsuited.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.rootFilename + "handranks.txt"))) {
            writer.write("Hand Rank,Number Dealt,Number Won,Win Percentage\n");

            // Write the hand ranks
            for (HandRank handRank : this.handRankStats.keySet()) {
                StatsLog handRankStatsLog = this.handRankStats.get(handRank);

                int numDealt = handRankStatsLog.getNumDealt();
                int numWon = handRankStatsLog.getNumWon();

                double winPercentage = (double) numWon / numDealt;

                writer.write(handRank + "," + numDealt + "," + numWon + "," + winPercentage + "\n");
            }
            writer.close();
            System.out.println("Logs written to " + this.rootFilename + "handranks.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
