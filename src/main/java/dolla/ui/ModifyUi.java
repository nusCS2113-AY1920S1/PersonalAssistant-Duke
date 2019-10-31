package dolla.ui;

//@@author omupenguin
public class ModifyUi extends Ui {

    /**
     * Prints out error message when the user inputs the wrong format for 'full modify' command.
     */
    public static void printInvalidFullModifyFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'modify [LIST NUM]"
                + "");
        System.out.println(line);
    }

    /**
     * Prints out error message when the user inputs the wrong format for 'partial modify' command.
     */
    public static void printInvalidPartialModifyFormatError() {
        System.out.println(line);
        System.out.println("\tFor quick modify, please follow the format "
                + "\n\t'modify [LIST NUM] {/type [TYPE]} {/amount [AMOUNT]} {/desc [DESCRIPTION]} {/on [DATE]}'"
                + "\n\tinclude the fields for whatever component you want to change :)");
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

    public static void printSingleStringModifyError() {
        System.out.println(line);
        System.out.println("\tPlease write your corrected record as if you're recreating a whole new one!");
        System.out.println(line);
    }
}
