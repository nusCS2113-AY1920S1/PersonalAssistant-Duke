package logic.command;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Details the output of a command
 */
public class CommandOutput {

    private final String responseToUser;


    /**
     * The project manager should exit.
     */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandOutput(String feedbackToUser, boolean exit) {
        this.responseToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandOutput(String feedbackToUser) {
        this(feedbackToUser, false);
    }

    public String getOutputToUser() {
        return responseToUser;
    }


    public boolean isExit() {
        return exit;
    }


}