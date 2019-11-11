package javacake.storage;

import javacake.exceptions.CakeException;
import javacake.tasks.Task;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getPackageName());
    private int stringBuffer = 7;
    private ArrayList<Task> tempTaskData = new ArrayList<>();
    public TaskList currentTaskData;

    private static String defaultFilePath = "data";
    private String filepath;
    private static boolean isResetFresh = false;


    /**
     * Constructor for storage.
     */
    public Storage() throws CakeException {
        this(defaultFilePath);
    }

    /**
     * Initialises the 'data' based on previous data
     * from filepath.
     * @param altPath The storage path of the saved data
     * @throws CakeException Exception when file is not found
     */
    public Storage(String altPath) throws CakeException {
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.INFO);
        LOGGER.entering(getClass().getName(), "Storage");

        this.currentTaskData = new TaskList();
        defaultFilePath = altPath;
        //Initialise new deadline file
        filepath = defaultFilePath + "/tasks/deadline.txt";
        File tasksFile = new File(filepath);
        //Initialise new notes directory
        File notesFile = new File(defaultFilePath + "/notes/");
        generateFolder(notesFile);
        LOGGER.info("Filepath[s]: " + filepath);
        try {
            initialiseStorage(tasksFile, altPath);
        } catch (IOException e) {
            LOGGER.severe("Reader failed[s]");
            throw new CakeException("Failed to create storage.");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String lineToRead;
            while ((lineToRead = reader.readLine()) != null) {
                String currStr = "";
                boolean isChecked;
                String[] listOfStrings = lineToRead.split("\\|");
                if (listOfStrings.length != 4) {
                    throw new CakeException("DEADLINE LENGTH INVALID: " + listOfStrings.length);
                }

                currStr += "deadline ";
                if (!"D".equals(listOfStrings[0])) {
                    throw new CakeException("DEADLINE EXP 0: " + listOfStrings[0]);
                }

                if ("✓".equals(listOfStrings[1])) {
                    isChecked = true;
                } else if ("✗".equals(listOfStrings[1])) {
                    isChecked = false;
                } else {
                    throw new CakeException("DEADLINE EXP 1: " + listOfStrings[1]);
                }

                //append taskname
                currStr += listOfStrings[2];
                currStr += " /by ";
                currStr += listOfStrings[3];

                System.out.println("CurrStr: " + currStr);
                if (!isChecked) {
                    TaskList.runDeadline(tempTaskData, currStr, TaskState.NOT_DONE);
                    this.currentTaskData.add(tempTaskData.get(0));
                    tempTaskData.clear();
                } else {
                    TaskList.runDeadline(tempTaskData, currStr, TaskState.DONE);
                    this.currentTaskData.add(tempTaskData.get(0));
                    tempTaskData.clear();
                }
            }
            reader.close();
        } catch (IOException e) {
            LOGGER.severe("Unable to find deadline file");
            throw new CakeException("Failed to create storage.");
        }
        LOGGER.exiting(getClass().getName(), "Storage");
    }

    private void initialiseStorage(File tasksFile, String altPath) throws IOException {
        LOGGER.entering(getClass().getName(), "initialiseStorage");
        boolean isCleanSlate = true;
        if (!tasksFile.getParentFile().getParentFile().exists()) {
            tasksFile.getParentFile().getParentFile().mkdir();
            LOGGER.info("Creating grandpa file[s]");
        }
        if (!tasksFile.getParentFile().exists()) {
            tasksFile.getParentFile().mkdir();
            LOGGER.info("Creating papa file[s]");
        }
        if (!tasksFile.exists()) {
            tasksFile.createNewFile();
            LOGGER.info("Creating file[s]");
        } else {
            isCleanSlate = false;
            LOGGER.info(filepath + " is found![s]");
        }

        //populate with testing trash
        if (!isResetFresh && isCleanSlate && "data".equals(altPath)) {
            LOGGER.info("Testing Storage initiated");
            PrintWriter out = new PrintWriter(filepath);
            out.println("D|✗|testmessage to show the39characterlimit|01 01 2019 0001");
            out.println("D|✗|finish javacake|31-12-19 23:59");
            out.println("D|✗|start dieting on java|01/01/2019");
            out.close();
        }
        LOGGER.exiting(getClass().getName(), "initialiseStorage");
    }

    public static String returnNotesDefaultFilePath() {
        return defaultFilePath + "/notes/";
    }

    /**
     * Method to hard reset profile.
     */
    public void resetStorage() throws CakeException {
        isResetFresh = true;
        try {
            FileUtils.deleteDirectory(new File(defaultFilePath));
            tempTaskData.clear();
            currentTaskData.getData().clear();
        } catch (IOException e) {
            throw new CakeException("Unable to reset Storage");
        }
    }

    /**
     * Generates starting folder when program starts.
     * @param sampleFile File that is auto-generated when program starts.
     */
    public static void generateFolder(File sampleFile) {
        if (!sampleFile.getParentFile().exists()) {
            sampleFile.getParentFile().mkdirs();
            sampleFile.mkdirs();
        } else if (!sampleFile.exists()) {
            sampleFile.mkdirs();
        }
    }

    /**
     * Loads the currently initialised ArrayList of Tasks.
     * @return ArrayList of Tasks that has been initialised
     */
    public ArrayList<Task> load() {
        return tempTaskData;
    }

    /**
     * Writes current taskList onto the save file.
     * @param tasks ArrayList of Tasks needing to be written
     *              onto the save file
     * @throws CakeException when no file is found
     */
    public void write(ArrayList<Task> tasks) throws CakeException {
        try {
            PrintWriter out = new PrintWriter(filepath);
            for (Task task : tempTaskData) {
                out.println(doInternalWrite(task));
            }
            for (Task task : tasks) {
                out.println(doInternalWrite(task));
            }

            out.close();
        } catch (FileNotFoundException e) {
            throw new CakeException("No file found");
        }
    }

    private String doInternalWrite(Task task) {
        String st1;
        st1 = task.toString().substring(1, 2);
        String st4 = null;
        //Appends extra task details for all task types excent 'Todo'
        if ("D".equals(st1)) {
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

    public ArrayList<Task> getData() {
        return currentTaskData.getData();
    }
}
