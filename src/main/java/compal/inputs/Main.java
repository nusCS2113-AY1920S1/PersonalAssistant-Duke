package compal.inputs;

import compal.main.Duke;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    private Duke duke = new Duke();

    //----------------------->


    /**
     * Initializes and sets up the GUI for ComPAL.
     * Pulls layout from file MainWindow.fxml
     *
     * @param primaryStage the stage for GUI
     * @throws Exception GUI problems
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            duke.ui.mainWindow = (ScrollPane) ap.getChildren().get(2); //gets a reference to the main display viewport
            duke.ui.secondaryWindow = (ScrollPane) ap.getChildren().get(3); //get reference to secondary viewport
            Scene s1 = new Scene(ap);

            //Set up some primary stage stuff --------------------------------------------------------------->
            primaryStage.setScene(s1);
            primaryStage.setTitle("ComPAL");
            primaryStage.setOpacity(0.98);
            primaryStage.getIcons().add(new Image(new FileInputStream(new File("./icon.png"))));
            //----------------------------------------------------------------------------------------------->


            //Get and show the current user system time ------------------------------------------------------->
            Label date = (Label) ap.getChildren().get(6);

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            Date d = new Date();

            date.setText("Today's Date:" + formatter.format(d));
            //------------------------------------------------------------------------------------------------->


            //just passes the initialized Duke object to the controller class to link them up
            fxmlLoader.<MainWindow>getController().setDuke(duke);

            primaryStage.show();
            System.out.println("Main:LOG: Primary Stage Initialized. Setting Scene and running initialization code.");

            //run ui's initialization code
            duke.ui.checkInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


