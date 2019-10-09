package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.common.command.Command;

public class EmailListCommand extends Command {
    private EmailList emailList;

    public EmailListCommand(EmailList emailList) {
        this.emailList = emailList;
    }

    @Override
    public boolean execute() {
        if (!silent) {
            try {
                EmailList syncedEmailList = EmailStorage.syncEmailListWithHtml(emailList);
                Duke.setEmailList(syncedEmailList);
                responseMsg = "Syncing email list with local storage...\n\n";
                responseMsg += Duke.getEmailList().toString();
                Duke.getUI().showResponse(responseMsg);
            } catch (Exception e) {
                Duke.getUI().showError(e.toString());
            }
        }
        return true;
    }
}
