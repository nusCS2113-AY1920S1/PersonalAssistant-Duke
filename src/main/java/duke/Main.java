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
    /**
     * Starts Duke with MainWindow.
     *
     * @param stage The main GUI of Duke
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            BorderPane borderPane = fxmlLoader.load();
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add("/layout/MainWindow.css");
            stage.setScene(scene);
            stage.setTitle("Duke++");
            fxmlLoader.<MainWindow>getController().setDuke(new Duke());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
