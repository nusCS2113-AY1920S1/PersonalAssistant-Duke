package duke;

import duke.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune-reused
//Reused from https://github.com/nusCS2113-AY1920S1/duke/blob/master/tutorials/javaFxTutorialPart4.md with minor modifications
/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Duke duke = new Duke();
    private MainWindow mainWindow = new MainWindow();
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Main() throws IOException {
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setTitle("DUKE Manager");
            stage.setResizable(false);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Unable to load main window.", e);
        }
    }

    //@@author maxxyx96
    //Solution adapted from https://github.com/nusCS2113-AY1920S1/addressbook-level3/blob/master/src/main/java/seedu/address/MainApp.java
    @Override
    public void stop() {
        logger.info("ALERT: Duke is shutting down! Attempting to save Data... ");
        duke.suddenStop();
        mainWindow.exitProgramAbrupt();

    } //@@author

}
//@@author