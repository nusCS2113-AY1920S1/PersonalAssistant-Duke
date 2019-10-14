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
    public String printList() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexCount = 1;
        stringBuilder.append("Here are the ").append(listIndex2SubList.size()).append(" subtopics available.\n");
        for (String topicsInMainList : listIndex2SubList) {
            stringBuilder.append("2.").append(indexCount).append(". ");
            stringBuilder.append(topicsInMainList).append("\n");
            indexCount++;
        }
        stringBuilder.append("Key in the index to learn more about the topic!\n");
        return stringBuilder.toString();
    }
}
