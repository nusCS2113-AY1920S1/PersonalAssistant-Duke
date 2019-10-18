package compal.ui;

import compal.Main;
import compal.logic.LogicManager;
import compal.storage.TaskStorageManager;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UiManager implements Ui {
    private UiParts uiParts;

    public UiManager(UiParts uiParts) {
        this.uiParts =uiParts;
    }


    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            TabPane tabReference = (TabPane) ap.getChildren().get(2);
            uiParts.setTabWindow(tabReference);

            //Create MainWindow Pane
            VBox root = new VBox();
            ScrollPane mainPane = new ScrollPane();
            mainPane.setContent(root);

            Tab mainTab = new Tab();
            mainTab.setText("Main Window");
            mainTab.setContent(mainPane);
            tabReference.getTabs().add(0, mainTab);

            //Create DailyCalUI Pane
            /**DailyCalUI dc = new DailyCalUI(compal);
             String datePattern = "dd/MM/yyyy";
             compal.ui.dateState = new SimpleDateFormat(datePattern).format(new Date());
             ScrollPane dailyPane = dc.init(compal.ui.dateState);

             Tab dailyTab = new Tab();
             dailyTab.setText(compal.ui.dateState);
             dailyTab.setContent(dailyPane);
             tabReference.getTabs().add(1, dailyTab);**/

            uiParts.setMainWindow(mainPane);
            uiParts.setSecondaryWindow(((ScrollPane) ap.getChildren().get(3)));  //get reference to secondary viewport
            Scene s1 = new Scene(ap);

            //Sets up primary stage --------------------------------------------------------------->
            primaryStage.setScene(s1);
            primaryStage.setTitle("ComPAL");
            primaryStage.setOpacity(1);
            primaryStage.getIcons().add(new Image(new FileInputStream(new File("./icon.png"))));
            //----------------------------------------------------------------------------------------------->

            //Gets and shows the current user system time ------------------------------------------------------->
            Label date = (Label) ap.getChildren().get(6);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date();

            date.setText("Today's Date:" + formatter.format(d));
            //------------------------------------------------------------------------------------------------->

            //Passes the initialized Compal object to the controller class to link them up
            fxmlLoader.<MainWindow>getController();

            primaryStage.show();
            // System.out.println("Main:LOG: Primary Stage Initialized. Setting Scene and running initialization code.");
            //uiParts.printg("HI WORKING?");
            //userName.checkInit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
