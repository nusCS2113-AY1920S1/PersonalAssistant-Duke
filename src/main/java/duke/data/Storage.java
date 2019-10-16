package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Storage {

    private final File taskFile;
    private HashMap<String, Tsk> taskMap = new HashMap<String, Tsk>();

    /**
     * Constructs a new Storage object, with the task file at the specified path, and create HashMap for quick task
     * lookup.
     *
     * @param filePath Path at which to look for or create the data file.
     * @throws DukeFatalException If data file cannot be setup.
     */
    public Storage(String filePath) throws DukeFatalException {
        taskFile = new File(filePath);
        if (!taskFile.exists()) {
            try {
                if (!taskFile.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException excp) {
                throw new DukeFatalException("Unable to setup data file, try checking your permissions?");
            }
        }
        for (Tsk tsk : Tsk.values()) {
            taskMap.put(tsk.toString(), tsk);
        }
    }

    /**
     * Writes data to the task file.
     *
     * @param taskFileStr String to write to task file.
     * @throws DukeFatalException If unable to write to the task file.
     */
    public void writeTaskFile(String taskFileStr) throws DukeFatalException {
        // TODO: figure out some way of editing that doesn't involve rewriting everything each time
        // Maybe some kind of diff file?

        try {
            FileWriter taskFileWr = new FileWriter(taskFile);
            taskFileWr.write(taskFileStr);
            taskFileWr.close();
        } catch (IOException excp) {
            throw new DukeFatalException("Unable to write data! Some data may have been lost,");
        }
    }

    /**
     * Parses a task file into an ArrayList of tasks.
     *
     * @return An ArrayList of the tasks extracted from the task file.
     * @throws DukeResetException If any corruption or improperly formatted entries are encountered.
     * @throws DukeFatalException If file cannot be found.
     */
    public ArrayList<Task> parseTaskFile() throws DukeResetException, DukeFatalException {
        ArrayList<Task> taskArrList = new ArrayList<Task>();
        if (taskFile.length() == 0) { // file is empty
            return taskArrList;
        }

        //message for when data corruption is detected in the file
        String corrupt = "Data file has been corrupted!";

        try {
            Scanner taskScanner = new Scanner(taskFile);
            while (taskScanner.hasNextLine()) {
                String taskLine = taskScanner.nextLine();
                String[] taskArr = taskLine.split("\t"); //extract data fields
                for (int i = 0; i < taskArr.length; ++i) {
                    taskArr[i] = taskArr[i].strip();
                }

                Task currTask;

                Tsk tsk = taskMap.get(taskArr[0]);
                if (tsk == null) {
                    throw new DukeResetException(corrupt);
                }
                currTask = tsk.getTask(taskArr);

                //check if task is done
                if (taskArr[1].equals("1")) {
                    currTask.markDone();
                } else if (!taskArr[1].equals("0")) {
                    throw new DukeResetException(corrupt);
                }
                taskArrList.add(currTask);
            }
            taskScanner.close();
        } catch (DukeException | DateTimeParseException | IndexOutOfBoundsException excp) {
            throw new DukeResetException(corrupt);
        } catch (FileNotFoundException excp) {
            throw new DukeFatalException("Unable to find data file, try opening Duke again?");
        }

        return taskArrList;
    }
}
