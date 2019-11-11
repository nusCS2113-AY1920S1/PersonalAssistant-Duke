package storage;

import duke.exception.DukeException;
import storage.task.Task;
import storage.task.TaskList;
import interpreter.Parser;
import ui.gui.MainWindow;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
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
     * Loads Task test data for Testers to try.
     * @param taskList TaskList to store test Tasks
     * @throws DukeException Cannot find test Data.
     */
    void loadTestData(TaskList taskList) throws DukeException {
        InputStream testerTaskData = MainWindow.class
                .getResourceAsStream("/testers/testersTask.txt");
        if (testerTaskData == null) {
            throw new DukeException("No file detected.");
        }
        Scanner s = new Scanner(testerTaskData).useDelimiter("\\A");
        Task newTask;
        while (s.hasNextLine()) {
            String loadedInput = s.nextLine();
            if (loadedInput.equals("")) {
                break;
            }
            newTask = loadTaskFromStorageString(loadedInput);
            taskList.addTask(newTask);
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
                newTask = TaskList.createTaskFromString(taskString);
            } else {
                queuedTask = TaskList.createTaskFromString(taskString);
                queuedTasks.add(queuedTask);
            }
        }
        if (queuedTask != null) {
            newTask.setQueuedTasks(queuedTasks);
        }
        return newTask;
    }
}
