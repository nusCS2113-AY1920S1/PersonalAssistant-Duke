package seedu.duke;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(DukeGui.class, args);
    }

    public static class DukeGui extends Application {

        @Override
        public void start(Stage stage) {
            Label helloWorld = new Label("Hello World!"); // Creating a new Label control
            Scene scene = new Scene(helloWorld); // Setting the scene to be our Label

            stage.setScene(scene); // Setting the stage to show our screen
            stage.show(); // Render the stage.
        }
    }
}