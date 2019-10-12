package javacake.topics;

import java.util.ArrayList;

public class ListIndex3 {

    public ArrayList<String> listIndex3SubList = new ArrayList<String>();

    /**
     * List at Index 3.
     */
    public ListIndex3() {
        listIndex3SubList.add("Enumerations");
        listIndex3SubList.add("Varargs");
        listIndex3SubList.add("Exceptions");
        listIndex3SubList.add("Quiz");
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {
        int indexCount = 1;
        System.out.println("Here are the " + listIndex3SubList.size() + " subtopics available.");
        for (String topicsInMainList : listIndex3SubList) {
            System.out.print("3." + indexCount + " ");
            System.out.println(topicsInMainList);
            indexCount++;
        }
        System.out.println("Key in the index to learn more about the topic!");
    }
}
