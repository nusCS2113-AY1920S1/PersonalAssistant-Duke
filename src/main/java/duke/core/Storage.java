package duke.core;

import duke.task.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Storage class that deals with reading tasks from
 * a file and saving tasks in the file.
 */
public class Storage {
    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param path A string that represents the path of the file to read or
     *             write.
     */
    public Storage(String path) {
        this.filePath = path;
    }

    /**
     * Read tasks from the file and store into a ArrayList of task.
     *
     * @return A ArrayList of tasks from the file.
     * @throws DukeException If file is not found.
     */
    public ArrayList<Task> load() throws DukeException {
        File newDuke = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Scanner ss = new Scanner(newDuke);
            while (ss.hasNext()) {
                String[] newTask = ss.nextLine().split(" \\| ");
                if (newTask[0].equals("T")) {
                    Task x = new Todo(newTask[2]);
                    if (newTask[1].equals("1")) {
                        x.markAsDone();
                    }
                    if ((newTask[3] != null) && !(newTask[3].equals("ONCE"))) {
                        x.makeTaskRecurring(giveFrequency(newTask[3]));
                    }
                    tasks.add(x);
                }
                else if (newTask[0].equals("D")) {
                    Task t = new Deadline(newTask[2], newTask[3]);
                    if (newTask[1].equals("1")) {
                        t.markAsDone();
                    }
                    if ((newTask[4] != null) && !(newTask[4].equals("ONCE"))) {
                        t.makeTaskRecurring(giveFrequency(newTask[4]));
                    }
                    tasks.add(t);
                }
                else if (newTask[0].equals("E")) {
                    Task t = new Event(newTask[2], newTask[3]);
                    if (newTask[1].equals("1")) {
                        t.markAsDone();
                    }
                    if ((newTask[4] != null) && !(newTask[4].equals("ONCE"))) {
                        t.makeTaskRecurring(giveFrequency(newTask[4]));
                    }
                    tasks.add(t);
                }

                else if (newTask[0].equals("P")) {
                    Task t = new PeriodTask(newTask[2], newTask[3], newTask[4]);
                    if (newTask[1].equals("1")) {
                        t.markAsDone();
                    }
                    tasks.add(t);
                }else if (newTask[0].equals("F")) {
                    Task x = new FixedDurationTask(newTask[2], newTask[3]);
                    if (newTask[1].equals("1")) {
                        x.markAsDone();
                    }
                    tasks.add(x);
                }

            }
            return tasks;
        } catch (FileNotFoundException e) {
            throw new DukeException("File is not found!");
        }
    }

    /**
     * Saves tasks to the local file.
     *
     * @param task The TaskList storing tasks.
     * @throws DukeException If writing to the local file failed.
     */
    public void save(ArrayList<Task> task) throws DukeException {
        try {
            FileWriter fileWriter = new FileWriter("./data/duke.txt");
            for (Task t : task) {
                fileWriter.write(t.writeTxt() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeException("File writing process encounters an error " + e.getMessage());
        }
    }

    private Task.RecurringFrequency giveFrequency(String string) {
        switch (string) {
            case "DAILY":
                return Task.RecurringFrequency.DAILY;
            case "WEEKLY":
                return Task.RecurringFrequency.WEEKLY;
            case "MONTHLY":
                return Task.RecurringFrequency.MONTHLY;
            default:
                return Task.RecurringFrequency.ONCE;
        }
    }

}
