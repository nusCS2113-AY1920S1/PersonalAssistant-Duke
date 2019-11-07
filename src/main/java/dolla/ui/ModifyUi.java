package dolla.ui;

import dolla.model.Record;

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
        System.out.println("\tPS: You can cancel the modifying of the record by entering 'CANCEL'.");
        System.out.println(line);
    }

    /**
     * Prints a message to alert the user that the modify command has been cancelled.
     */
    public static void printCancelModifyMsg() {
        System.out.println(line);
        System.out.println("\tOkay, modify command is cancelled! Sorry for the troubles!");
        System.out.println(line);
    }

    /**
     * Prints the details of the specified record and is typically called when a is modified,
     * so that the user can check the details of the edited record.
     *
     * @param currRecord record to be printed, can be an entry, limit or debt.
     */
    public static void echoModifyRecord(Record currRecord) {
        System.out.println(line);
        System.out.println("\tGot it. I've modified this " + currRecord.getRecordType() + ": ");
        System.out.println("\t" + currRecord.getRecordDetail());
        System.out.println(line);
    }

    /**
     * Prints a message to alert user that they did not add the new information that they want to
     * use after indicating the component they want to change.
     * @param component
     */
    public static void printMissingComponentInfoError(String component) {
        System.out.println(line);
        System.out.println("\tHey! You forgot to tell me what you want to modify " + component +  " to!");
        System.out.println(line);
    }
}
