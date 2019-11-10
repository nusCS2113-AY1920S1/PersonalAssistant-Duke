package duke.logic;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /**
     * Represents all panes that can be displayed in the main window.
     * This is used when the displayed pane is to be switched due to the command operation.
     */
    public enum DisplayedPane {
        EXPENSE,
        TRENDING,
        BUDGET,
        PLAN,
        PAYMENT,
    }

    /**
     * The feedback message to be displayed in the console.
     */
    private String consoleInfo;

    /**
     * The pane to be displayed.
     */
    private DisplayedPane displayedPane;

    /**
     * The application should exit.
     */
    private boolean isExit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     *
     * @param consoleInfo    The message to be displayed in the console.
     * @param displayedPane  The pane to be displayed in the main window.
     * @param isExit         Whether the application should exit.
     */
    public CommandResult(String consoleInfo, DisplayedPane displayedPane, boolean isExit) {
        this.consoleInfo = requireNonNull(consoleInfo);
        this.displayedPane = requireNonNull(displayedPane);
        this.isExit = isExit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code consoleInfo} and {@code displayedPane}.
     * The field {@code isExit} is set to its default value false.
     *
     * @param consoleInfo    The message to be displayed in the console.
     * @param displayedPane  The pane to be displayed in the main window.
     */
    public CommandResult(String consoleInfo, DisplayedPane displayedPane) {
        this(requireNonNull(consoleInfo), requireNonNull(displayedPane), false);
    }

    public String getConsoleInfo() {
        return consoleInfo;
    }

    public DisplayedPane getDisplayedPane() {
        return displayedPane;
    }

    public boolean isExit() {
        return isExit;
    }
}
