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

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class Main extends Application {

    private Duke duke = new Duke();


    /**
     * Initializes and sets up the GUI for ComPAL.
     * Pulls from file MainWindow.fxml
     * @param primaryStage the stage for GUI
     * @throws Exception GUI problems
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            duke.ui.sp = (ScrollPane) ap.getChildren().get(2);
            Scene s1 = new Scene(ap);

            //Set up some primary stage stuff --------------------------------------------------------------->
            primaryStage.setScene(s1);
            primaryStage.setTitle("ComPAL");
            primaryStage.setOpacity(0.98);
            primaryStage.getIcons().add(new Image(new FileInputStream(new File("./icon.png"))));
            //----------------------------------------------------------------------------------------------->


            //Show the current user system time --------------------------------------------------------------->
            Label date = (Label) ap.getChildren().get(6);
            Date d = java.util.Calendar.getInstance().getTime();
            date.setText("System Date:" + d.toString());
            //------------------------------------------------------------------------------------------------->


            fxmlLoader.<MainWindow>getController().setDuke(duke);
            primaryStage.show();
            System.out.println("Primary Stage Initialized. Setting Scene and Initializing Duke.");

            //run duke's initialization code
            duke.start(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


