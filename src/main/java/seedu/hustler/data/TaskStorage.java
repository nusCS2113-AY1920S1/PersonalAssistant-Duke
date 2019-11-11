package seedu.hustler.data;

import seedu.hustler.Hustler;
import seedu.hustler.task.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

/**
 * A class that stores current task list and loads it on request from disc.
 */
public class TaskStorage {
    /**
     * Path to the file where tasks are stored and
     * retrieved from.
     */
    private static String filePath;
    private static String filePathBackup;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Empty constructor.
     */
    public TaskStorage() {

    }

    /**
     * Initializes filePath.
     */
    public TaskStorage(String filePath) {
        this.filePath = filePath;
        this.filePathBackup = filePath.split("hustler.txt")[0] + "backup/hustlerBackup.txt";
    }

    /**
     * Loads list of tasks from disc from a csv style file.
     * @return an array list loaded from the disc.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> list = new ArrayList<>();
        try {
            Scanner hustlerTxt = new Scanner(new File(this.filePath));
            while (hustlerTxt.hasNextLine()) {
                // splits line input based on |
                String[] taskString = hustlerTxt.nextLine().split("\\|");

                if (taskString[0].equals("T")) {
                    LocalDateTime now = LocalDateTime.parse(taskString[5], formatter);
                    list.add(new ToDo(taskString[4], taskString[2], taskString[3], now));
                } else if (taskString[0].equals("D")) {
                    LocalDateTime by = getDateTime(taskString[5]);
                    LocalDateTime now = LocalDateTime.parse(taskString[6], formatter);

                    if (taskString.length == 10) {
                        int period = Integer.parseInt(taskString[8]);
                        boolean hasRecurred = Boolean.parseBoolean(taskString[9]);
                        list.add(new RecurringDeadline(taskString[4], by, taskString[2], taskString[3],
                                now, taskString[7], period, hasRecurred));
                    } else {
                        list.add(new Deadline(taskString[4], by, taskString[2], taskString[3], now));
                    }
                } else {
                    LocalDateTime at = getDateTime(taskString[5]);
                    LocalDateTime now = LocalDateTime.parse(taskString[6], formatter);

                    if (taskString.length == 10) {
                        int period = Integer.parseInt(taskString[8]);
                        boolean hasRecurred = Boolean.parseBoolean(taskString[9]);
                        list.add(new RecurringEvent(taskString[4], at, taskString[2], taskString[3],
                                now, taskString[7], period, hasRecurred));
                    } else {
                        list.add(new Event(taskString[4], at, taskString[2], taskString[3], now));
                    }
                }

                if (taskString[1].equals("1")) {
                    list.get(list.size() - 1).markAsDone();
                }
            }
            hustlerTxt.close();
        } catch (FileNotFoundException e) {
            list = getTestList();
            return list;
        }

        try {
            this.loadTimeSpent(list);
        } catch (IOException e) {
            System.out.println();
        }

        return list;
    }

    /**
     * Reloads list of tasks from a backup of the original hustler.txt file.
     * @return an array list loaded from the backup disc.
     */
    public ArrayList<Task> reloadBackup() {
        ArrayList<Task> list = new ArrayList<>();
        try {
            Scanner hustlerBackupTxt = new Scanner(new File(this.filePathBackup));
            while (hustlerBackupTxt.hasNextLine()) {
                // splits line input based on |
                String[] taskString = hustlerBackupTxt.nextLine().split("\\|");

                // instantiate classes
                if (taskString[0].equals("T")) {
                    LocalDateTime now = LocalDateTime.parse(taskString[5], formatter);
                    list.add(new ToDo(taskString[4], taskString[2], taskString[3], now));
                } else if (taskString[0].equals("D")) {
                    LocalDateTime by = getDateTime(taskString[5]);
                    LocalDateTime now = LocalDateTime.parse(taskString[6], formatter);

                    if (taskString.length == 10) {
                        int period = Integer.parseInt(taskString[8]);
                        boolean hasRecurred = Boolean.parseBoolean(taskString[9]);
                        list.add(new RecurringDeadline(taskString[4], by, taskString[2], taskString[3],
                                now, taskString[7], period, hasRecurred));
                    } else {
                        list.add(new Deadline(taskString[4], by, taskString[2], taskString[3], now));
                    }
                } else {
                    LocalDateTime at = getDateTime(taskString[4]);
                    LocalDateTime now = LocalDateTime.parse(taskString[6], formatter);

                    if (taskString.length == 10) {
                        int period = Integer.parseInt(taskString[8]);
                        boolean hasRecurred = Boolean.parseBoolean(taskString[9]);
                        list.add(new RecurringEvent(taskString[4], at, taskString[2], taskString[3],
                                now, taskString[7], period, hasRecurred));
                    } else {
                        list.add(new Event(taskString[4], at, taskString[2], taskString[3], now));
                    }
                }

                if (taskString[1].equals("1")) {
                    list.get(list.size() - 1).markAsDone();
                }
            }
            hustlerBackupTxt.close();
        } catch (FileNotFoundException e) {
            System.out.println("\t_____________________________________");
            System.out.println("\tNo list saved in database. Please "
                    + "create a list now.");
            System.out.println("\t_____________________________________\n\n");
        }
        return list;
    }

