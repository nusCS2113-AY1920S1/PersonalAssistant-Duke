package duke;

import duke.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static duke.common.Messages.filePath;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    static Boolean isScreenLoaded = false;
//    private MainWindow mainWindow = new MainWindow();
//    private Ui ui = new Ui(mainWindow);
//    private Duke duke = new Duke(ui);

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap);
        stage.setScene(scene);
//        fxmlLoader.<MainWindow>getController().setDuke(duke);
        stage.setTitle("ChefDuke");
        stage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("ChefDuke");
//        stage.show();
    }
}