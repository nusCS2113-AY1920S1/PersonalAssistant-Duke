package spinbox;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;
import spinbox.items.tasks.After;
import spinbox.items.tasks.Deadline;
import spinbox.items.tasks.Event;
import spinbox.items.tasks.FileTask;
import spinbox.items.tasks.Fixed;
import spinbox.items.tasks.Recurring;
import spinbox.items.tasks.Task;
import spinbox.items.tasks.Tentative;
import spinbox.items.tasks.Todo;
import spinbox.items.tasks.Within;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Vector;

public class Storage {
    private File dukeFile;

    /**
     * This constructor creates the file if needed.
     * @param fileLocation relative path of the text file to store data in.
     */
    public Storage(String fileLocation) throws SpinBoxException {
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
    public List<Task> loadData() throws SpinBoxException {
        Vector<Task> tasks = new Vector<>();
        try {
            DateTime start;
            DateTime end;

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
                        start = new DateTime(arguments[3]);
                        tasks.addElement(new Deadline(Integer.parseInt(arguments[1]), arguments[2], start));
                        break;
                    case "F":
                        tasks.addElement(new Fixed(Integer.parseInt(arguments[1]), arguments[2], arguments[3]));
                        break;
                    case "A":
                        tasks.addElement(new After(Integer.parseInt(arguments[1]), arguments[2], arguments[3]));
                        break;
                    case "W":
                        start = new DateTime(arguments[3]);
                        end = new DateTime(arguments[4]);
                        tasks.addElement(new Within(Integer.parseInt(arguments[1]), arguments[2], start, end));
                        break;
                    case "TE":
                        start = new DateTime(arguments[3]);
                        end = new DateTime(arguments[4]);
                        tasks.addElement(new Tentative(Integer.parseInt(arguments[1]), arguments[2], start, end));
                        break;
                    case "R":
                        start = new DateTime(arguments[3]);
                        end = new DateTime(arguments[4]);
                        tasks.addElement(new Recurring(arguments[2], start, end, Long.parseLong(arguments[5]),
                                Long.parseLong(arguments[6])));
                        break;
                    case "FILE":
                        tasks.addElement(new FileTask(Integer.parseInt(arguments[1]), arguments[2]));
                        break;
                    default:
                        start = new DateTime(arguments[3]);
                        end = new DateTime(arguments[4]);
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
    public void setData(List<Task> tasks) throws SpinBoxException {
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
