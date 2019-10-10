package JavaFx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgressController extends AnchorPane {

    @FXML
    private Label moduleCode;
    @FXML
    private Label valueCompleted;
    @FXML
    private Label valueOverdue;

    private ProgressController(String moduleCode1, String valueCompleted1, String valueOverdue1) {

        System.out.println("EFG");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/progressCircle.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        moduleCode.setText(moduleCode1);
        valueCompleted.setText(valueCompleted1);
        valueOverdue.setText(valueOverdue1);

    }

    public static ProgressController getProgress(String moduleCode1, String valueCompleted1, String valueOverdue1){
        var pc = new ProgressController(moduleCode1, valueCompleted1, valueOverdue1);
        return pc;
    }
}
