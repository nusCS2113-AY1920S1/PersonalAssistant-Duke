package entertainment.pro;

/**
 * This class handles application wide tasks and services.
 */
import entertainment.pro.ui.MovieHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage mainWindow;
    private Scene mainMoviesScene;
    private BorderPane mainLayout;

    /**
     * Reponsible for starting the application.
     */
    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        setUp();

        // Display window and the root scene
        mainMoviesScene = new Scene(mainLayout);
        mainWindow.setTitle("Entertainment Pro");
        mainWindow.setScene(mainMoviesScene);
        mainWindow.show();
    }

    /**
     * Responsible for setting the scene for the window.
     */
    private void setUp() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("trial.fxml"));
            mainLayout = loader.load();

            // setup the controller's window and reference to this main application class
            MovieHandler controller = loader.getController();
            controller.setMainApplication(this);
            controller.setWindow(mainWindow);
        } catch (IOException e) {
            //Ui.printLine();
            e.printStackTrace();
        }
    }

}