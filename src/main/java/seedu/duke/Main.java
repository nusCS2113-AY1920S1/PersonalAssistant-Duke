package seedu.duke;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            configureStage(stage, scene);
            configureUi(stage, fxmlLoader, scene);

            stage.show();

            Duke duke = new Duke();
            duke.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureUi(Stage stage, FXMLLoader fxmlLoader, Scene scene) {
        Duke.getUI().setMainWindow(fxmlLoader.getController());
        Duke.getUI().setKeyBinding(scene);
        Duke.getUI().setMainStage(stage);
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("Duke Email Manager");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        stage.setMinWidth(450);
        stage.setMinHeight(300);
    }

}