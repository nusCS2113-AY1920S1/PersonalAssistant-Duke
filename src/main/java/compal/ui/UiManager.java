package compal.ui;

import compal.Main;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@@author jaedonkey
public class UiManager implements Ui {
    private UiUtil uiUtil;

    public UiManager(UiUtil uiUtil) {
        this.uiUtil = uiUtil;
    }


    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // The content is displayed in tabs, e.g. MainWindow showing tasks in one tab.
            // Each Tab will be contained in the TabPane.
            TabPane tabReference = (TabPane) ap.getChildren().get(2);
            uiUtil.setTabWindow(tabReference);

            // Vertical Box (VBox) for the MainWindow (showing tasks).
            // By placing VBox in a ScrollPane, it is able to scroll automatically.
            VBox root = new VBox();
            ScrollPane mainPane = new ScrollPane();
            mainPane.setContent(root);

            // Create Tab for containing the MainWindow VBox, and add it to the TabPane.
            Tab mainTab = new Tab();
            mainTab.setText("Main Window");
            mainTab.setContent(mainPane);
            tabReference.getTabs().add(0, mainTab);

            // This allows uiUtil to read or modify the content on mainPane.
            uiUtil.setMainWindow(mainPane);

            Label date = (Label) ap.getChildren().get(4);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date();
            date.setText("Today's Date:" + formatter.format(d));
            fxmlLoader.<MainWindow>getController();

            // Prepare the Stage for show.
            Scene s1 = new Scene(ap);
            primaryStage.setScene(s1);
            primaryStage.setTitle("COMPal");
            primaryStage.setOpacity(1);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
