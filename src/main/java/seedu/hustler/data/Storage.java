package seedu.hustler.data;

import java.text.ParseException;
import seedu.hustler.task.Task;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Event;
import seedu.hustler.task.Deadline;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

import static seedu.hustler.parser.DateTimeParser.getDateTime;

/**
 * A class that stores current task list and loads it on request from disc.
 */
public class Storage {
    /**
     * Path to the file where tasks are stored and retreived
     * from.
     */
    private static String filePath;
    private static String filePathBackup;
    private Schedule schedule;

    /**
     * Empty constructor.
     */
    public Storage() {

    }

    /**
     * Initializes filePath.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.filePathBackup = filePath.split("duke.txt")[0] + "backup/dukeBackup.txt";
        this.schedule = new Schedule();
    }

    /**
     * Loads list of tasks from disc from a csv style file.
     *
     * @return an array list loaded from the disc.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> list = new ArrayList<Task>();
        try {
            Scanner dukeTxt = new Scanner(new File(this.filePath));
            while (dukeTxt.hasNextLine()) {
                // splits line input based on |
                String[] taskString = dukeTxt.nextLine().split("\\|");

                // instantiate classes
                if (taskString[0].equals("T")) {
                    list.add(new ToDo(taskString[3], taskString[2]));
                } else if (taskString[0].equals("D")) {
                    LocalDateTime by = getDateTime(taskString[4]);
                    list.add(new Deadline(taskString[3], by, taskString[2]));
                    try {
                        String dateOnly = taskString[4].split(" ")[0];
                        Date date = schedule.convertStringToDate(dateOnly);
                        Task lastTask = list.get(list.size() - 1);
                        schedule.addToSchedule(lastTask, date);
                    } catch (ParseException ignored) {
                        return null;
                    }
                } else {
                    LocalDateTime at = getDateTime(taskString[4]);
                    list.add(new Event(taskString[3], at, taskString[2]));
                    try {
                        String dateOnly = taskString[4].split(" ")[0];
                        Date date = schedule.convertStringToDate(dateOnly);
                        Task lastTask = list.get(list.size() - 1);
                        schedule.addToSchedule(lastTask, date);
                    } catch (ParseException ignored) {
                        return null;
                    }
                }

                if (taskString[1].equals("1")) {
                    list.get(list.size() - 1).markAsDone();
                }
            }
            dukeTxt.close();
        } catch (FileNotFoundException e) {
            System.out.println("\t_____________________________________");
            System.out.println("\tNo list saved in database. Please "
                + "create a list now.");
            System.out.println("\t_____________________________________\n\n");
        }
        return list;
    }

    /**
     * Reloads list of tasks from a backup of the original duke.txt file.
     *
     * @return an array list loaded from the backup disc.
     */
    public ArrayList<Task> reloadBackup() {
        ArrayList<Task> list = new ArrayList<Task>();
        try {
            Scanner dukeBackupTxt = new Scanner(new File(this.filePathBackup));
            while (dukeBackupTxt.hasNextLine()) {
                // splits line input based on |
                String[] taskString = dukeBackupTxt.nextLine().split("\\|");

                // instantiate classes
                if (taskString[0].equals("T")) {
                    list.add(new ToDo(taskString[3], taskString[2]));
                } else if (taskString[0].equals("D")) {
                    LocalDateTime by = getDateTime(taskString[4]);
                    list.add(new Deadline(taskString[3], by, taskString[2]));
                    try {
                        String dateOnly = taskString[4].split(" ")[0];
                        Date date = schedule.convertStringToDate(dateOnly);
                        Task lastTask = list.get(list.size() - 1);
                        schedule.addToSchedule(lastTask, date);
                    } catch (ParseException ignored) {
                        return null;
                    }
                } else {
                    LocalDateTime at = getDateTime(taskString[4]);
                    list.add(new Event(taskString[3], at, taskString[2]));
                    try {
                        String dateOnly = taskString[4].split(" ")[0];
                        Date date = schedule.convertStringToDate(dateOnly);
                        Task lastTask = list.get(list.size() - 1);
                        schedule.addToSchedule(lastTask, date);
                    } catch (ParseException ignored) {
                        return null;
                    }
                }

                if (taskString[1].equals("1")) {
                    list.get(list.size() - 1).markAsDone();
                }
            }
            dukeBackupTxt.close();
        } catch (FileNotFoundException e) {
        }
        return list;
    }

    /**
     * Saves the input task list to disc.
     *
     * @param inputList the list of tasks to save to disc.
     * @throws IOException if file could not be saved
     */
    public void save(ArrayList<Task> inputList) throws IOException {
        // if list has nothing just quit
        if (inputList.isEmpty()) {
            (new File(this.filePath)).delete();
            return;
        }
        //if data folder doesnt exist create it
        File directory = new File(this.filePath.split("/")[0]);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // save inputs
        String savedLine = inputList.get(0).toSaveFormat();
        for (int i = 1; i < inputList.size(); i++) {
            savedLine = savedLine + "\n" + inputList.get(i).toSaveFormat();
        }
        BufferedWriter writer = new BufferedWriter(
            new FileWriter(new File(this.filePath))
            );
        writer.write(savedLine);
        writer.close();
    }

    /**
     * Creates a backup copy of duke.txt each time the user uses this app; used for UndoCommand.
     *
     * @param inputList the list of tasks to save to disc.
     * @throws IOException if file could not be saved
     */
    public void createBackup(ArrayList<Task> inputList) throws IOException {
        // if list has nothing just quit
        if (inputList.isEmpty()) {
            (new File(this.filePathBackup)).delete();
            return;
        }
        //if data folder doesnt exist create it
        File directory = new File(this.filePathBackup.split("/dukeBackup.txt")[0]);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // save inputs
        String savedLine = inputList.get(0).toSaveFormat();
        for (int i = 1; i < inputList.size(); i++) {
            savedLine = savedLine + "\n" + inputList.get(i).toSaveFormat();
        }
        BufferedWriter writer = new BufferedWriter(
            new FileWriter(new File(this.filePathBackup))
            );
        writer.write(savedLine);
        writer.close();
    }
}
