package compal;

import compal.ui.DailyCal;
import compal.ui.MainWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import compal.compal.Compal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Initializes GUI.
 */
public class Main extends Application {
    //Class Properties/Variables

    private Compal compal = new Compal();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the GUI.
     * Pulls layout from file MainWindow.fxml.
     *
     * @param primaryStage The stage for GUI.
     * @throws Exception If there is GUI problems.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            TabPane tabReference = (TabPane) ap.getChildren().get(2);
            compal.ui.tabWindow = tabReference;

            //Create MainWindow Pane
            VBox root = new VBox();
            ScrollPane mainPane = new ScrollPane();
            mainPane.setContent(root);

            Tab mainTab = new Tab();
            mainTab.setText("Main Window");
            mainTab.setContent(mainPane);
            tabReference.getTabs().add(0, mainTab);

            //Create DailyCal Pane
            DailyCal dc = new DailyCal();
            String datePattern = "dd/MM/yyyy";
            String todayDate = new SimpleDateFormat(datePattern).format(new Date());
            ScrollPane dailyPane = dc.init(todayDate);

            Tab dailyTab = new Tab();
            dailyTab.setText("Daily Window");
            dailyTab.setContent(dailyPane);
            tabReference.getTabs().add(1, dailyTab);

            compal.ui.mainWindow = mainPane; //gets a reference to the main display viewport
            compal.ui.secondaryWindow = (ScrollPane) ap.getChildren().get(3); //get reference to secondary viewport
            Scene s1 = new Scene(ap);

            //Sets up primary stage --------------------------------------------------------------->
            primaryStage.setScene(s1);
            primaryStage.setTitle("ComPAL");
            primaryStage.setOpacity(0.96);
            primaryStage.getIcons().add(new Image(new FileInputStream(new File("./icon.png"))));
            //----------------------------------------------------------------------------------------------->

            //Gets and shows the current user system time ------------------------------------------------------->
            Label date = (Label) ap.getChildren().get(6);

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            Date d = new Date();

            date.setText("Today's Date:" + formatter.format(d));
            //------------------------------------------------------------------------------------------------->

            //Passes the initialized Compal object to the controller class to link them up
            fxmlLoader.<MainWindow>getController().setCompal(compal);

            primaryStage.show();
            System.out.println("Main:LOG: Primary Stage Initialized. Setting Scene and running initialization code.");

            //Runs ui's initialization code
            compal.ui.checkInit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
