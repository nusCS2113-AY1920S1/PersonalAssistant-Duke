package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.task.TaskList;
import seedu.duke.ui.UI;

public class EmailClearCommand extends Command {

    /**
     * Executes the clear command by calling the clear list function of task list.
     *
     * @return a flag whether clearing of task list is done successfully. Returns false if the delete
     *     function of task list throws an exception
     */
    @Override
    public boolean execute(Model model) {
        EmailList emailList = model.getEmailList();
        responseMsg = emailList.clearList();
        UI.getInstance().showResponse(responseMsg);
        return true;
    }
}
