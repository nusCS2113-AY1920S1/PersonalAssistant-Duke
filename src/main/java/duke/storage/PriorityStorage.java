package duke.storage;

import duke.task.PriorityList;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class PriorityStorage {
    //protected String filePath = "./";
    protected String filePath = "";
    String storageClassPath = Storage.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final int ZERO = 0;

    /**
     * Creates a storage with a specified filePath.
     *
     * @param filePath The location of the text file.
     */
    public PriorityStorage(String filePath) {
        String[] pathSplitter = storageClassPath.split("/");
        for (String directory: pathSplitter) {
            if (!directory.isEmpty() && !directory.equals("build")) {
                this.filePath += directory + "/";
            } else if (directory.equals("build")) {
                break;
            }
        }
        this.filePath += filePath;
    }


    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the priorityList.
     * @throws IOException  If there is an error reading the text file.
     */
    public ArrayList<Integer> read() throws IOException {
        ArrayList<Integer> items = new ArrayList<>();
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String priority;
        while ((priority = br.readLine()) != null) {
            items.add(Integer.parseInt(priority.trim()));
        }
        br.close();
        return items;
    }

    /**
     * Updates the text file from interpreting the priorities of the priorityList.
     *
     * @param priorityList The list of priorities associated with each task.
     * @throws IOException  If there is an error writing the text file.
     */
    public void write(PriorityList priorityList) throws IOException {
        String fileContent = "";
        for (int i = ZERO; i < priorityList.getSize(); i++) {
            fileContent += priorityList.getList().get(i) + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
    }

}
