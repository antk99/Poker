package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Wrapper class for HandLogger that writes all the logs to a file.
 */
public class FileHandLogger extends HandLogger {

    private final String filename;

    public FileHandLogger(String filename) {
        super();
        this.filename = filename;
    }

    /**
     * Writes all the logs to a file.
     */
    public void printLogsToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
            for (HandLog log : this.handLogs) {
                writer.write(log.toString());
                writer.newLine();
            }
            writer.close();
            System.out.println("Logs written to " + this.filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
