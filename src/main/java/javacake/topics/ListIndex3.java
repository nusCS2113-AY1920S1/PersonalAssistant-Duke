package javacake.topics;

import java.util.ArrayList;

public class ListIndex3 extends ListFormat {

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
    public String printList() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexCount = 1;
        stringBuilder.append("Here are the ").append(listIndex3SubList.size()).append(" subtopics available.\n");
        for (String topicsInMainList : listIndex3SubList) {
            stringBuilder.append("3.").append(indexCount).append(". ");
            stringBuilder.append(topicsInMainList).append("\n");
            indexCount++;
        }
        stringBuilder.append("Key in the index to learn more about the topic!\n");
        return stringBuilder.toString();
    }
}
