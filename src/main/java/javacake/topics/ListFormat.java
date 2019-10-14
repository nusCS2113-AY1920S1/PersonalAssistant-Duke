package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public abstract class ListFormat {

    private String folderPath;
    private File folder;
    private File[] listOfFiles;

    public ListFormat(String inputFolderPath) {
        this.folderPath = inputFolderPath;
    }
    public abstract String getFolderPath();
    public abstract File findTopicsWithinFolder(String folderPath);


    /**
     * Prints all the strings in the ArrayList al.
     */
    public abstract String printList();

}
