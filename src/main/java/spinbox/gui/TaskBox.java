package spinbox.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TaskBox extends HBox {
    @FXML
    private Label description;
    @FXML
    private Label module;
    @FXML
    private Label dates;

    private TaskBox(String description, String module, String dates) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/TaskBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.description.setText(description);
        this.module.setText(module);
        this.dates.setText(dates);
    }

    public static TaskBox getTask(String description, String module, String dates) {
        return new TaskBox(description, module, dates);
    }
}
