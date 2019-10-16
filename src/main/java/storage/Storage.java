package storage;

import executor.task.Task;
import executor.task.TaskList;
import interpreter.Parser;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Storage {
    protected String filePath;

    /**
     * * Constrctor for the 'Storage' Class.
     *
     * @param filePath The file path to be used to store and load data
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method to save the current list of tasks.
     *
     * @param taskList TaskList class
     */
    public void saveData(TaskList taskList) {
        try {
            FileWriter writer = new FileWriter(this.filePath);
            for (Task task : taskList.getList()) {
                String strSave = Parser.encodeTask(task);
                writer.write(strSave);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to load previously saved list of tasks.
     *
     * @return TaskList class
     */
    public TaskList loadData() {
        TaskList taskList = new TaskList();
        try {
            File file = new File(this.filePath);
            Scanner scanner = new Scanner(file);
            Task newTask;
            while (scanner.hasNextLine()) {
                try {
                    String loadedInput = scanner.nextLine();
                    newTask = loadTaskFromStorageString(loadedInput);
                    taskList.addTask(newTask);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("No Previously saved Data.");
        }
        return taskList;
    }

    /**
     * Converts saved String in Storage to actual Task Object.
     * @param loadedInput The saved String to be converted
     * @return Task Object from String
     */
    public static Task loadTaskFromStorageString(String loadedInput) {
        TaskList queuedTasks = new TaskList();
        Task newTask = null;
        Task queuedTask = null;
        String[] taskStrings = Parser.parseStoredTask(loadedInput);
        for (String taskString : taskStrings) {
            if (newTask == null) {
                newTask = TaskList.createTaskFromString(taskString);
            } else {
                queuedTask = TaskList.createTaskFromString(taskString);
                queuedTasks.getList().add(queuedTask);
            }
        }
        if (queuedTask != null) {
            newTask.setQueuedTasks(queuedTasks);
        }
        return newTask;
    }
}
