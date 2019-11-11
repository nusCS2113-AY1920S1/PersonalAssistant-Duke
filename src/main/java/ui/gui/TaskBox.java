package ui.gui;

import storage.task.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class TaskBox extends HBox {
    @FXML
    private TextFlow taskText;

    private TaskBox(String taskTypeStr, String taskDescStr) {
        try {
            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("/view/TaskBox.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Text boldText = new Text(taskTypeStr + " ");
        boldText.setStyle("-fx-font-weight: bold");
        this.taskText.getChildren().addAll(boldText, new Text(taskDescStr));
    }

    /**
     * Creates a new TaskBox GUI Component for a Task Object.
     * @param task Task Object to be printed
     * @return TaskBox Object
     */
    public static TaskBox getNewTaskBox(Task task) {
        String taskTypeStr = task.getTaskType().toString();
        String taskDescStr = task.genTaskDesc();
        TaskBox newTaskBox = new TaskBox(taskTypeStr, taskDescStr);
        newTaskBox.setAlignment(Pos.CENTER_LEFT);
        return newTaskBox;
    }
}
