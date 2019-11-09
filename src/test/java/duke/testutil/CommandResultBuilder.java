package duke.testutil;

import duke.logic.command.CommandResult;

public class CommandResultBuilder {

    private static final String DEFAULT_FEEDBACK = "";
    private static final CommandResult.DisplayedPage DEFAULT_PAGE = CommandResult.DisplayedPage.INVENTORY;

    public static final CommandResult.DisplayedPage PAGE_PRODUCT = CommandResult.DisplayedPage.PRODUCT;

    private String feedbackToUser;

    /**
     * The page that should be displayed to the user.
     */
    private CommandResult.DisplayedPage displayedPage;

    public CommandResultBuilder() {
        this.feedbackToUser = DEFAULT_FEEDBACK;
        this.displayedPage = DEFAULT_PAGE;
    }

    public CommandResultBuilder withMessage(String message) {
        this.feedbackToUser = message;
        return this;
    }

    public CommandResultBuilder withPage(CommandResult.DisplayedPage page) {
        this.displayedPage = page;
        return this;
    }

    public CommandResult build() {
        return new CommandResult(feedbackToUser, displayedPage);
    }

}
