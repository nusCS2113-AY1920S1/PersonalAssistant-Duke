package duke.logic.command.product;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;

/**
 * This class carries the index of the product to be shown besides other {@code CommandResult} information.
 */
public class ShowProductCommandResult extends CommandResult {

    private Index index;

    /**
     * Construct a ShowProductCommandResult.
     */
    public ShowProductCommandResult(String feedbackToUser, DisplayedPage displayedPage, Index index) {
        super(feedbackToUser, displayedPage);
        this.index = index;
    }

    /** Returns the index in the ShowProductCommand. */
    public Index getIndex() {
        return this.index;
    }
}
