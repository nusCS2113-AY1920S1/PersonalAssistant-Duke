package seedu.hustler.command;

import seedu.hustler.Hustler;
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
        if (this.taskInfo.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        Hustler.list.add(this.taskInfo[0], this.taskInfo[1]);
    }
}
