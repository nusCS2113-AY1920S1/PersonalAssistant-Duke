package moomoo.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import moomoo.MooMoo;

/**
 * Represents the Main GUI window to be shown to user when run.
 */

public class Main extends Application {

    private MooMoo moomoo = new MooMoo();

    /**
     * Starts the GUI to be shown to the user, reads in precreated FXML file created in Java SceneBuilder.
     * @param stage Stage object that holds the container containing the visual elements.
     */
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMoomoo(moomoo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}