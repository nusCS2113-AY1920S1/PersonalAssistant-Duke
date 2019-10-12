package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class ListIndex1 extends ListFormat {

    private String folderPath = "content/MainList/ListIndex1/JavaBasics";
    private File folder;
    private static File[] listOfFiles;
    public static ArrayList<SubListTopic> listIndex1SubList = new ArrayList<SubListTopic>();

    /**
     * List at index 1.
     */
    public ListIndex1() {
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
                listIndex1SubList.add(new SubListTopic(listOfFile.getName()));
            } else if (listOfFile.isDirectory()) {
                listIndex1SubList.add(new SubListTopic(listOfFile.getName()));
            }
        }
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {
        System.out.println("Here are the " + listIndex1SubList.size() + " subtopics available.");
        for (SubListTopic subList1Topics : listIndex1SubList) {
            System.out.println(subList1Topics.getName());
        }
        System.out.println("Key in the index to learn more about the topic!");
    }

    public static void main(String[] args) {
        ListIndex1 lI1 = new ListIndex1();
        lI1.loadTopics();
        lI1.printList();
    }
}
