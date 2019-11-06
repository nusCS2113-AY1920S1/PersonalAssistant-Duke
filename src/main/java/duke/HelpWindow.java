package duke;

import duke.ui.Ui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

//@@author gervaiseang
/**
 * Controller for a help page.
 */

public class HelpWindow extends AnchorPane {
    private Duke duke;
    private MainWindow mainWindow;

    @FXML
    private ComboBox<String> cbCommands;

    @FXML
    private Label getDescription;

    /**
     * Setting up Help Window Interface.
     *
     * @param d The object of Duke.
     * @param mainWindow The main window that runs DUKE Manager.
     */
    @FXML
    public void setHelpWindow(Duke d, MainWindow mainWindow) {
        duke = d;
        this.mainWindow = mainWindow;
        cbCommands.getItems().addAll(
                "todo",
                "deadline",
                "fixedduration",
                "budget",
                "addcontact",
                "listcontacts",
                "deletecontact",
                "findcontact",
                "notes",
                "filter",
                "update",
                "setpriority"
        );

        /**
         * Create action event when command is selected from drop down list
         */
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cbCommands.getSelectionModel().getSelectedItem().equals("todo")) {
                    getDescription.setText(cbCommands.getValue() + " <task description>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("deadline")) {
                    getDescription.setText(cbCommands.getValue() + " <task description> /by <date and time>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("fixedduration")) {
                    getDescription.setText(cbCommands.getValue() + " <task description> /for <duration> <unit>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("budget")) {
                    getDescription.setText(cbCommands.getValue() + " add/minus <amount> <optional desc> \n"
                            + cbCommands.getValue() + "<reset/new <amount> \n"
                            + cbCommands.getValue() + "<view>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("addcontact")) {
                    getDescription.setText(cbCommands.getValue() + " <name> <number> <email> <office>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("listcontacts")) {
                    getDescription.setText(cbCommands.getValue());
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("deletecontact")) {
                    getDescription.setText(cbCommands.getValue() + " <index>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("findcontact")) {
                    getDescription.setText(cbCommands.getValue() + " <keyword>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("notes")) {
                    getDescription.setText(cbCommands.getValue() + " <index> /add <notes description> \n"
                            + cbCommands.getValue() + " /delete \n"
                            + cbCommands.getValue() + " /show");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("filter")) {
                    getDescription.setText(cbCommands.getValue() + " <task type>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("update")) {
                    getDescription.setText(cbCommands.getValue() + " <index> /desc <description> \n"
                            + cbCommands.getValue() + " <index> /date <date time> \n"
                            + cbCommands.getValue() + " <index> /type <task type>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("setpriority")) {
                    getDescription.setText(cbCommands.getValue() + " <task number> <priority>");
                    getDescription.getScene().getWindow();
                }
            }
        };
        cbCommands.setOnAction(event);
    }


}