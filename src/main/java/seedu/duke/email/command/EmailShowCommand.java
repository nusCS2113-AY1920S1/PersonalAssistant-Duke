package seedu.duke.email.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;

import java.io.IOException;

public class EmailShowCommand extends Command {
    private int index;

    public EmailShowCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute() {
        EmailList emailList = Duke.getModel().getEmailList();
        String[] parsedMsg = emailList.show(index);
        responseMsg = parsedMsg[0];
        Duke.getUI().showResponse(parsedMsg[0]);
        Duke.getUI().setEmailContent(parsedMsg[1]);
        return true;
    }
}
