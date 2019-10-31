package dolla.ui;

import dolla.Time;
import dolla.task.RecordList;

//@@author tatayu
public class SearchUi extends Ui {

    /**
     * Print the result of searching by description.
     *
     * @param mode          the mode
     * @param recordList       the log list
     * @param searchContent the search content
     */
    public static void printSearchDesc(String mode, RecordList recordList, String searchContent) {

        System.out.println(line);
        System.out.println("\tHere are the matching results found in " + mode);
        int listNum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            String temp = recordList.get().get(i).getDescription();
            if (temp.contains(searchContent)) {
                listNum += 1;
                System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
            }
        }
        System.out.println(line);
    }

    /**
     * Print the result of searching by name.
     *
     * @param mode          the mode
     * @param recordList       the log list
     * @param searchContent the search content
     */
    public static void printSearchName(String mode, RecordList recordList, String searchContent) {

        System.out.println(line);
        System.out.println("\tHere are the matching results found in " + mode);
        int listNum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            String tempt = recordList.get().get(i).getName();
            if (tempt.contains(searchContent)) {
                listNum += 1;
                System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
            }
        }
        System.out.println(line);
    }

    /**
     * Print the result of searching by date.
     *
     * @param mode          the mode
     * @param recordList       the record list
     * @param searchContent the search content
     */
    public static void printSearchDate(String mode, RecordList recordList, String searchContent) {
        System.out.println(line);
        System.out.println("\tHere are the matching results found in " + mode);
        int listNum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            String temp = Time.dateToString(recordList.get().get(i).getDate());
            if (temp.contains(searchContent)) {
                listNum += 1;
                System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
            }
        }
        System.out.println(line);
    }

    /**
     * Print the result of searching by duration (for limit mode only).
     * @param mode the mode
     * @param recordList the record list
     * @param searchContent the search content
     */
    public static void printSearchDuration(String mode, RecordList recordList, String searchContent) {
        System.out.println(line);
        System.out.println("\tHere are the matching results found in " + mode);
        int listNum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            String temp = recordList.get().get(i).getDuration();
            if (temp.contains(searchContent)) {
                listNum += 1;
                System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
            }
        }
        System.out.println(line);
    }

    /**
     * Print error message when the search command is invalid.
     */
    public static void printInvalidSearchFormat() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'search [COMPONENT] [CONTENT]'");
        System.out.println(line);
    }

    /**
     * Prints error message when the user did not properly specify the component to search from.
     */
    public static void printInvalidSearchComponent() {
        System.out.print(line);
        System.out.println("\tPlease specify the component to search from: description, date, name, duration.");
        System.out.print(line);
    }
}
