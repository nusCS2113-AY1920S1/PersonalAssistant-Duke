package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;

import java.util.ArrayList;

public class EmailTagCommand extends Command {
    private EmailList emailList;
    private int index;
    private ArrayList<String> tags;

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
