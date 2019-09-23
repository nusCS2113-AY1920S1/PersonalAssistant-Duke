package duke.GUI;

import duke.Duke;
import javafx.application.Application;
import java.io.FileNotFoundException;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {

    Duke duke = new Duke(".\\src\\main\\java\\duke\\Data\\duke.txt");

    public Launcher() throws FileNotFoundException {
    }

    /**
     * Begin GUI application and run main duke class.
     * @param args expects array of string objects
     */
    public static void main(String[] args) {
        Application.launch(Duke.class, args);
    }
}
