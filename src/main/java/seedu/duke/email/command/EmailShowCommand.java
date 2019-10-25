package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;


public class EmailShowCommand extends Command {
    private int index;

    EmailShowCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(Model model) {
        EmailList emailList = model.getEmailList();
        String[] parsedMsg = emailList.show(index);
        responseMsg = parsedMsg[0];
        Duke.getUI().showResponse(parsedMsg[0]);
        Duke.getUI().setEmailContent(parsedMsg[1]);
        return true;
    }
}
