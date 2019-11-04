package duke.logic.command;

import static java.util.Objects.requireNonNull;


/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    public CommandResult(boolean isExiting) {

        //Dummy values to word around the field not declared exception.
        this("", DisplayedPage.ORDER, true);

        if (isExiting) {
            exit();
        }
    }

    /**
     * Constructs a {@code CommandResult}.
     *
     * @param feedbackToUser Message to display to user.
     * @param displayedPage  Page to display to user.
     * @param isExiting      true if the command requires the app to exit.
     */
    public CommandResult(String feedbackToUser, DisplayedPage displayedPage, boolean isExiting) {
        if (isExiting) {
            System.exit(0);
        }

        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.displayedPage = requireNonNull(displayedPage);
    }

    public CommandResult(String feedbackToUser, DisplayedPage displayedPage) {
        this(feedbackToUser, displayedPage, false);
    }

    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, DisplayedPage.SAME, false);
    }

    private void exit() {
        Runtime.getRuntime().exit(0);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DisplayedPage getDisplayedPage() {
        return displayedPage;
    }

    /**
     * The page shown to the user.
     */
    public enum DisplayedPage {
        PRODUCT,
        ORDER,
        INVENTORY,
        SHOPPING,
        SALE,
        SAME
    }

    private final String feedbackToUser;

    /**
     * The page that should be displayed to the user.
     */
    private final DisplayedPage displayedPage;




}