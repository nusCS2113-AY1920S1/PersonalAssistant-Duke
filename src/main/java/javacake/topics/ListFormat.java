package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public abstract class ListFormat {

    private String folderPath;
    private File folder;
    private static File[] listOfFiles;
    private static ArrayList<Topic> listOfTopics;


    public abstract void loadTopics();

    /**
     * Prints all the strings in the ArrayList al.
     */
    public abstract String printList();

}
