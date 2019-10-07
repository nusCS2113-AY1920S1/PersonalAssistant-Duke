package seedu.duke.email.command;

import seedu.duke.Duke;
import seedu.duke.email.entity.EmailList;
import seedu.duke.Parser;
import seedu.duke.common.command.Command;

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
        try {
            responseMsg = emailList.show(index);
            Duke.getUI().showResponse(responseMsg);
            return true;
        } catch (Parser.UserInputException | IOException e) {
            if (!silent) {
                Duke.getUI().showError(e.toString());
            }
            return false;
        }
    }
}
