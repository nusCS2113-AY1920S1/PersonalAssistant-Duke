package javacake.topics;

import java.io.File;
import java.util.ArrayList;

public class ListIndex1 extends ListFormat {

    private String folderPath;
    private File folder;
    private File[] listOfFiles;
    public ArrayList<Topic> listIndex1SubList = new ArrayList<Topic>();

    /**
     * List at index 1.
     */
    public ListIndex1() {
        listIndex1SubList.add("Print");
        listIndex1SubList.add("Read");
        listIndex1SubList.add("Classes and Objects");
        listIndex1SubList.add("Quiz");
    }


    @Override
    public String getFolderPath() {
        return null;
    }

    @Override
    public File findTopicsWithinFolder(String folderPath) {
        return null;
    }

    /**
     * Method to print the contents of the list.
     */
    public void printList() {
        int indexCount = 1;
        System.out.println("Here are the " + listIndex1SubList.size() + " subtopics available.");
        for (String topicsInMainList : listIndex1SubList) {
            System.out.print("1." + indexCount + " ");
            System.out.println(topicsInMainList);
            indexCount++;
        }
        System.out.println("Key in the index to learn more about the topic!");
    }
}
