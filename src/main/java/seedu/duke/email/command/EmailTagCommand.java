package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;

import java.util.ArrayList;

/**
 * Command to add tags to emails.
 */
public class EmailTagCommand extends Command {
    private int index;
    private ArrayList<String> tags;

    /**
     * Instantiates attributes for command.
     *
     * @param index specific email which tags should be added to
     * @param tags  tags to be added to the email
     */
    EmailTagCommand(int index, ArrayList<String> tags) {
        this.index = index;
        this.tags = tags;
    }

    @Override
    public boolean execute() {
        EmailList emailList = Duke.getModel().getEmailList();
        responseMsg = emailList.addTags(index, tags);
        Duke.getUI().showResponse(responseMsg);
        return true;
    }
}
