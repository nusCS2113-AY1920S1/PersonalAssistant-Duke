import control.Duke;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import storage.BookingConstants;
import storage.Constants;
import ui.MainWindow;

import java.io.IOException;

/**
 * A GUI for control.Duke using FXML.
 */
public class Main extends Application {

    private Duke duke = new Duke(BookingConstants.FILENAME, Constants.ROOMFILENAME);

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Hall Booker");
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
}
