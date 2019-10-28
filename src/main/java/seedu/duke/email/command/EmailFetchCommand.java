package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.storage.EmailStorage;
import seedu.duke.ui.UI;

public class EmailFetchCommand extends Command {

    public EmailFetchCommand() {
    }

    @Override
    public boolean execute(Model model) {
        if (!silent) {
            EmailStorage.syncWithServer();
            responseMsg = "Fetching emails and syncing with local storage...\n\n";
            responseMsg += model.getEmailList().toString();
            UI.getInstance().showResponse(responseMsg);
        }
        return true;
    }
}
