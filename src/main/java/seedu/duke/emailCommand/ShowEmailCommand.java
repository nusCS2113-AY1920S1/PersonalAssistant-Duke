package seedu.duke.emailCommand;

import seedu.duke.Duke;
import seedu.duke.EmailList;
import seedu.duke.Parser;
import seedu.duke.command.Command;

import java.io.IOException;

public class ShowEmailCommand extends Command {
    private EmailList emailList;
    private int index;

    public ShowEmailCommand(EmailList emailList, int index) {
        this.emailList = emailList;
        this.index = index;
    }

    @Override
    public boolean execute() {
        try {
            String responseMsg =  emailList.show(index);
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
