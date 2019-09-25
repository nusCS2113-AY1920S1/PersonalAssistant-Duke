package seedu.duke.data;

import java.text.ParseException;

import seedu.duke.task.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

import static seedu.duke.parser.DateTimeParser.getDateTime;

/**
 * A class that stores current task list and loads it on request from disc.
 */
public class Storage {
    /**
     * Path to the file where tasks are stored and retreived
     * from.
     */
    private String filePath;
    private Schedule schedule;

    /**
     * Initializes filePath.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
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
                    list.add(new ToDo(taskString[2]));
                } else if (taskString[0].equals("D")) {
                    LocalDateTime localDateTime = getDateTime(taskString[3]);
                    list.add(new Deadline(taskString[2], localDateTime));
                    try {
                        String dateOnly = taskString[3].split(" ")[0];
                        Date date = schedule.convertStringToDate(dateOnly);
                        Task lastTask = list.get(list.size() - 1);
                        schedule.addToSchedule(lastTask, date);
                    } catch (ParseException ignored) {
                        return null;
                    }
                } else if (taskString[0].equals("R")) {
                    String[] dateTime = taskString[3].split(" and ");
                    LocalDateTime from = getDateTime(dateTime[0]);
                    LocalDateTime by = getDateTime(dateTime[1]);
                    list.add(new RangedTask(taskString[2], from, by));
                } else if (taskString[0].equals("A")) {
                    list.add(new DoAfter(taskString[2], taskString[3]));
                } else if (taskString[0].equals("RC")) {
                    LocalDateTime on = getDateTime(taskString[3]);
                    int periodInMin = Integer.parseInt(taskString[5]);
                    list.add(new RecurringTask(taskString[2], on, taskString[4], periodInMin));
                } else {
                    LocalDateTime at = getDateTime(taskString[3]);
                    list.add(new Event(taskString[2], at));
                    try {
                        String dateOnly = taskString[3].split(" ")[0];
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
}
