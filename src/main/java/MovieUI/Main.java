package MovieUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import object.MovieInfoObject;
import ui.Ui;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {
    private Stage mainWindow;
    private Scene mainMoviesScene;
    private BorderPane mainLayout;

    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        setUp();

        // Display window and the root scene
        mainMoviesScene = new Scene(mainLayout);
        mainWindow.setTitle("Entertainment Pro");
        mainWindow.setScene(mainMoviesScene);
        mainWindow.show();
    }

    private void setUp() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MainPage.fxml"));
            mainLayout = loader.load();

            // setup the controller's window and reference to this main application class
            MovieHandler controller = loader.getController();
            controller.setMainApplication(this);
            controller.setWindow(mainWindow);
        } catch (IOException e) {
            Ui.printLine();
            e.printStackTrace();
        }
    }

    // Request control to be passed to the movie info controller to view movie info
    public void transitToMovieInfoController(MovieInfoObject movie) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("InsideMovie.fxml"));
            Pane layout = loader.load();

            // setup controller
            MovieInfoController controller = loader.getController();
            controller.setWindow(mainWindow);
            controller.setMainApplication(this);
            controller.setMovie(movie);
            mainWindow.setScene(new Scene(layout));
        } catch (IOException e) {
            Ui.printLine();
            e.printStackTrace();
        }
    }

    public void transitionBackToMoviesController()
    {
        mainWindow.setScene(mainMoviesScene);
    }


}