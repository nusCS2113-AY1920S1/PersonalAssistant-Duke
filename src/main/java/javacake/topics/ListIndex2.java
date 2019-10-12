package javacake.topics;

import java.util.ArrayList;

public class ListIndex2 {

    public ArrayList<String> listIndex2SubList = new ArrayList<String>();

    /**
     * List at Index 2.
     */
    public ListIndex2() {
        listIndex2SubList.add("Inheritance");
        listIndex2SubList.add("Encapsulation");
        listIndex2SubList.add("Abstration");
        listIndex2SubList.add("Polymorphism");
        listIndex2SubList.add("Quiz");
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {
        int indexCount = 1;
        System.out.println("Here are the " + listIndex2SubList.size() + " subtopics available.");
        for (String topicsInMainList : listIndex2SubList) {
            System.out.print("2." + indexCount + " ");
            System.out.println(topicsInMainList);
            indexCount++;
        }
        System.out.println("Key in the index to learn more about the topic!");
    }
}
