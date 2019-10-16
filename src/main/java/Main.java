import java.io.IOException;

import exception.DukeException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Acts as the bridge between Duke's logic and the Graphical User Interface based on FXML.
 */
public class Main extends Application {
    /**
     * The start class method needs to be created in all JavaFX application and the Anchor pane is created,
     * along with the and finally the stage is shown.
     */
    @Override
    public void start(Stage stage) {
        try {
            MainWindow.initializeDukeElements();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/GuiLogo.png")));
            stage.setScene(scene);
            stage.setTitle("Chronologer");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}