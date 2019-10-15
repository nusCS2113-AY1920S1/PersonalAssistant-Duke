package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static compal.commons.Messages.MESSAGE_INVALID_PRIORITY;

/**
 * Edit command is the command object handling the editing of tasks.
 * Syntax: edit task id /field-to-edit new value
 * Currently only 1 editable field per edit command. Will slowly implement changing of multiple fields.
 *
 * @author jaedonkey
 */
public class EditCommand extends Command implements CommandParser {


    /**
     * Constructs a Command object.
     *
     * @param d Compal object
     */
    public EditCommand(Compal d) {
        super(d);
    }

    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner sc = new Scanner(userIn);
        sc.next(); //skip command string
        int taskId = sc.nextInt();
        Task taskToEdit = compal.tasklist.getTaskById(taskId);
        while (sc.hasNext()) {
            String fieldToEdit = sc.next().replace("/","");
            String newValue = sc.nextLine();

            //for now only 2 cases for testing purposes
            switch (fieldToEdit) {
            case "description":
                taskToEdit.setDescription(newValue);
                break;
            case "priority":
                switch (newValue.trim()) {
                case "high":
                    taskToEdit.setPriority(Task.Priority.high);
                    break;
                case "medium":
                    taskToEdit.setPriority(Task.Priority.high);
                    break;
                case "low":
                    taskToEdit.setPriority(Task.Priority.high);
                    break;
                default:
                    throw new Compal.DukeException(MESSAGE_INVALID_PRIORITY);

                }
                break;

            default:
                System.out.println("EditCommand:LOG: Default case");
            }


        }

        //save the changes
        compal.storage.saveCompal(compal.tasklist.arrlist);
    }
}
