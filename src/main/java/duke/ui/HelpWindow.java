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

    @FXML
    private Label getExample;

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
                "list",
                "done",
                "find",
                "delete",
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
                "setpriority",
                "findpriority",
                "finddate"
        );

        /**
         * Create action event when command is selected from drop down list
         */
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cbCommands.getSelectionModel().getSelectedItem().equals("todo")) {
                    getDescription.setText(cbCommands.getValue() + " [TASK]");
                    getExample.setText(cbCommands.getValue() + " CS2113 assignment");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("deadline")) {
                    getDescription.setText(cbCommands.getValue() + " [TASK] /by [DD/MM/YYYY HHMM] \n");
                    getExample.setText(cbCommands.getValue() + " finish reading book /by 27/10/2019 2359");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("fixedduration")) {
                    getDescription.setText(cbCommands.getValue() + " [TASK] /for [DURATION] [UNITS] \n");
                    getExample.setText(cbCommands.getValue() + " build a robot /for 3 hour");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("budget")) {
                    getDescription.setText(cbCommands.getValue() + " add/+/minus/- [AMOUNT] [(OPTIONAL)DESCRIPTION] \n"
                            + cbCommands.getValue() + " reset/new [AMOUNT] \n"
                            + cbCommands.getValue() + " view");
                    getExample.setText(cbCommands.getValue() + " add 21.13 pay school fees \n"
                            + cbCommands.getValue() + " reset 100 \n"
                            + cbCommands.getValue() + " view");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("addcontact")) {
                    getDescription.setText(cbCommands.getValue() + " [NAME], [NUMBER], [EMAIL], [OFFICE]");
                    getExample.setText(cbCommands.getValue() + " Prof Tan, 91234567, tancc@nus.edu.sg, E1-08-11");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("listcontacts")) {
                    getDescription.setText(cbCommands.getValue());
                    getExample.setText(cbCommands.getValue());
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("deletecontact")) {
                    getDescription.setText(cbCommands.getValue() + " [INDEX]");
                    getExample.setText(cbCommands.getValue() + " 1");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("findcontact")) {
                    getDescription.setText(cbCommands.getValue() + " [KEYWORD]");
                    getExample.setText(cbCommands.getValue() + " Prof Tan");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("notes")) {
                    getDescription.setText(cbCommands.getValue() + " [INDEX] /add [NOTES DESCRIPTION] \n"
                            + cbCommands.getValue() + " /delete \n"
                            + cbCommands.getValue() + " /show");
                    getExample.setText(cbCommands.getValue() + " 1 /add read page 578"
                            + cbCommands.getValue() + " 1 /delete \n"
                            + cbCommands.getValue() + " 1 /show");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("filter")) {
                    getDescription.setText(cbCommands.getValue() + " [TASK TYPE]");
                    getExample.setText(cbCommands.getValue() + " deadline");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("update")) {
                    getDescription.setText(cbCommands.getValue() + " [INDEX] /desc [DESCRIPTION] \n"
                            + cbCommands.getValue() + " [INDEX] /date [DATE/TIME] \n"
                            + cbCommands.getValue() + " [INDEX] /type [TASK TYPE]");
                    getExample.setText(cbCommands.getValue() + " 1 /desc running \n"
                            + cbCommands.getValue() + " 1 /date 27/10/2019 2359 \n"
                            + cbCommands.getValue() + " 1 /type todo");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("setpriority")) {
                    getDescription.setText(cbCommands.getValue() + " [TASK NUM] [PRIORITY NUM]");
                    getExample.setText(cbCommands.getValue() + " 1 5");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("findpriority")) {
                    getDescription.setText(cbCommands.getValue() + " [PRIORITY NUM]");
                    getExample.setText(cbCommands.getValue() + " 5");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("finddate")) {
                    getDescription.setText(cbCommands.getValue() + " /on [DATE]");
                    getExample.setText(cbCommands.getValue() + " /on 01/12/2019");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("list")) {
                    getDescription.setText(cbCommands.getValue());
                    getExample.setText(cbCommands.getValue());
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("done")) {
                    getDescription.setText(cbCommands.getValue() + " [INDEX]");
                    getExample.setText(cbCommands.getValue() + " 1");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("find")) {
                    getDescription.setText(cbCommands.getValue() + " [KEYWORD] [MORE_KEYWORDS]");
                    getExample.setText(cbCommands.getValue() + " /meeting Christian");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("delete")) {
                    getDescription.setText(cbCommands.getValue() + " [INDEX]");
                    getExample.setText(cbCommands.getValue() + " 3");
                }
            }
        };
        cbCommands.setOnAction(event);
    }


}