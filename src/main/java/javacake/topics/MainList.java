package javacake.topics;

import java.util.ArrayList;

public class MainList {

    public ArrayList<String> mainContentList = new ArrayList<String>();

    /**
     * Adds the available content in the main list.
     */
    public MainList() {
        mainContentList.add("Java Basics");
        mainContentList.add("OOP concepts");
        mainContentList.add("Useful Extensions");
        mainContentList.add("Overall Quiz");
    }

    /**
     * Prints the available content in the main list.
     */
    public void printList() {
        int indexCount = 1;
        System.out.println("Here are the " + mainContentList.size() + " topics available.");
        for (String topicsInMainList : mainContentList) {
            System.out.print(indexCount + ".");
            System.out.println(topicsInMainList);
            indexCount++;
        }
        System.out.println("Key in the index to learn more about the topic!");
    }
}
