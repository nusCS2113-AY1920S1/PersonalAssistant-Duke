package duke;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    static Boolean isScreenLoaded = false;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        //You are violating SLAP here.
        // If the responsibility of this method is to call other objects' method to start the application,
        // then it should not deal with creation of objects to follow SLAP.
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap);
        stage.setScene(scene);

        stage.setTitle("ChefDuke");
        stage.show();
    }
}