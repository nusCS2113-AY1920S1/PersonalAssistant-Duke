package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class ListIndex1  {

    public ArrayList<String> listIndex1SubList = new ArrayList<String>();

    /**
     * List at index 1.
     */
    public ListIndex1() {
        listIndex1SubList.add("Print");
        listIndex1SubList.add("Read");
        listIndex1SubList.add("Classes and Objects");
        listIndex1SubList.add("Quiz");
    }


    /**
     * Method to print the contents of the list.
     */
    public void printList() {
        int indexCount = 1;
        System.out.println("Here are the " + listIndex1SubList.size() + " subtopics available.");
        for (String topicsInMainList : listIndex1SubList) {
            System.out.print("1." + indexCount + " ");
            System.out.println(topicsInMainList);
            indexCount++;
        }
        System.out.println("Key in the index to learn more about the topic!");
    }
}
