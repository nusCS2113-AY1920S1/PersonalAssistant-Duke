package dolla.ui;

//@@author omupenguin
public class ModifyUi extends Ui {
    /**
     * Prints out error message when the user inputs the wrong format for 'modify' command.
     */
    public static void printInvalidModifyFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'modify [LIST NUM]"
                + "");
        System.out.println(line);
    }

    /**
     * Prints a message asking what the user wants to modify the selected log to.
     */
    public static void printInitialModifyMsg(String mode) {
        System.out.println(line);
        System.out.println("\tWhat would you want to change this " + mode + " to?");
        System.out.println(line);
    }
}
