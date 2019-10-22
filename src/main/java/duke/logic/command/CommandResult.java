package duke.logic.command;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, DisplayedPage.SAME, false);
    }

    private final String feedbackToUser;

    /**
     * The page that should be displayed to the user.
     */
    private final DisplayedPage displayedPage;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, DisplayedPage displayedPage, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.displayedPage = displayedPage;
        this.exit = exit;
    }

    public CommandResult(String feedbackToUser, DisplayedPage displayedPage) {
        this(feedbackToUser, displayedPage, false);
    }

    /**
     * The page shown to the user.
     */
    public enum DisplayedPage {
        PRODUCT,
        ORDER,
        INVENTORY,
        SALE,
        SAME
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DisplayedPage getDisplayedPage() {
        return displayedPage;
    }

    public boolean isExit() {
        return exit;
    }


}
