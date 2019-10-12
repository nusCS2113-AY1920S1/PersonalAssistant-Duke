package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class MainList extends ListFormat {

    private String folderPath = "content/MainList";
    private File folder;
    private static File[] listOfFiles;
    public static ArrayList<MainListTopic> openingMainList = new ArrayList<MainListTopic>();


    public MainList() {
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
                openingMainList.add(new MainListTopic(listOfFile.getName()));
            } else if (listOfFile.isDirectory()) {
                openingMainList.add(new MainListTopic(listOfFile.getName()));
            }
        }
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {
        System.out.println("Here are the " + openingMainList.size() + " subtopics available.");
        for (MainListTopic mainListTopics : openingMainList) {
            System.out.println(mainListTopics.getName());
        }
        System.out.println("Key in the index to learn more about the topic!");
    }

    public static void main(String[] args) {
        MainList ml = new MainList();
        ml.loadTopics();
        ml.printList();
    }
}

