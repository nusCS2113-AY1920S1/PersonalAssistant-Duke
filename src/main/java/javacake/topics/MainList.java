package javacake.topics;

import java.util.ArrayList;

public class MainList extends ListFormat {

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
    public String printList() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexCount = 1;
        stringBuilder.append("Here are the ").append(mainContentList.size()).append(" topics available.\n");
        for (String topicsInMainList : mainContentList) {
            stringBuilder.append(indexCount).append(". ");
            stringBuilder.append(topicsInMainList).append("\n");
            indexCount++;
        }
        stringBuilder.append("Key in the index to learn more about the topic!\n");
        return stringBuilder.toString();
    }
}
