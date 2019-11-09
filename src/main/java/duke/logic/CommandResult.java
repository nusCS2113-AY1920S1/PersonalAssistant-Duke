package duke.logic;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public enum DisplayedPane {
        EXPENSE,
        TRENDING,
        BUDGET,
        PLAN,
        PAYMENT,
        PAYMENT_SEARCH;
        // todo: add more custom pages.
    }

    private String consoleInfo;

    private DisplayedPane displayedPane;

    private boolean isExit;

    /**
     *
     *
     * @param consoleInfo
     * @param displayedPane
     * @param isExit
     */
    public CommandResult(String consoleInfo, DisplayedPane displayedPane, boolean isExit) {
        this.consoleInfo = consoleInfo;
        this.displayedPane = displayedPane;
        this.isExit = isExit;
    }

    public CommandResult(String consoleInfo, DisplayedPane displayedPane) {
        this(consoleInfo, displayedPane, false);
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
