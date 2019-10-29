package duke.storage;

import duke.dukeexception.DukeException;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.Repeat;
import duke.task.FixedDuration;
import duke.ui.Ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune
/**
 * Represents a storage to store the task list into a text file.
 */
public class Storage {
    protected String filePath = "";   //27-28, 40-47
    String storageClassPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a storage with a specified filePath.
     *
     * @param filePath The location of the text file for tasks.
     */
    public Storage(String filePath) {
        int numberofSlash;
        storageClassPath = storageClassPath.replaceAll("%20", " ");
        String[] pathSplitter = storageClassPath.split("/");
        numberofSlash = pathSplitter.length - ONE;
        for (String directory: pathSplitter) {
            if (numberofSlash == ZERO) {
                break;
            } else if (!directory.isEmpty() && !directory.equals("build") && !directory.equals("out")) {
                this.filePath += directory + "/";
            } else if (directory.equals("build") || directory.equals("out")) {
                break;
            }
            numberofSlash--;
        }
        this.filePath += filePath;
    }

    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the task list.
     * @throws IOException  If there is an error reading the text file.
     */
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
                for (int i = ZERO; i < commandList.length; i++) {
                    if (i == TWO) {
                        taskDesc = commandList[i];
                    } else if (i == THREE) {
                        notesDesc = commandList[i];
                    } else if (i == FOUR) {
                        if (commandList[ZERO].equals("F")) {
                            durDesc = commandList[i];
                        } else {
                            dateDesc = commandList[i];
                        }
                    }
                }
                boolean checked = false;
                if (commandList.length > ONE) {
                    if (!(commandList[ONE].equals("1") || commandList[ONE].equals("0"))) {
                        throw new DukeException("Error reading 1 or 0, skipping to next line");
                    }
                    checked = commandList[ONE].equals("1");
                }
                Task t;
                if (commandList[ZERO].equals("T")) {
                    if (taskDesc.trim().isEmpty()) {
                        throw new DukeException("Error reading description, skipping to next line");
                    } else {
                        t = new Todo(taskDesc);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (commandList[ZERO].equals("D")) {
                    if (taskDesc.trim().isEmpty() || dateDesc.trim().isEmpty()) {
                        throw new DukeException("Error reading description or date/time, skipping to next line");
                    } else {
                        t = new Deadline(taskDesc, dateDesc);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (commandList[ZERO].equals("R")) {
                    if (taskDesc.isEmpty() || dateDesc.isEmpty()) {
                        throw new DukeException("Error reading description or date/time, skipping to next line");
                    } else {
                        t = new Repeat(taskDesc, dateDesc);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (commandList[ZERO].equals("F")) {
                    if (taskDesc.isEmpty() || durDesc.isEmpty()) {
                        throw new DukeException("Error reading fixed duration description,"
                                + " skipping to next line");
                    } else {
                        int duration = Integer.parseInt(durDesc.split(" ")[ZERO]);
                        t = new FixedDuration(taskDesc, duration, durDesc.split(" ")[ONE]);
                        t.setStatusIcon(checked);
                        t.setNotes(notesDesc);
                        items.add(t);
                    }
                } else if (!commandList[ZERO].isEmpty()) {
                    throw new DukeException("Error reading whether if its T, D, R, or F skipping to next line");
                }
            } catch (Exception e) {
                ui.showErrorMsg("     Error when reading current line, please fix the text file:");
                logr.log(Level.SEVERE,"Error when reading current line, please fix the text file", e);
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
    public void write(TaskList items) throws IOException {
        String fileContent = "";
        for (int i = ZERO; i < items.size(); i++) {
            fileContent += items.get(i).toFile() + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
    }
}
