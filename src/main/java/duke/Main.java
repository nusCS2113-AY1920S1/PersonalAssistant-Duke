package duke;

import duke.javafx.MainWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Duke duke = new Duke();

    public Main() throws FileNotFoundException {
    }

    /**
     * starts duke GUI.
     * @param stage the stage for GUI
     */
    @Override
    public void start(Stage stage) {
        System.out.println("Starting Duke...");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * stops the GUI.
     * @throws Exception the exception thrown
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
    }

}