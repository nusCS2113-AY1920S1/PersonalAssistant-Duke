package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailTags;

import java.util.ArrayList;

public class EmailListTagCommand extends Command {
    private EmailList emailList;
    private ArrayList<String> tags;

    public EmailListTagCommand(EmailList emailList, ArrayList<String> tags) {
        this.emailList = emailList;
        this.tags = tags;
    }

    @Override
    public boolean execute() {
        String responseMsg = EmailTags.displayEmailTagList(tags);
        Duke.getUI().showResponse(responseMsg);
        return true;
    }

}