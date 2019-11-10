package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.ui.UI;


public class EmailDeleteCommand extends Command {
    private int index;

    public EmailDeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(Model model) {
        EmailList emailList = model.getEmailList();
        responseMsg = emailList.delete(index);
        UI.getInstance().showResponse(responseMsg);
        return true;
    }
}
