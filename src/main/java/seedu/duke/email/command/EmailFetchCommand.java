package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailStorage;

public class EmailFetchCommand extends Command {

    EmailFetchCommand() {
    }

    @Override
    public boolean execute() {
        if (!silent) {
            EmailStorage.syncWithServer();
            responseMsg = "Fetching emails and syncing with local storage...\n\n";
            responseMsg += Duke.getModel().getEmailList().toString();
            Duke.getUI().showResponse(responseMsg);
        }
        return true;
    }
}
