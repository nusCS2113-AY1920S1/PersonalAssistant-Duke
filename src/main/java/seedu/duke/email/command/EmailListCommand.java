package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;

public class EmailListCommand extends Command {

    EmailListCommand() {
    }

    @Override
    public boolean execute(Model model) {
        if (!silent) {
            EmailList emailList = model.getEmailList();
            try {
                responseMsg += emailList.toString();
                Duke.getUI().showResponse(responseMsg);
            } catch (Exception e) {
                Duke.getUI().showError(e.toString());
            }
        }
        return true;
    }
}
