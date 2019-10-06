package seedu.duke.email.emailcommand;

import seedu.duke.Duke;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.command.Command;

public class ListEmailCommand extends Command {
    private EmailList emailList;

    public ListEmailCommand(EmailList emailList) {
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
