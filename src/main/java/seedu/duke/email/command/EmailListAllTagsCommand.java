package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailTags;
import seedu.duke.ui.UI;

public class EmailListAllTagsCommand extends Command {

    @Override
    public boolean execute(Model model) {
        responseMsg = "Tags exist in the emails: " + System.lineSeparator();
        responseMsg += EmailTags.getEmailTagList().toString();
        UI.getInstance().showResponse(responseMsg);
        return true;
    }
}
