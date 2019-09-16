package duke.worker;

import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;

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
            while (scanner.hasNextLine()) {
                try {
                    String loadedInput = scanner.nextLine();
                    String[] parsedInput = Parser.parseStoredTask(loadedInput);
                    TaskType taskType = TaskType.valueOf(parsedInput[0]);
                    Task newTask = taskList.createTask(taskType, parsedInput[1]);
                    if (Boolean.valueOf(parsedInput[2])) {
                        newTask.markDone();
                    }
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
}
