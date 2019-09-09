package duke;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import duke.logic.Duke;
import duke.ui.MainWindow;

import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(Launcher.class, args);
    }

    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        try {
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            scene.getStylesheets().add(Duke.class.getResource("/css/duke.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
