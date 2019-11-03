package seedu.duke;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.duke.ui.UI;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = LogsCenter.getLogger(Main.class);

    @Override
    public void start(Stage stage) {
        try {
            LogsCenter.init();
            logger.info("=============================[ Initializing Email Manager "
                    + "]===========================");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            configureStage(stage, scene);
            configureUi(stage, fxmlLoader, scene);

            stage.show();
            Duke.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureUi(Stage stage, FXMLLoader fxmlLoader, Scene scene) {
        UI.getInstance().setMainWindow(fxmlLoader.getController());
        UI.getInstance().setKeyBinding(scene);
        UI.getInstance().setMainStage(stage);
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("Duke Email Manager");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        stage.setMinWidth(450);
        stage.setMinHeight(300);
    }

}