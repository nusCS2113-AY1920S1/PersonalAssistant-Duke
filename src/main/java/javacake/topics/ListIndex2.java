package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class ListIndex2 extends ListFormat {

    private String folderPath = "content/MainList/ListIndex2/oop";
    private File folder;
    private static File[] listOfFiles;
    public static ArrayList<SubListTopic> listIndex2SubList = new ArrayList<SubListTopic>();

    /**
     * List at index 2.
     */
    public ListIndex2() {
        folder = new File(this.folderPath);
        listOfFiles = folder.listFiles();
    }

    /**
     * Creates SubListTopic objects based on the names of the directories.
     * Store SubListTopic objects into listIndex1Sublist ArrayList.
     */
    @Override
    public void loadTopics() {
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                listIndex2SubList.add(new SubListTopic(listOfFile.getName()));
            } else if (listOfFile.isDirectory()) {
                listIndex2SubList.add(new SubListTopic(listOfFile.getName()));
            }
        }
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {

        System.out.println("Here are the " + listIndex2SubList.size() + " subtopics available.");
        for (SubListTopic subList2Topics : listIndex2SubList) {
            System.out.println(subList2Topics.getName());
        }
        System.out.println("Key in the index to learn more about the topic!");
    }

    public static void main(String[] args) {
        ListIndex2 lI2 = new ListIndex2();
        lI2.loadTopics();
        lI2.printList();
    }
}
