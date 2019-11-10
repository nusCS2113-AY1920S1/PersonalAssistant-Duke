package controlpanel;

import guicontroller.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for controlpanel.Duke using FXML.
 */
public class Main extends Application {

    private Duke duke = new Duke();

    @Override
    //@@author therealnickcheong
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Financial Ghost");
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}