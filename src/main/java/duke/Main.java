package duke;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Bridge between duke and MainWindow.
 */
public class Main extends Application {

    // The duke object used by all scenes.
    private Duke duke;

    private void startDuke() {
        duke = new Duke();
    }

    /**
     * Starts Duke with MainWindow.
     * @param stage The main GUI of Duke
     */
    @Override
    public void start(Stage stage) {
        try {
            Scene scene = ScenesSwitcher.getMainScene();

            stage.setScene(scene);
            stage.setTitle("Duke++");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
