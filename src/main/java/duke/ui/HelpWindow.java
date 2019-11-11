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
    private Label getFormat;

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
                "finddate",
                "backup",
                "friendlier syntax"
        );

        /**
         * Create action event when command is selected from drop down list
         */
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cbCommands.getSelectionModel().getSelectedItem().equals("todo")) {
                    getDescription.setText(" Create a new 'to-do' task");
                    getFormat.setText(cbCommands.getValue() + " [TASK]");
                    getExample.setText(cbCommands.getValue() + " CS2113 assignment");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("deadline")) {
                    getDescription.setText(" Create a new task with deadline");
                    getFormat.setText(cbCommands.getValue() + " [TASK] /by [DD/MM/YYYY HHMM] \n");
                    getExample.setText(cbCommands.getValue() + " finish reading book /by 27/10/2019 2359");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("fixedduration")) {
                    getDescription.setText(" Create a new task with fixed duration");
                    getFormat.setText(cbCommands.getValue() + " [TASK] /for [DURATION] [UNITS] \n");
                    getExample.setText(cbCommands.getValue() + " build a robot /for 3 hour");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("budget")) {
                    getDescription.setText("Create a new budget / Reset budget \n"
                            + "Add / Deducts existing budget \n"
                            + "View available budget \n"
                            + "Undo previous budget");
                    getFormat.setText(cbCommands.getValue() + " new/reset [AMOUNT] \n"
                            + cbCommands.getValue() + " add/+/minus/- [AMOUNT] [(OPTIONAL)DESCRIPTION] \n"
                            + cbCommands.getValue() + " view \n"
                            + cbCommands.getValue() + " undo");
                    getExample.setText(cbCommands.getValue() + " new 100 \n"
                            + cbCommands.getValue() + " add 21.13 pay school fees \n"
                            + cbCommands.getValue() + " view \n"
                            + cbCommands.getValue() + " undo");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("addcontact")) {
                    getDescription.setText(" Add a new contact");
                    getFormat.setText(cbCommands.getValue() + " [NAME], [NUMBER], [EMAIL], [OFFICE]");
                    getExample.setText(cbCommands.getValue() + " Prof Tan, 91234567, tancc@nus.edu.sg, E1-08-11");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("listcontacts")) {
                    getDescription.setText(" List all contacts");
                    getFormat.setText(cbCommands.getValue());
                    getExample.setText(cbCommands.getValue());
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("findcontact")) {
                    getDescription.setText(" Find a contact");
                    getFormat.setText(cbCommands.getValue() + " [KEYWORD]");
                    getExample.setText(cbCommands.getValue() + " Prof Tan");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("deletecontact")) {
                    getDescription.setText(" Delete a specific contact");
                    getFormat.setText(cbCommands.getValue() + " [INDEX]");
                    getExample.setText(cbCommands.getValue() + " 1");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("notes")) {
                    getDescription.setText(" Add / Update notes to this existing task \n"
                            + "Shows notes of this existing task \n"
                            + "Delete notes of this existing task");
                    getFormat.setText(cbCommands.getValue() + "[INDEX] /add [NOTES DESCRIPTION] \n"
                            + cbCommands.getValue() + " /show \n"
                            + cbCommands.getValue() + " /delete");
                    getExample.setText(cbCommands.getValue() + " 1 /add read page 578 \n"
                            + cbCommands.getValue() + " 1 /show \n"
                            + cbCommands.getValue() + " 1 /delete");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("filter")) {
                    getDescription.setText(" Filters tasks of the same type");
                    getFormat.setText(cbCommands.getValue() + " [TASK TYPE]");
                    getExample.setText(cbCommands.getValue() + " deadline");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("update")) {
                    getDescription.setText(" Updates description of an existing task \n"
                            + "Updates date and time of this existing task \n"
                            + "Updates the task type of this existing task");
                    getFormat.setText(cbCommands.getValue() + " [INDEX] /desc [DESCRIPTION] \n"
                            + cbCommands.getValue() + " [INDEX] /date [DATE/TIME] \n"
                            + cbCommands.getValue() + " [INDEX] /type [TASK TYPE]");
                    getExample.setText(cbCommands.getValue() + " 1 /desc running \n"
                            + cbCommands.getValue() + " 1 /date 27/10/2019 2359 \n"
                            + cbCommands.getValue() + " 1 /type todo");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("priority")) {
                    getDescription.setText(" List existing tasks with priority");
                    getFormat.setText(cbCommands.getValue());
                    getExample.setText(cbCommands.getValue());
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("setpriority")) {
                    getDescription.setText(" Sets priority of a task");
                    getFormat.setText(cbCommands.getValue() + " [TASK NUM] [PRIORITY NUM]");
                    getExample.setText(cbCommands.getValue() + " 1 5");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("findpriority")) {
                    getDescription.setText(" Find tasks based on a specified priority");
                    getFormat.setText(cbCommands.getValue() + " [PRIORITY NUM]");
                    getExample.setText(cbCommands.getValue() + " 5");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("finddate")) {
                    getDescription.setText(" Find a list of tasks on a certain date");
                    getFormat.setText(cbCommands.getValue() + " /on [DATE]");
                    getExample.setText(cbCommands.getValue() + " /on 01/12/2019");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("list")) {
                    getDescription.setText(" List all tasks available in Duke Manager");
                    getFormat.setText(cbCommands.getValue());
                    getExample.setText(cbCommands.getValue());
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("done")) {
                    getDescription.setText(" Marking a task as 'completed' ");
                    getFormat.setText(cbCommands.getValue() + " [INDEX]");
                    getExample.setText(cbCommands.getValue() + " 1");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("find")) {
                    getDescription.setText(" Find particular tasks by task description");
                    getFormat.setText(cbCommands.getValue() + " [KEYWORD] [MORE_KEYWORDS]");
                    getExample.setText(cbCommands.getValue() + " /meeting Christian");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("delete")) {
                    getDescription.setText(" Delete an existing task");
                    getFormat.setText(cbCommands.getValue() + " [INDEX]");
                    getExample.setText(cbCommands.getValue() + " 3");
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("backup")) {
                    getDescription.setText(" Saves the state of the data and shows the data folder");
                    getFormat.setText(cbCommands.getValue());
                    getExample.setText(cbCommands.getValue());
                } else if (cbCommands.getSelectionModel().getSelectedItem().equals("friendlier syntax")) {
                    getDescription.setText(" Alternative shortened commands for inputting the equivalent commands.\n"
                                            + " Format is as follows : | command: shortened cmd |");
                    getFormat.setText(" | deadline: dl | fixedduration: fd | setpriority: sp |"
                            + " findpriority: fp | priority: lp |\n"
                            + " | addcontacts: ac | listcontacts: lc | deletecontacts: dc | findcontacts: fc |\n"
                            + " | budget add: budget + | budget minus: budget - | budget view: budget list |\n");
                    getExample.setText(" ac Prof Lim, 81234567, limkopi@nus.edu.sg, Singapore \n"
                                        + " dl computing project /by 19/11/2019 2359 \n"
                                        + " budget - 1000 New Laptop ");
                }
            }
        };
        cbCommands.setOnAction(event);
    }


}