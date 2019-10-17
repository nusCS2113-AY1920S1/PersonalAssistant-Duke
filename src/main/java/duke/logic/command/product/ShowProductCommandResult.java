package duke.logic.command.product;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;

public class ShowProductCommandResult extends CommandResult {

    private Index index;
    public ShowProductCommandResult(String feedbackToUser, DisplayedPage displayedPage, Index index) {
        super(feedbackToUser, displayedPage);
        this.index = index;
    }
    public Index getIndex() {
        return this.index;
    }
}
