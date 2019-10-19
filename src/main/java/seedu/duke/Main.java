package seedu.duke;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.duke.ui.MainWindow;

import java.io.IOException;

public class Main extends Application {

    Duke duke;

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

            Duke.getUI().setMainWindow(fxmlLoader.<MainWindow>getController());
            Duke.getUI().setKeyBinding(scene);
            Duke.getUI().setMainStage(stage);

            stage.show();

            duke = new Duke();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}