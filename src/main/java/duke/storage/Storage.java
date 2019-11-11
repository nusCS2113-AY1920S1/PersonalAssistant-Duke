package duke.storage;

import duke.dukeexception.DukeException;
import duke.enums.Numbers;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.FixedDuration;
import duke.ui.Ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune
/**
 * Represents a storage to store the task list into a text file.
 */
public class Storage {
    private String filePath = System.getProperty("user.dir") + "/";
    private static final String CREDITS_PATH = "credits/credits.txt";
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a storage with a specified filePath.
     *
     * @param filePath The location of the text file for tasks.
     */
    public Storage(String filePath) {
        this.filePath += filePath;
    }

    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the task list.
     * @throws IOException  If there is an error reading the text file.
     */
    //Solution below adapted from http://www.javased.com/index.php?source_dir=AndroidCommon/src/com/asksven/android/common/kernelutils/Wakelocks.java
    public ArrayList<Task> read() throws IOException {

        ArrayList<Task> items = new ArrayList<>();
        Ui ui = new Ui();
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String taskDesc;
        String dateDesc;
        String durDesc;
        String notesDesc;
        while ((st = br.readLine()) != null) {
            String[] commandList = st.split("\\|");
            try {
                taskDesc = "";
                dateDesc = "";
                durDesc = "";
                notesDesc = "";
                for (int i = Numbers.ZERO.value; i < commandList.length; i++) {
                    if (i == Numbers.TWO.value) {
                        taskDesc = commandList[i];
                    } else if (i == Numbers.THREE.value) {
                        notesDesc = commandList[i];
                    } else if (i == Numbers.FOUR.value) {
                        if (commandList[Numbers.ZERO.value].equals("F")) {
                            durDesc = commandList[i];
                        } else {
                            dateDesc = commandList[i];
                        }
                    }
                }
                boolean checked = false;
                if (commandList.length > Numbers.ONE.value) {
                    if (!(commandList[Numbers.ONE.value].equals("1") || commandList[Numbers.ONE.value].equals("0"))) {
                        throw new DukeException("Error reading 1 or 0, skipping to next line");
                    }
                    checked = commandList[Numbers.ONE.value].equals("1");
                }
                Task t;
                if (commandList[Numbers.ZERO.value].equals("T")) {
                    if (taskDesc.trim().isEmpty()) {
                        throw new DukeException("Error reading description, skipping to next line");
                    } else {
                        t = new Todo(taskDesc);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (commandList[Numbers.ZERO.value].equals("D")) {
                    if (taskDesc.trim().isEmpty() || dateDesc.trim().isEmpty()) {
                        throw new DukeException("Error reading description or date/time, skipping to next line");
                    } else {
                        t = new Deadline(taskDesc, dateDesc);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (commandList[Numbers.ZERO.value].equals("F")) {
                    if (taskDesc.isEmpty() || durDesc.isEmpty()) {
                        throw new DukeException("Error reading fixed duration description,"
                                + " skipping to next line");
                    } else {
                        int duration = Integer.parseInt(durDesc.split(" ")[Numbers.ZERO.value]);
                        t = new FixedDuration(taskDesc, duration, durDesc.split(" ")[Numbers.ONE.value]);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (!commandList[Numbers.ZERO.value].isEmpty()) {
                    throw new DukeException("Error reading whether if its T, D, or F skipping to next line");
                }
            } catch (Exception e) {
                ui.showErrorMsg("     Error when reading current line, please fix the text file:");
                logger.log(Level.SEVERE,"Error when reading current line, please fix the text file", e);
                e.printStackTrace();
                ui.showErrorMsg("     Duke will continue reading the rest of file");
            }
        }
        br.close();
        return items;
    }


    /**
     * Updates the text file from interpreting the tasks of the task list.
     *
     * @param items The task list that contains a list of tasks.
     * @throws IOException  If there is an error writing the text file.
     */
    //Solution below adapted from https://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it/1377322#1377322
    public void write(TaskList items) throws IOException {
        String fileContent = "";
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            fileContent += items.get(i).toFile() + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
    }

    //@@author maxxyx96
    //Solution adapted from https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
    /**
     * Extracts the sample data from jar file and moves it to data folder in the computer.
     *
     * @param samplePath path of the sample data set for tasks.
     * @throws IOException When there is an error writing to the text file.
     */
    public void writeSample(String samplePath) throws IOException {
        String fileContent = "";
        InputStream in = Storage.class.getResourceAsStream(samplePath);
        if (in == null) {
            in = Storage.class.getClassLoader().getResourceAsStream(samplePath);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String input;
        while ((input = bufferedReader.readLine()) != null) {
            fileContent += input + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
        bufferedReader.close();
        in.close();
    } //@@author

    //@@author talesrune
    /**
     * Extracts the credits data from jar file.
     *
     * @return String of credits.
     * @throws IOException When there is an error writing to the text file.
     */
    public String readCredits() throws IOException {
        String creditsDesc = "";
        InputStream in = Storage.class.getResourceAsStream(CREDITS_PATH);
        if (in == null) {
            in = Storage.class.getClassLoader().getResourceAsStream(CREDITS_PATH);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String input;
        while ((input = bufferedReader.readLine()) != null) {
            creditsDesc += input + "\n";
        }
        bufferedReader.close();
        in.close();
        return creditsDesc;
    } //@@author
}
