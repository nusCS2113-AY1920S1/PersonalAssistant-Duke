package storage;

import duke.exception.DukeException;
import executor.task.Task;
import executor.task.TaskList;
import interpreter.Parser;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class StorageTask {
    protected String filePath;

    /**
     * * Constrctor for the 'StorageTask' Class.
     * @param filePath The file path to be used to store and load data
     */
    public StorageTask(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method to save the current list of tasks.
     * @param taskList TaskList that houses all the Tasks to be saved
     */
    public void saveData(TaskList taskList) throws DukeException {
        try {
            FileWriter writer = new FileWriter(this.filePath);
            for (Task task : taskList) {
                String strSave = Parser.encodeTask(task);
                writer.write(strSave);
            }
            writer.close();
        } catch (Exception e) {
            throw new DukeException("Unable to load saved Task Data.\n");
        }
    }

    /**
     * Method to load previously saved list of tasks.
     * @param taskList TaskList that will house all the Tasks
     */
    public void loadData(TaskList taskList) throws DukeException {
        try {
            File file = new File(this.filePath);
            Scanner scanner = new Scanner(file);
            Task newTask;
            while (scanner.hasNextLine()) {
                String loadedInput = scanner.nextLine();
                if (loadedInput.equals("")) {
                    break;
                }
                newTask = loadTaskFromStorageString(loadedInput);
                taskList.addTask(newTask);
            }
        } catch (Exception e) {
            throw new DukeException("No Previously Saved Tasks.\n");
        }
    }

    /**
     * Converts saved String in Storage to actual Task Object.
     * @param loadedInput The saved String to be converted
     * @return Task Object from String
     */
    public Task loadTaskFromStorageString(String loadedInput) {
        TaskList queuedTasks = new TaskList();
        Task newTask = null;
        Task queuedTask = null;
        String[] taskStrings = Parser.parseStoredTask(loadedInput);
        for (String taskString : taskStrings) {
            if (newTask == null) {
                try {
                    newTask = TaskList.createTaskFromString(taskString);
                } catch (DukeException e) {
                    // Task cannot be created. Continue
                }
            } else {
                try {
                    queuedTask = TaskList.createTaskFromString(taskString);
                } catch (DukeException e) {
                // Task cannot be created. Continue
                }
                queuedTasks.add(queuedTask);
            }
        }
        if (queuedTask != null) {
            newTask.setQueuedTasks(queuedTasks);
        }
        return newTask;
    }
}
