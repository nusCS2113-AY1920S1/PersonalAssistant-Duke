package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailTags;

import java.util.ArrayList;

public class EmailListTagCommand extends Command {
    private ArrayList<String> tags;

    public EmailListTagCommand(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean execute() {
        responseMsg = EmailTags.displayEmailTagList(tags);
        Duke.getUI().showResponse(responseMsg);
        return true;
    }

}