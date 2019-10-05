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


    private Duke duke = new Duke();

    /**
     * Starts Duke with MainWindow.
     * @param stage The main GUI of Duke
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Duke.class.getResource("/view/MainWindow.fxml"));
            BorderPane borderPane = fxmlLoader.load();
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add("/layout/MainWindow.css");
            stage.setScene(scene);
            stage.setTitle("Duke++");
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
