package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class ListIndex3 extends ListFormat {

    private String folderPath = "content/MainList/ListIndex3/Extensions";
    private File folder;
    private static File[] listOfFiles;
    public static ArrayList<SubListTopic> listIndex3SubList = new ArrayList<SubListTopic>();

    /**
     * List at index 2.
     */
    public ListIndex3() {
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
                listIndex3SubList.add(new SubListTopic(listOfFile.getName()));
            } else if (listOfFile.isDirectory()) {
                listIndex3SubList.add(new SubListTopic(listOfFile.getName()));
            }
        }
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {

        System.out.println("Here are the " + listIndex3SubList.size() + " subtopics available.");
        for (SubListTopic subList3Topics : listIndex3SubList) {
            System.out.println(subList3Topics.getName());
        }
        System.out.println("Key in the index to learn more about the topic!");
    }

    public static void main(String[] args) {
        ListIndex3 lI3 = new ListIndex3();
        lI3.loadTopics();
        lI3.printList();
    }
}