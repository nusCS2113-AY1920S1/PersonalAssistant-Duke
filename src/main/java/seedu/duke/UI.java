package seedu.duke;

public class UI {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static boolean debug = false;

    /**
     * Instantiates the UI component, which also display the welcoming message.
     */
    public UI() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        logo = "Hello from\n" + logo + "\n";
        logo += "What can I do for you?";
        showMessage(logo);
    }

    public void setDebug(boolean flag) {
        debug = flag;
    }

    /**
     * Shows a simple message without any format.
     *
     * @param msg the message that is to be shown
     */
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Shows a message in the format of a response, which is in between two lines.
     *
     * @param msg the message that is to be shown
     */
    public void showResponse(String msg) {
        System.out.println("------------------------------");
        System.out.println(msg);
        System.out.println("------------------------------\n");
    }

    /**
     * Shows an error message in the red color.
     *
     * @param msg the error message that is to be shown
     */
    public void showError(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
    }

    /**
     * Shows a debug message when debug flag is on in yellow color.
     *
     * @param msg the debug message that is to be shown
     */
    public void showDebug(String msg) {
        if (debug) {
            System.out.println(ANSI_YELLOW + msg + ANSI_RESET);
        }
    }

}
