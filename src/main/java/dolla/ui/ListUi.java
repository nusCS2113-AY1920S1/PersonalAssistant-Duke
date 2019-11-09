package dolla.ui;

import dolla.model.RecordList;

//@@author omupenguin
public class ListUi extends Ui {

    /**
     * Prints out a list depending on the mode where 'list' is called.
     *
     * @param mode    The mode that is used when 'list' is input.
     * @param recordList The RecordList containing the data of the list to be printed.
     */
    public static void printList(String mode, RecordList recordList) {

        System.out.println(line);
        System.out.println("\tHere is the list of " + mode + "(s) you have added:");
        for (int i = 0; i < recordList.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
        }
        System.out.println(line);
    }

    /**
     * Prints error message when user wants to list down items in the specific list but it's empty.
     *
     * @param mode The mode that is used when the 'list' is input
     */
    public static void printEmptyListError(String mode) {
        System.out.println(line);
        System.out.println("\tYou haven't added any " + mode + " yet!");
        System.out.println(line);
    }

    /**
     * Prints error message when LogNum is not associated to a task.
     *
     * @param index The Record number that does not exist in the specific list.
     * @param mode  The mode where the list is to be accessed.
     */
    public static void printNoRecordAssocError(int index, String mode) {
        System.out.println(line);
        System.out.println("\t" + mode + " number " + (index + 1) + " doesn't seem to exist in my records!");
        System.out.println("\tTry looking through the list of " + mode + "again.");
        System.out.println(line);
    }
}
