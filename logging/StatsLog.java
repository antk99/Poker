package logging;

public class StatsLog {

    private int numDealt = 0;
    private int numWon = 0;

    public void addStat(boolean isWinner) {
        this.numDealt++;
        if (isWinner) {
            this.numWon++;
        }
    }

    public int getNumDealt() {
        return this.numDealt;
    }

    public int getNumWon() {
        return this.numWon;
    }
}
