package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.dukeexception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import duke.ui.Ui;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
//    @FXML
//    private MenuItem exit;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
//    @FXML
//    private Button sendButton;
    @FXML
    private ListView<Task> listT;
    @FXML
    private Label labelSelectedTask;
    @FXML
    private Button btnDone;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Setting up Duke GUI.
     * @param d The object of Duke.
     */
    public void setDuke(Duke d) {
        duke = d;
        listViewRefresh();
        setDisableButtons();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(Ui.showWelcomeGui(), dukeImage)
        );
    }

    Timer timer = new Timer();
    TimerTask exitDuke = new TimerTask() {
        public void run() {
            System.exit(0);
        }
    };

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            if (cmd instanceof ExitCommand) {
                duke.saveState(cmd);
                response = Ui.showLineGui() + Ui.showByeGui() + Ui.showLineGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
                timer.schedule(exitDuke, new Date(System.currentTimeMillis() + 500));
            } else {
                response = Ui.showLineGui() + duke.executeCommand(cmd) + Ui.showLineGui();
                dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
                );
                updateGui();
            }
        } catch (DukeException e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui(e.getMessage()) + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.") + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    @FXML
    private void handleUserEvent(String input) {
        //String input = userInput.getText();
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
//            if (cmd instanceof ExitCommand) {
//                duke.saveState(cmd);
//                response = Ui.showLineGui() + Ui.showByeGui() + Ui.showLineGui();
//                dialogContainer.getChildren().add(
//                        DialogBox.getDukeDialog(response, dukeImage)
//                );
//                timer.schedule(exitDuke, new Date(System.currentTimeMillis() + 500));
//            } else {
                response = Ui.showLineGui() + duke.executeCommand(cmd) + Ui.showLineGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
           // }
        } catch (DukeException e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui(e.getMessage()) + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.") + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    @FXML
    private void onMouseClick_ListView(MouseEvent event) {
        //System.out.println("clicked on " + listT.getSelectionModel().getSelectedItem());
        labelSelectedTask.setText("Selected Task: " + listT.getSelectionModel().getSelectedItem());
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        if (taskObj.isDone()) {
            btnDone.setDisable(true);
        } else {
            btnDone.setDisable(false);
        }
        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
    }

    @FXML
    private void onMouseClickDone(MouseEvent event) {
        //System.out.println("CURRENTLY on " + listT.getSelectionModel().getSelectedItem());
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + 1;
        handleUserEvent("done " + itemNumber);
        updateGui();
    }

    @FXML
    private void onMouseClickDelete(MouseEvent event) {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + 1;
        handleUserEvent("delete " + itemNumber);
        updateGui();
    }

    @FXML
    private void updateGui() {
        listViewRefresh();
        setDisableButtons();
        labelSelectedTask.setText("Selected Task: ");
    }

    @FXML
    private void listViewRefresh() {
        listT.getItems().clear();
        TaskList items = duke.getTaskList();
        for (int i = 0; i < items.size(); i++) {
            listT.getItems().add(items.get(i));
        }
    }

    @FXML
    private void setDisableButtons() {
        btnDone.setDisable(true);
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
    }

    @FXML
    private void exitProgram() {
        String input = "bye";
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            duke.saveState(cmd);
            response = Ui.showLineGui() + Ui.showByeGui() + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            timer.schedule(exitDuke, new Date(System.currentTimeMillis() + 500));
        } catch (DukeException e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui(e.getMessage()) + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.") + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }
}