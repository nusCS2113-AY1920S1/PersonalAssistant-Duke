package dolla.ui;

import dolla.task.Log;

import java.util.ArrayList;

public class SortUi extends Ui{
    /**
     * Print sorted list.
     *
     * @param list the list to be printed
     * @param type the type of input of the list
     */
    public static void printSortedList(ArrayList<Log> list, String type) {
        System.out.println(line);
        if (type.equals("date")) {
            System.out.println("sorting date.........");
        } else if (type.equals("description")) {
            System.out.println("sorting description.........");
        } else if (type.equals("name")) {
            System.out.println("sorting name.........");
        } else if (type.equals("amount")) {
            System.out.println("sorting amount.........");
        }

        for (int i = 0; i < list.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + list.get(i).getLogText());
        }
        System.out.println(line);
    }
}