    /**
     * Saves the input task list to disc.
     * @param inputList the list of tasks to saveAchievements to disc.
     * @throws IOException if file could not be saved
     */
    public void save(ArrayList<Task> inputList) throws IOException {
        // if list has nothing just quit
        if (inputList.isEmpty()) {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(filePath))
            );
            writer.write("");
            writer.close();
            saveTimeSpent(inputList);
            return;
        }
        //if data folder doesnt exist create it
        File directory = new File(this.filePath.split("/")[0]);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // saveAchievements inputs
        String savedLine = inputList.get(0).toSaveFormat();
        for (int i = 1; i < inputList.size(); i++) {
            savedLine = savedLine + "\n" + inputList.get(i).toSaveFormat();
        }
        BufferedWriter writer = new BufferedWriter(
            new FileWriter(new File(this.filePath))
            );
        writer.write(savedLine);
        writer.close();

        saveTimeSpent(inputList);
    }
    
    /**
     * Saves the time spent on each task in a separate file that contains
     * address of the task and the time spent in seconds.
     *
     * @param inputList list of tasks whose time spent is saved
     * @throws IOException in case of IO error.
     */
    public void saveTimeSpent(ArrayList<Task> inputList) throws IOException {
        // if list has nothing just quit
        String saveAt = "data/timeSpent.txt";
        if (inputList.isEmpty()) {
            (new File(saveAt)).delete();
            return;
        }
        //if data folder doesnt exist create it
        File directory = new File(saveAt.split("/")[0]);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // saveAchievements inputs
        String savedLine = 0 + "|" + Hustler.scheduler.getTimeSpent(inputList.get(0));
        for (int i = 1; i < inputList.size(); i++) {
            savedLine = savedLine + "\n" + i + "|" + Hustler.scheduler.getTimeSpent(inputList.get(i));
        }
        BufferedWriter writer = new BufferedWriter(
            new FileWriter(new File(saveAt))
            );
        writer.write(savedLine);
        writer.close();
    }
    
    /**
     * Configures Scheduler on launch with time spent on incomplete tasks.
     * 
     * @param list list of tasks whose time spent is loaded
     * @throws IOException IO loading error
     */
    public void loadTimeSpent(ArrayList<Task> list) throws IOException {
        try {
            Scanner timeSpentSaved = new Scanner(new File("data/timeSpent.txt"));

            while (timeSpentSaved.hasNextLine()) {
                String[] taskString = timeSpentSaved.nextLine().split("\\|");
                int index = Integer.parseInt(taskString[0]);
                int timeSpent = Integer.parseInt(taskString[1]);
                Hustler.scheduler.add(list.get(index), timeSpent);
            }
            timeSpentSaved.close();
        } catch (FileNotFoundException e) {
            System.out.println();
        }


    }

    /**
     * Creates a backup copy of hustler.txt each time the user uses this app; used for UndoCommand.
     * @param inputList the list of tasks to saveAchievements to disc.
     * @throws IOException if file could not be saved
     */
    public void createBackup(ArrayList<Task> inputList) throws IOException {
        // if list has nothing just quit
        if (inputList.isEmpty()) {
            (new File(this.filePathBackup)).delete();
            return;
        }
        //if data folder doesn't exist create it
        File directory = new File(this.filePathBackup.split("/hustlerBackup.txt")[0]);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // saveAchievements inputs
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

    /**
     * Creates a test task list for users to experiment.
     * @return The new test task with 20 preloaded tasks.
     */
    private ArrayList<Task> getTestList() {
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Ask parents for lunch", "", "call", now));
        taskList.add(new ToDo("Wash clothes", "H", "", now));
        taskList.add(new ToDo("Put laundry in dryer", "L", "", now));
        taskList.add(new ToDo("Prepare steak dinner", "H", "", now));
        taskList.add(new ToDo("Make bed", "", "", now));
        taskList.add(new ToDo("Go to the gym", "", "Bishan", now));
        taskList.add(new Deadline("Finish arts project", now.plusDays(10)
            .with(LocalTime.of(23, 59)),"H", "important", now));
        taskList.add(new Deadline("Complete math assignment 5", now.plusDays(4)
            .with(LocalTime.of(23, 59)),"L", "", now));
        taskList.add(new Deadline("Submit documents to math office", now.plusDays(9)
            .with(LocalTime.of(10, 30)),"L", "", now));
        taskList.add(new Deadline("Collect JC graduation cert", now.plusDays(12)
            .with(LocalTime.of(12, 0)),"H", "", now));
        taskList.add(new Deadline("Submit sports proposal", now.plusDays(4)
            .with(LocalTime.of(23, 59)),"L", "", now));
        taskList.add(new Event("Attend Linkin Park concert", now.plusMonths(5)
            .with(LocalTime.of(17, 0)),"M", "buyTix", now));
        taskList.add(new Event("Go to build-yourself-workshop", now.plusDays(12)
            .with(LocalTime.of(12, 0)),"M", "", now));
        taskList.add(new Event("Attend hall talk", now.plusDays(3)
            .with(LocalTime.of(10, 0)),"M", "", now));
        taskList.add(new Event("Go to one-off exam briefing", now.plusDays(2)
            .with(LocalTime.of(14, 30)),"M", "", now));
        taskList.add(new RecurringEvent("Attend CS1010 lecture", now.plusDays(1)
            .with(LocalTime.of(9, 0)), "", "", now,"1 weeks",
            TaskList.convertToMinute("1 weeks"), false));
        taskList.add(new RecurringEvent("Attend GET1022 lecture", now.plusDays(3)
            .with(LocalTime.of(10, 0)), "", "", now,"1 weeks",
            TaskList.convertToMinute("1 weeks"), false));
        taskList.add(new RecurringEvent("Attend MA2111 lecture", now.plusDays(4)
            .with(LocalTime.of(12, 0)), "", "", now,"1 weeks",
            TaskList.convertToMinute("1 weeks"), false));
        taskList.add(new RecurringDeadline("Finish monthly arts proposal", now.plusDays(2)
            .with(LocalTime.of(23, 59)), "", "", now,"1 months",
            TaskList.convertToMinute("1 months"), false));
        taskList.add(new RecurringDeadline("Finish daily reflections", now
            .with(LocalTime.of(23, 59)), "", "", now,"1 days",
            TaskList.convertToMinute("1 days"), false));
        return taskList;
    }
}
