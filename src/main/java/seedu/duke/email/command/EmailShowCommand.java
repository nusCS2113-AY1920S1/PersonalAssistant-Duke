package seedu.duke.email.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;

import java.io.IOException;

public class EmailShowCommand extends Command {
    private EmailList emailList;
    private int index;

    public EmailShowCommand(EmailList emailList, int index) {
        this.emailList = emailList;
        this.index = index;
    }

    @Override
    public boolean execute() {
        String[] parsedMsg = emailList.show(index);
        responseMsg = parsedMsg[0];
        Duke.getUI().showResponse(parsedMsg[0]);
        Duke.getUI().setEmailContent(parsedMsg[1]);
        return true;
    }
}
