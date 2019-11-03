package dolla.ui;

public class RemoveUi extends Ui {
    //@@author yetong1895
    /**
     * This method will print the error message if user is trying trying to remove a non-existing record.
     * @param number the total number of record.
     */
    public static void printRemoveError(int number) {
        System.out.println(line);
        System.out.println("\tSorry, you only have " + number + " record(s).");
        System.out.println(line);
    }

    /**
     * The method will print the message of the removal of a user defined record.
     * @param record the record to be removed
     */
    public static void echoRemove(String record) {
        System.out.println(line);
        System.out.println("\tNoted. I've removed this record: ");
        System.out.println("\t" + record);
        System.out.println(line);
    }

    /**
     * This method will print the error message if user have enter an invalid number to be removed.
     */
    public static void printInvalidRemoveMessage() {
        System.out.println(line);
        System.out.println("\tPlease enter a valid number to be removed.");
        System.out.println(line);
    }
}
