package seedu.duke.email.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.ui.UI;

public class EmailFuzzySearchCommand extends Command {
    private String target;

    public EmailFuzzySearchCommand(String target) {
        this.target = target;
    }

    @Override
    public boolean execute(Model model) {
        responseMsg = model.getEmailList().fuzzySearch(target);
        UI.getInstance().showMessage(responseMsg);
        return true;
    }
}
