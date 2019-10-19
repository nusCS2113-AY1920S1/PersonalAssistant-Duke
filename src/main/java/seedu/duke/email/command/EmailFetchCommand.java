package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.common.command.Command;

public class EmailFetchCommand extends Command {
    private EmailList emailList;

    public EmailFetchCommand(EmailList emailList) {
        this.emailList = emailList;
    }

    @Override
    public boolean execute() {
        if (!silent) {
            try {
                // To fetch email from server, and save them to local storage.
                // EmailStorage.syncWithServer();

                // Sync the current emailList with the html files in local storage.
                EmailStorage.syncWithServer();
                responseMsg = "Fetching emails and syncing with local storage...\n\n";
                responseMsg += Duke.getModel().getEmailList().toString();
                Duke.getUI().showResponse(responseMsg);
            } catch (Exception e) {
                Duke.getUI().showError(e.toString());
                return false;
            }
        }
        return true;
    }
}
