package dolla.ui;

import dolla.model.Record;

import java.util.ArrayList;

//@@author yetong1895
public class SortUi extends Ui {
    public static String outputStringTest;

    /**
     * This method will print sorted list according to the type to be sorted.
     *
     * @param list the list to be printed
     * @param type the type of input of the list
     */
    public static void printSortedList(ArrayList<Record> list, String type) {
        System.out.println(line);
        outputStringTest = line + "\n";
        switch (type) {
        case "date":
            typewriter("\tsorting date.........\n");
            outputStringTest += "\tsorting date.........\n";
            break;
        case "description":
            typewriter("\tsorting description.........\n");
            outputStringTest += "\tsorting description.........\n";
            break;
        case "name":
            typewriter("\tsorting name.........\n");
            outputStringTest += "\tsorting name.........\n";
            break;
        case "amount":
            typewriter("\tsorting amount.........\n");
            outputStringTest += "\tsorting amount.........\n";
            break;
        default:
            break;
        }

        for (int i = 0; i < list.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + list.get(i).getRecordDetail());
            outputStringTest += "\t" + listNum + ". " + list.get(i).getRecordDetail() + "\n";
        }
        System.out.println(line);
        outputStringTest += line + "\n";
    }

    /**
     * This method will print the invalid message for invalid sort type.
     * @param mode the mode that the user is in.
     */
    public static void printInvalidSort(String mode) {
        System.out.println(line);
        System.out.println("\tYou have enter a invalid sort command!");
        switch (mode) {
        case "entry":
            System.out.println("\tYou can try [sort amount/date/description].");
            break;
        case "debt":
            System.out.println("\tYou can try [sort amount/date/description/name].");
            break;
        case "limit":
            System.out.println("\tYou can try [sort amount].");
            break;
        default:
            break;
        }
        System.out.println(line);
    }
}
