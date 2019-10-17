package duke;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ScenesSwitcher {
    // private static Scene scene;
    private static Duke duke;

    public static void setDuke() {
        duke = new Duke();
    }

    public static Scene getMainScene() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        BorderPane borderPane = fxmlLoader.load();
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("/layout/MainWindow.css");
        fxmlLoader.<MainWindow>getController().setDuke(duke);

        return scene;
    }

}
