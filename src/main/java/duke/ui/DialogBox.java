package duke.ui;

import com.jfoenix.controls.JFXCheckBox;
import duke.parser.TimeParser;
import duke.task.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;


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
        } else if (task instanceof Event || task instanceof FixedDurationTask) {
            typeLabel.setText(task.toString());
            typeLabel.getStyleClass().add("event-label");
        } else if (task instanceof Deadline) {
            typeLabel.setText(task.toString());
            typeLabel.getStyleClass().add("deadline-label");
        }

        this.description.setText(task.getDescription());

        setConflictDescription(task, tasks);

        checkBox.setText(tasks.indexOf(task) + 1 + ". ");
        checkBox.setSelected(task.isDone());
    }

    private void setConflictDescription(Task task, TaskList tasks) {
        List<Task> conflicts = TimeParser.detectConflict(task, tasks);

        if (conflicts.size() > 0) {
            this.description.setTextFill(Color.rgb(237, 107, 96));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" (conflict with: ");
            for (Task t : conflicts) {
                int index = tasks.indexOf(t) + 1;
                stringBuilder.append(index + ", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");
            this.description.setText(task.getDescription() + stringBuilder.toString());
        }
    }
}