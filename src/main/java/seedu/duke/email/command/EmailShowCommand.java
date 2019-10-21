package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;


public class EmailShowCommand extends Command {
    private int index;

    EmailShowCommand(int index) {
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
