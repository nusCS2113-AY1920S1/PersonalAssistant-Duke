package seedu.duke.logic;

import seedu.duke.ui.Ui;

public class CommandLineException extends Exception {

    public CommandLineException(String message) {
        super(message);
    }

    public void getErrorMsg() {
        System.out.println(Ui.LINE);
        System.out.println(this.getMessage());
        System.out.println(Ui.LINE);
    }

}
