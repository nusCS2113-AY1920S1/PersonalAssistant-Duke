package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;

/**
 * Command that adds task to list.
 */
public class AddCommand extends Command {
    
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Initializes taskInfo.
     *
     * @param taskInfo the info of the task to add.
     */
    public AddCommand(String[] taskInfo) {
        this.taskInfo = taskInfo;
    }
    
    /**
     * Adds task of type and description inside taskInfo.
     *
     */
    public void execute() {
        Ui ui = new Ui();
        if (this.taskInfo.length == 1) {
            ui.empty_description_error();
            CommandLog.deleteLatestLoggedCommand();
            return;
        }

        String[] taskDescription = this.taskInfo[1].split("/");

        if (taskDescription.length == 1) {
            Hustler.list.add("todo", taskDescription[0]);
            return;
        }

        String taskType = "";
        
        String timeCommand = taskDescription[1].split(" ")[0];
        switch (timeCommand) {
        case "by":
            taskType = "deadline";
            break;
        case "at":
            taskType = "event";
            break;
        default:
            ui.show_message("/" + timeCommand + " is an invalid addition to /add");
            return;
        }
        Hustler.list.add(taskType, this.taskInfo[1]);
    }
}
