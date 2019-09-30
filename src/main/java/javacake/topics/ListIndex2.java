package javacake.topics;

import java.util.ArrayList;

public class ListIndex2 extends ListFormat {

    public ArrayList<String> listIndex2SubList = new ArrayList<String>();

    public ListIndex2() {
        listIndex2SubList.add("Enumerations");
        listIndex2SubList.add("Varargs");
        listIndex2SubList.add("Exceptions");
    }
    public void printList() {
        int indexCount = 1;
        System.out.println("Here are the " + listIndex2SubList.size() + " subtopics available.");
        for (String topicsInMainList : listIndex2SubList) {
            System.out.print(indexCount + ".");
            System.out.println(topicsInMainList);
            indexCount++;
        }
        System.out.println("Key in the index to learn more about the topic!");
    }
}
