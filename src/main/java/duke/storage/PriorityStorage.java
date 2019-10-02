package duke.storage;

import duke.task.Task;
import duke.ui.Ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PriorityStorage {

    protected String filePath = "./";

    /**
     * Creates a storage with a specified filePath.
     *
     * @param filePath The location of the text file.
     */
    public PriorityStorage(String filePath) {
        this.filePath += filePath;
    }

    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the task list.
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

}
