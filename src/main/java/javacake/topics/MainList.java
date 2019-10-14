package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class MainList {

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
    public void loadTopics() {
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                openingMainList.add(new MainListTopic(listOfFile.getName()));
            } else if (listOfFile.isDirectory()) {
                openingMainList.add(new MainListTopic(listOfFile.getName()));
            }
        }
    }




}

