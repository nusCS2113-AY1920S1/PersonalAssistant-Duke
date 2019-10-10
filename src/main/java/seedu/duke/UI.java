package seedu.duke;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import seedu.duke.gui.DialogBox;

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

    // to output result to GUI
    private VBox dialogContainer;
    private Image userImage;
    private Image dukeImage;
    private String input = "";
    private String command = "";

    private boolean isGuiSet = false;

    // variable returned to GUI
    private String emailPath = "";
    private String responseMsg = "";

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
        showGui(msg);
    }

    /**
     * Shows a message in the format of a response, which is in between two lines.
     *
     * @param msg the message that is to be shown
     */
    public void showResponse(String msg) {
        this.responseMsg = msg;
        System.out.println("------------------------------");
        System.out.println(msg);
        System.out.println("------------------------------\n");
        showGui(msg);
    }

    /**
     * Shows an error message in the red color.
     *
     * @param msg the error message that is to be shown
     */
    public void showError(String msg) {
        String errorMsg = ANSI_RED + msg + ANSI_RESET;
        System.out.println(errorMsg);
        showGui(errorMsg);
    }

    /**
     * Shows a debug message when debug flag is on in yellow color.
     *
     * @param msg the debug message that is to be shown
     */
    public void showDebug(String msg) {
        String debugMsg = ANSI_YELLOW + msg + ANSI_RESET;
        if (debug) {
            System.out.println(debugMsg);
        }
        showGui(debugMsg);
    }

    public void setEmailPath(String emailPath) {
        this.emailPath = emailPath;
    }

    public String getEmailPath() {
        return this.emailPath;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    /**
     * Setup GUI elements.
     *
     * @param dialogContainer VBox that contains all chats
     * @param userImage       User avatar
     * @param dukeImage       Duke avatar
     */
    public void setupGui(VBox dialogContainer, Image userImage, Image dukeImage) {
        this.dialogContainer = dialogContainer;
        this.userImage = userImage;
        this.dukeImage = dukeImage;
        isGuiSet = true;
    }

    public void showGui(String msg) {
        if (!isGuiSet) {
            return;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(command + "\n\n" + msg, dukeImage)
        );
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
