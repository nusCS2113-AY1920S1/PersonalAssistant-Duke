import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    static File duketxt;
    static InputStream is;
    static OutputStream os;

    private Storage storage;
    private ArrayList<Task> tasks;

    public Duke(String filePath) {
        storage = new Storage(filePath);
        tasks = storage.load();
    }

    /**
     * <p>main running structure of Duke.</p>
     */
    public void run() {
        Ui.welcome();
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        while (!isExit) {
            try {
                String fullCommand = Ui.readLine(in);
                Command c = Parser.commandLine(fullCommand);
                c.execute(tasks, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                Ui.print(e.getMessage());
            }
        }
    }

    /**
     * <p>Main method of the entire project.</p>
     * @param args command line arguments, not used here
     */
    public static void main(String[] args) {
        new Duke("../data/tasks.txt").run();
    }
}
