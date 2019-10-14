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
    public String printList() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexCount = 1;
        stringBuilder.append("Here are the ").append(listIndex1SubList.size()).append(" subtopics available.\n");
        for (String topicsInMainList : listIndex1SubList) {
            stringBuilder.append("1.").append(indexCount).append(". ");
            stringBuilder.append(topicsInMainList).append("\n");
            indexCount++;
        }
        stringBuilder.append("Key in the index to learn more about the topic!\n");
        return stringBuilder.toString();
    }
}
