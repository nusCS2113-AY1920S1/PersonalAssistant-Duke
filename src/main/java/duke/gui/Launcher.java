package duke.gui;

import duke.Main;
import javafx.application.Application;

/**
 * A launcher class for the GUI implementation of duke.Duke to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}