package seedu.duke.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.duke.Duke;

import java.io.IOException;

public class Main extends Application {

    private Duke duke = new Duke();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Duke Email Manager");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
            stage.setMinWidth(450);
            stage.setMinHeight(300);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            fxmlLoader.<MainWindow>getController().setKeyBinding(scene);
            stage.show();
            MainWindow.setMainStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}