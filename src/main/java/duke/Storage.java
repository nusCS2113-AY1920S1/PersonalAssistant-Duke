package duke;


import duke.exception.DukeTaskClashException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Storage {
    private File filePath;

    /**
     * Constructor for Storage Class.
     * Create directory and file in the event that it does not exist
     * @param filePath File, the path of the duke.txt File
     */
    public Storage(File filePath) {
        this.filePath = filePath;
        try {
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Unable to create file.\n");
        }
    }

    /**
     * Read all the tasks that are stored in the file. Loads and restore
     * progress of duke.
     * @return TaskList with all the tasks that were stored in file.
     */
    public TaskList load() {
        TaskList tasks = new TaskList();
        try {
            FileReader rd = new FileReader(filePath);
            BufferedReader br = new BufferedReader(rd);

            String message;
            int counter = 0;

            while ((message = br.readLine()) != null) {
                String[] arrStr = message.split(" \\| ");

                if (arrStr.length == 3) {
                    tasks.addTask(new Todo(arrStr[2]));
                } else if (arrStr[0].equals("D")) {
                    tasks.addTask(new Deadline(arrStr[2], arrStr[3]));
                } else {
                    tasks.addTask(new Event(arrStr[2], arrStr[3]));
                }

                if (arrStr[1].equals("1")) {
                    tasks.get(counter).markAsDone();
                }

                counter++;
            }

            br.close();
            rd.close();

        } catch (IOException e) {
            System.out.println("Unable to load file.\n");
        } catch (DukeTaskClashException e) {
            System.out.println(e.toString());
        }

        return tasks;
    }

    /**
     * Deletes old file and creates new file with the same filepath.
     * Writes all the tasks that has been stored in TaskList to the new file
     * for duke.
     * @param tasks The list of task that is stored by duke
     */
    public void write(TaskList tasks) {

        try {
            filePath.delete();
            filePath.createNewFile();
            FileWriter wr = new FileWriter(filePath, true);

            for (Task i : tasks) {
                wr.write(i.writeToFile() + "\n");
            }
            wr.close();
        } catch (IOException e) {
            System.out.println(" BYE, sorry you suck.");
        }
    }
}
