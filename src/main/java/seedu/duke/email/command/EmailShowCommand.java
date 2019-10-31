package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.ui.UI;


public class EmailShowCommand extends Command {
    private int index;

    public EmailShowCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(Model model) {
        EmailList emailList = model.getEmailList();
        String[] parsedMsg = emailList.show(index);
        responseMsg = parsedMsg[0];
        UI.getInstance().showResponse(parsedMsg[0]);
        UI.getInstance().setEmailContent(parsedMsg[1]);
        UI.getInstance().updateHtml();
        return true;
    }
}
