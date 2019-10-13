package duke;

import duke.gui.Gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Class to launch Dr. Duke.
 */
public class DukeLauncher {
    /**
     * Main function.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        // TODO: mkdir in Storage class instead?
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        Application.launch(DukeApplication.class, args);
    }

    public static class DukeApplication extends Application {
        @Override
        public void start(Stage stage) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(DukeGui.class.getResource("/view/MainWindow.fxml"));
                AnchorPane ap = fxmlLoader.load();

                Gui gui = fxmlLoader.getController();
                gui.setDuke(new DukeGui(gui, "data" + File.separator + "tasks.tsv"));

                stage.setScene(new Scene(ap));
                stage.setTitle("Dr. Duke");
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
