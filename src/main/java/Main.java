import java.io.IOException;

import exception.DukeException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws DukeException {
        try {
            MainWindow GUIrender = new MainWindow();
            GUIrender.initializeDukeElements();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/GuiLogo.png")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}