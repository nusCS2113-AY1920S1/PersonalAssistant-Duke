package compal.logic.command;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;
    public final boolean requireSaving;

    /**
     * Return command results.
     *
     * @param feedbackToUser the string feedback
     */
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        requireSaving = false;
    }

    /**
     * Return command results.
     *
     * @param feedbackToUser the string feedback
     * @param requireSaving if command require saving
     */
    public CommandResult(String feedbackToUser, boolean requireSaving) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.requireSaving = requireSaving;
    }

}
