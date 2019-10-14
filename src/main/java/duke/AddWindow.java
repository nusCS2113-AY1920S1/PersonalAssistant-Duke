package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.dukeexception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import duke.ui.Ui;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class AddWindow extends AnchorPane {
    private Duke duke;
    private MainWindow mainWindow;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private TextField tfDesc;

    @FXML
    private TextField tfDateTime;

    @FXML
    private TextField tfDuration;

    @FXML
    private TextField tfUnit;

    @FXML
    private ComboBox<String> cbExistingTask;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnReset;

    @FXML
    public void setAddWindow(Duke d, MainWindow mainWindow) {
        duke = d;
        this.mainWindow = mainWindow;
        cbType.getItems().addAll(
                "Todo",
                "Deadline",
                "Event",
                "Fixed Duration",
                "Repeat",
                "Do After"
                );
        TaskList items = d.getTaskList();
        for (int i = 0; i < items.size(); i++) {
            cbExistingTask.getItems().add(items.get(i).getDescription());
        }
    }

    @FXML
    void onMouseClickAdd() {
        System.out.println("clicked on " + cbType.getSelectionModel().getSelectedItem());

        if (cbType.getSelectionModel().getSelectedItem().equals("Todo")) {
            mainWindow.handleUserEvent("todo " + tfDesc.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Deadline")) {
            mainWindow.handleUserEvent("deadline " + tfDesc.getText().trim() + " /by " + tfDateTime.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Event")) {
            mainWindow.handleUserEvent("event " + tfDesc.getText().trim() + " /at " +  tfDateTime.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Fixed Duration")) {
            mainWindow.handleUserEvent("fixedduration " + tfDesc.getText().trim() + " /for " +  tfDuration.getText().trim() + " " +  tfUnit.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Repeat")) {
            mainWindow.handleUserEvent("repeat " + tfDesc.getText().trim() + " /from " +  tfDateTime.getText().trim() + " /for " +  tfDuration.getText().trim() + " "+ tfUnit.getText().trim());
        } else if (cbType.getSelectionModel().getSelectedItem().equals("Do After")) {
            mainWindow.handleUserEvent("doafter " + tfDesc.getText().trim() + " /after " +  cbExistingTask.getSelectionModel().getSelectedItem());
        }
        mainWindow.listViewRefresh();
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onMouseClickReset() {
        cbType.getSelectionModel().clearSelection();
        tfDesc.clear();
        tfDateTime.clear();
        tfDuration.clear();
        tfUnit.clear();
        cbExistingTask.getSelectionModel().clearSelection();
    }
}