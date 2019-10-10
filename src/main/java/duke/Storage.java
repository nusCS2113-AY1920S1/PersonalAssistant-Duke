package duke;

import duke.exceptions.DukeException;
import duke.exceptions.StorageException;
import duke.items.tasks.After;
import duke.items.tasks.Deadline;
import duke.items.tasks.Event;
import duke.items.tasks.FileTask;
import duke.items.tasks.Fixed;
import duke.items.tasks.Recurring;
import duke.items.tasks.Task;
import duke.items.tasks.Tentative;
import duke.items.tasks.Todo;
import duke.items.tasks.Within;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Storage {
    private File dukeFile;

    /**
     * This constructor creates the file if needed.
     * @param fileLocation relative path of the text file to store data in.
     */
    public Storage(String fileLocation) throws DukeException {
        try {
            dukeFile = new File(fileLocation);
            if (dukeFile.getParentFile().mkdir()) {
                dukeFile.createNewFile();
            }
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    /**
     * This method retrieves data from the text file, and constructs objects to insert back into the list.
     * @return tasks
     */
    public List<Task> loadData() throws DukeException {
        Vector<Task> tasks = new Vector<>();
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        try {
            Date start;
            Date end;

            BufferedReader inputStream = new BufferedReader(new FileReader(dukeFile));
            while (true) {
                String currentLine = inputStream.readLine();
                if (currentLine == null) {
                    break;
                } else {
                    String[] arguments = currentLine.split(" \\| ");
                    switch (arguments[0]) {
                    case "T":
                        tasks.addElement(new Todo(Integer.parseInt(arguments[1]), arguments[2]));
                        break;
                    case "D":
                        Date by = parser.parse(arguments[3]).get(0).getDates().get(0);
                        tasks.addElement(new Deadline(Integer.parseInt(arguments[1]), arguments[2], by));
                        break;
                    case "F":
                        tasks.addElement(new Fixed(Integer.parseInt(arguments[1]), arguments[2], arguments[3]));
                        break;
                    case "A":
                        tasks.addElement(new After(Integer.parseInt(arguments[1]), arguments[2], arguments[3]));
                        break;
                    case "W":
                        start = parser.parse(arguments[3]).get(0).getDates().get(0);
                        end = parser.parse(arguments[4]).get(0).getDates().get(0);
                        tasks.addElement(new Within(Integer.parseInt(arguments[1]), arguments[2], start, end));
                        break;
                    case "TE":
                        start = parser.parse(arguments[3]).get(0).getDates().get(0);
                        end = parser.parse(arguments[4]).get(0).getDates().get(0);
                        tasks.addElement(new Tentative(Integer.parseInt(arguments[1]), arguments[2], start, end));
                        break;
                    case "R":
                        start = parser.parse(arguments[3]).get(0).getDates().get(0);
                        end = parser.parse(arguments[4]).get(0).getDates().get(0);
                        tasks.addElement(new Recurring(arguments[2], start, end, Long.parseLong(arguments[5]),
                                Long.parseLong(arguments[6])));
                        break;
                    case "FILE":
                        tasks.addElement(new FileTask(Integer.parseInt(arguments[1]), arguments[2]));
                        break;
                    default:
                        start = parser.parse(arguments[3]).get(0).getDates().get(0);
                        end = parser.parse(arguments[4]).get(0).getDates().get(0);
                        tasks.addElement(new Event(Integer.parseInt(arguments[1]), arguments[2], start, end));
                    }
                }
            }
            inputStream.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
        return tasks;
    }

    /**
     * This method takes in a vector and calls each task's storeString method to store its data in the correct format.
     * @param tasks A vector of tasks currently in the program.
     */
    public void setData(List<Task> tasks) throws DukeException {
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(dukeFile));
            for (Task task : tasks) {
                outputStream.write(task.storeString());
                outputStream.newLine();
            }
            outputStream.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
