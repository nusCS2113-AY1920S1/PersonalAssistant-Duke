package duke.ui;

import duke.Duke;
import duke.ui.MainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
                "fixedduration"
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
                }
            }
        };
        cbCommands.setOnAction(event);
    }


}