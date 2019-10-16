package executor.task;

import interpreter.Parser;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    protected List<Task> taskList = new ArrayList<Task>();

    /**
     * Constructor for 'TaskList' Class.
     */
    public TaskList() {
    }

    /**
     * Deletes a task via its index.
     *
     * @param index Index of the task to be deleted
     */
    public void deleteTaskByIndex(int index) {
        this.taskList.remove(index);
    }

    /**
     * Adds a new task to the List.
     *
     * @param newTask The Task Object to be added
     */
    public void addTask(Task newTask) {
        this.taskList.add(newTask);
    }

    /**
     * Finds and prints each task that contains the string.
     *
     * @param name The substring to be found.
     */
    public void findTasks(String name) {
        for (int index = 0; index < this.taskList.size(); ++index) {
            try {
                if (this.taskList.get(index).taskName.contains(name)) {
                    printTaskByIndex(index);
                }
            } catch (Exception e) {
                System.out.println("Read invalid taskName");
            }
        }
    }

    /**
     * Prints tasks from Duke.taskList based on the index provided.
     *
     * @param indexes Varargs The indexes of tasks from taskList to be printed.
     */
    public void printTaskByIndex(int... indexes) {
        for (int index : indexes) {
            System.out.println(String.valueOf(index + 1) + ". "
                    + this.taskList.get(index).genTaskDesc());
        }
    }

    /**
     * Initializes a 'Task' subclass based on TaskType.
     * TODO: Think about how this can be neater.
     *
     * @param taskDesc The task description from the user input
     * @param taskType TaskType enum that specifies the subclass to create
     */
    public static Task createTask(TaskType taskType, String taskDesc) {
        Task newTask;
        switch (taskType) {
        case DEADLINE:
            newTask = new Deadline(taskDesc);
            break;
        case EVENT:
            newTask = new Event(taskDesc);
            break;
        case TODO:
            newTask = new ToDo(taskDesc);
            break;
        case FDURATION:
            newTask = new FixedDuration(taskDesc);
            break;
        default:
            newTask = null;
            break;
        }
        return newTask;
    }

    /**
     * Creates a Task Object from a saved (Storage) String.
     * @param userInput The saved string
     * @return Created Task Object
     */
    public static Task createTaskFromString(String userInput) {
        String[] parsedInput = Parser.parseStoredTaskDetails(userInput);
        TaskType taskType = TaskType.valueOf(parsedInput[0]);
        Task newTask = TaskList.createTask(taskType, parsedInput[1]);
        if (Boolean.valueOf(parsedInput[2])) {
            newTask.markDone();
        }
        return newTask;
    }

    // ---- Get Methods

    /**
     * Returns a List(ArrayList) of Task Objects.
     *
     * @return List(ArrayList) of Task Objects
     */
    public List<Task> getList() {
        return this.taskList;
    }

    /**
     * Returns the size of the list.
     *
     * @return Size of the list as an int
     */
    public int getSize() {
        return this.taskList.size();
    }

    /**
     * Prints each task in the taskList.
     */
    public void printTasks() {
        for (int index = 0; index < this.taskList.size(); ++index) {
            try {
                System.out.println((index + 1)
                                 + ". "
                                 + this.taskList.get(index).genTaskDesc()
                );
            } catch (Exception e) {
                System.out.println("Unable to print Task "
                         + String.valueOf(index + 1));
            }
        }
    }
}
