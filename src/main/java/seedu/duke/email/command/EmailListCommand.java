package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.ui.UI;

public class EmailListCommand extends Command {

    EmailListCommand() {
    }

    @Override
    public boolean execute(Model model) {
        if (!silent) {
            EmailList emailList = model.getEmailList();
            try {
                responseMsg += emailList.toString();
                UI.getInstance().showResponse(responseMsg);
            } catch (Exception e) {
                UI.getInstance().showError(e.toString());
            }
        }
        return true;
    }
}
