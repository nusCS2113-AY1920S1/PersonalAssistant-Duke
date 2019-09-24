package seedu.duke.emailCommand;

import seedu.duke.Duke;
import seedu.duke.EmailList;
import seedu.duke.EmailStorage;
import seedu.duke.command.Command;

public class fetchEmailCommand extends Command {
    private EmailList emailList;

    public fetchEmailCommand(EmailList emailList) {
        this.emailList = emailList;
    }

    @Override
    public boolean execute() {
        if (!silent) {
            try {
                // To fetch email from server, and save them to local storage.
                // EmailStorage.syncWithServer();

                // Sync the current emailList with the html files in local storage.
                EmailList syncedEmailList= EmailStorage.syncEmailListWithHtml(emailList);
                Duke.setEmailList(syncedEmailList);
                String responseMsg =  "Fetching emails and syncing with local storage...\n\n";
                responseMsg += Duke.getEmailList().toString();
                Duke.getUI().showResponse(responseMsg);
            } catch (Exception e) {
                Duke.getUI().showError(e.toString());
                return false;
            }
        }
        return true;
    }
}
