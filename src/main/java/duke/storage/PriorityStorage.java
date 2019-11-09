package duke.storage;

import duke.enums.Numbers;
import duke.task.PriorityList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;

//@@author Dou-Maokang
/**
 * Representing a class to store the list of priorities.
 */
public class PriorityStorage {
    protected static String filePath = System.getProperty("user.dir") + "/";

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
        for (int i = Numbers.ZERO.value; i < priorityList.getSize(); i++) {
            fileContent += priorityList.getList().get(i) + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
    }

    //@@author maxxyx96
    /**
     * Extracts the sample data from jar file and moves it to data folder in the computer.
     *
     * @param samplePath path of the sample data set for priority.
     * @throws IOException When there is an error writing to the text file.
     */
    public static void writeSample(String samplePath) throws IOException {
        String fileContent = "";
        InputStream in = PriorityStorage.class.getResourceAsStream(samplePath);
        if (in == null) {
            in = PriorityStorage.class.getClassLoader().getResourceAsStream(samplePath);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String input = "";
        while ((input = bufferedReader.readLine()) != null) {
            fileContent += input + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
        bufferedReader.close();
        in.close();
    } //@@author
}
//@@author