package duke.ui;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import duke.task.*;

import java.io.IOException;


public class DialogBox extends AnchorPane {
    @FXML
    private Label description;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private Label typeLabel;

    public DialogBox(Task task, TaskList tasks) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        typeLabel.getStyleClass().clear();
        if (task instanceof Todo) {
            typeLabel.setText(" TODO ");
            typeLabel.getStyleClass().add("todo-label");
        } else if (task instanceof Event) {
            typeLabel.setText(task.toString());
            typeLabel.getStyleClass().add("event-label");
        } else if (task instanceof Deadline) {
            typeLabel.setText(task.toString());
            typeLabel.getStyleClass().add("deadline-label");
        }

        this.description.setText(task.getDescription());
        checkBox.setText(tasks.indexOf(task) + 1 + ". ");
        checkBox.setSelected(task.isDone());
    }

}