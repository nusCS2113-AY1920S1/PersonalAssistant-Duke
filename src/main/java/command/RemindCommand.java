package command;

import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a specified command as RemindCommand by extending the {@code Command} class.
 * Lists all uncompleted tasks in Duke
 * Responses with the result.
 */
public class RemindCommand extends Command {
    /**
     * Constructs a {@code RemindCommand} object.
     */
    public RemindCommand() {
        super(null, null, null, null);
    }

    /**
     * Lists all uncompleted tasks in taskList of Duke by using ui of Duke.
     *
     * @param tasks   The taskList of Duke.
     * @param ui      The ui of Duke.
     * @param storage The storage of Duke.
     */
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) {
        int count = 0;
        ArrayList<String> toPrint = new ArrayList<>();
        for (int i = 0; i < tasks.getSize(); i++) {
            if (!tasks.isDone(i)) {
                toPrint.add((++count) + "." + tasks.getTaskInfo(i));
            }
        }

        if (count == 0) {
            ui.println("Congrats, you have no upcoming uncompleted tasks!");
        } else {
            ui.println("[Reminder] You have " + count + " uncompleted tasks due:");
            for (String i: toPrint) {
                ui.println(i);
            }
        }
    }
}
