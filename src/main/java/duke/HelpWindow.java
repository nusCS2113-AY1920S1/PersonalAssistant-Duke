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
                "event",
                "fixedduration",
                "repeat",
                "doafter"
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
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("event")) {
                    getDescription.setText(cbCommands.getValue() + " <task description> /at <date and time>");
                    getDescription.getScene().getWindow();
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("fixedduration")) {
                    getDescription.setText(cbCommands.getValue() + " <task description> /for <duration> <unit>");
                    getDescription.getScene().getWindow();
                }
                else if (cbCommands.getSelectionModel().getSelectedItem().equals("repeat")){
                    getDescription.setText(cbCommands.getValue() +
                            " <task> /from <date and time> /for <duration> <day/week/month>");
                    getDescription.getScene().getWindow();
                }
                else if (cbCommands.getSelectionModel().getSelectedItem().equals("doafter")){
                    getDescription.setText(cbCommands.getValue() +
                            " <task description> /after <existing task description>");
                    getDescription.getScene().getWindow();
                }
            }
        };
        cbCommands.setOnAction(event);
    }


}