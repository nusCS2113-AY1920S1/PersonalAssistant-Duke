package spinbox.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class TaskBox extends AnchorPane {
    @FXML
    private Label description;
    @FXML
    private Label module;
    @FXML
    private Label dates;

    private TaskBox(String description, String module, String dates) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/itemBoxes/TaskBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.description.setTextFill(Color.WHITE);
        this.module.setTextFill(Color.WHITE);
        this.dates.setTextFill(Color.WHITE);

        this.description.setText(description);
        this.module.setText(module);
        this.dates.setText(dates);
        this.dates.setAlignment(Pos.BASELINE_RIGHT);
    }

    public static TaskBox getTaskBox(String description, String module, String dates) {
        return new TaskBox(description, module, dates);
    }
}
