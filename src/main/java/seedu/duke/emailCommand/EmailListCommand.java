package seedu.duke.emailCommand;

import seedu.duke.Duke;
import seedu.duke.EmailList;
import seedu.duke.command.Command;

public class EmailListCommand extends Command {
    private EmailList emailList;

    public EmailListCommand(EmailList emailList) {
        this.emailList = emailList;
    }

    @Override
    public boolean execute() {
        if (!silent) {
            try {
                Duke.getUI().showResponse(this.emailList.toString());
            } catch (Exception e) {
                Duke.getUI().showError(e.toString());
            }
        }
        return true;
    }

}
