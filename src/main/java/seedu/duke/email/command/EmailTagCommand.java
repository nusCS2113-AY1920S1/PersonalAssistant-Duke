package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;

import java.util.ArrayList;

/**
 * Command to add tags to emails.
 */
public class EmailTagCommand extends Command {
    private EmailList emailList;
    private int index;
    private ArrayList<String> tags;

    /**
     * Instantiates attributes for command.
     * @param emailList list of emails
     * @param index specific email which tags should be added to
     * @param tags tags to be added to the email
     */
    public EmailTagCommand(EmailList emailList, int index, ArrayList<String> tags) {
        this.emailList = emailList;
        this.index = index;
        this.tags = tags;
    }

    @Override
    public boolean execute() {
        String responseMsg = emailList.addTags(index, tags);
        Duke.getUI().showResponse(responseMsg);
        return true;
    }
}
