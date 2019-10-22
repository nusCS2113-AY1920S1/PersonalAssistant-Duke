package javacake.storage;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Storage {
    private int stringBuffer = 7;
    private static ArrayList<Task> internalTaskData = new ArrayList<>();

    private String filepath;
    private TaskType dataType;
    public TaskList tasks;

    public enum TaskType {
        TODO, DEADLINE, TODO_DAILY, TODO_WEEKLY, TODO_MONTHLY
    }

    /**
     * Constructor for storage.
     */
    public Storage() throws DukeException {
        this.tasks = new TaskList();
        //Initialise new deadline file
        try {
            File tasksFile = new File("data/tasks/deadline.txt");
            File notesFile = new File("data/notes/");
            filepath = tasksFile.getPath();
            Duke.logger.log(Level.INFO,"Filepath: " + filepath);
            generateFolder(notesFile);

            if (!tasksFile.getParentFile().getParentFile().exists()) {
                tasksFile.getParentFile().getParentFile().mkdir();
                tasksFile.getParentFile().mkdir();
                tasksFile.createNewFile();
                System.out.println("A" + tasksFile.getParentFile().getParentFile().getPath());
            } else if (!tasksFile.getParentFile().exists()) {
                tasksFile.getParentFile().mkdir();
                tasksFile.createNewFile();
                System.out.println("B" + tasksFile.getParentFile().getPath());
            } else if (!tasksFile.exists()) {
                tasksFile.createNewFile();
                System.out.println("C" + tasksFile.getPath());
            } else {
                Duke.logger.log(Level.INFO, filepath + " is found!");
            }
        } catch (IOException e) {
            Duke.logger.log(Level.WARNING, "Unable to create deadline file");
            throw new DukeException("Failed to create storage.");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "|");
                int count = 1;
                StringBuilder finalOutput = new StringBuilder();
                String currStr;
                boolean isChecked = false;

                while (stringTokenizer.hasMoreTokens()) {
                    currStr = stringTokenizer.nextToken();
                    if (count == 1) {
                        if (currStr.equals("D")) {
                            finalOutput = new StringBuilder("deadline ");
                            this.dataType = TaskType.DEADLINE;
                        }
                    } else if (count == 2 && currStr.equals("✓")) {
                        isChecked = true;
                    } else if (count == 3) {
                        finalOutput.append(currStr);
                    } else if (count == 4) {
                        if (this.dataType == TaskType.DEADLINE) {
                            finalOutput.append(" /by ").append(currStr);
                        }
                    }
                    count++;
                }
                if (!isChecked) {
                    TaskList.runDeadline(internalTaskData, finalOutput.toString(), TaskList.TaskState.NOT_DONE);
                } else {
                    TaskList.runDeadline(internalTaskData, finalOutput.toString(), TaskList.TaskState.DONE);
                }
            }
            reader.close();
        } catch (IOException e) {
            Duke.logger.log(Level.WARNING, "Unable to find deadline file");
            throw new DukeException("Failed to create storage.");
        }

        //Initialise new notes directory

    }

    /**
     * Generates starting folder when program starts.
     * @param sampleFile File that is auto-generated when program starts.
     * @throws DukeException If file does not exist.
     */
    private static void generateFolder(File sampleFile) {
        if (!sampleFile.getParentFile().exists()) {
            sampleFile.getParentFile().mkdirs();
            sampleFile.mkdirs();
        }
    }

    /**
     * Initialises the 'data' based on previous data
     * from filepath.
     * @param filepath The storage path of the saved data
     * @throws DukeException Exception when file is not found
     */
    public Storage(String filepath) throws DukeException {
        this();
    }

    /**
     * Loads the currently initialised ArrayList of Tasks.
     * @return ArrayList of Tasks that has been initialised
     */
    public ArrayList<Task> load() {
        return internalTaskData;
    }

    /**
     * Writes current taskList onto the save file.
     * @param tasks ArrayList of Tasks needing to be written
     *              onto the save file
     * @throws DukeException when no file is found
     */
    public void write(ArrayList<Task> tasks) throws DukeException {
        try {
            PrintWriter out = new PrintWriter(filepath);
            for (Task task : internalTaskData) {
                out.println(doInternalWrite(task));
            }
            for (Task task : tasks) {
                out.println(doInternalWrite(task));
            }

            out.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("No file found");
        }
    }

    private String doInternalWrite(Task task) {
        String st1;
        st1 = task.toString().substring(1, 2);
        String st4 = null;
        //Appends extra task details for all task types excent 'Todo'
        if (st1.equals("D") || st1.equals("E") || st1.equals("d") || st1.equals("m") || st1.equals("w")) {
            st4 = task.getExtra();
        }
        String st2;
        st2 = task.getStatusIcon();
        String st3;
        st3 = task.toString().substring(3);
        if (st4 != null && st3.contains(st4)) {
            st3 = st3.replace(st4, "");
            //STRING_BUFFER removes the " (by: )" / " (at: )" from st3
            st3 = st3.substring(0, st3.length() - stringBuffer);
        }
        StringBuilder str = new StringBuilder();
        str.append(st1);
        str.append("|");
        str.append(st2);
        str.append("|");
        str.append(st3);
        if (st4 != null) {
            str.append("|");
            str.append(st4);
        }
        return str.toString();
    }

    /**
     * Method to get size of internal data.
     * @return size of internal data
     */
    public static int getInternalDataSize() {
        return internalTaskData.size();
    }
}
