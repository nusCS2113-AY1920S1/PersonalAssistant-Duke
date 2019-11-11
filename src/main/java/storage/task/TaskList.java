package storage.task;

import duke.exception.DukeException;
import interpreter.Parser;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {
    /**
     * Constructor for 'TaskList' Class.
     */
    public TaskList() {
    }

    /**
     * Deletes a task via its index.
     * @param index Index of the task to be deleted
     */
    public void deleteTaskByIndex(int index) {
        this.remove(index);
    }

    /**
     * Adds a new task to the List.
     * @param newTask The Task Object to be added
     */
    public void addTask(Task newTask) {
        this.add(newTask);
    }

    /**
     * Finds and prints each task that contains the string.
     * @param name The substring to be found.
     */
    public TaskList getTasksByName(String name) {
        TaskList filteredTaskList = new TaskList();
        for (int index = 0; index < this.size(); ++index) {
            try {
                if (this.get(index).taskName.contains(name)) {
                    filteredTaskList.addTask(this.get(index));
                }
            } catch (Exception e) {
                // Read invalid taskName"
            }
        }
        return filteredTaskList;
    }

    /**
     * Prints tasks from taskList based on the index provided.
     * @param indexes Varargs The indexes of tasks from taskList to be printed.
     * @return String representing the list of Tasks to be printed to the User.
     */
    public String getPrintableTaskByIndex(int... indexes) {
        StringBuilder outputStr = new StringBuilder();
        for (int index : indexes) {
            outputStr.append(index + 1)
                    .append(". ")
                    .append(this.get(index).genTaskDesc())
                    .append("\n");
        }
        return outputStr.toString();
    }

    public Task getMostRecentTaskAdded() {
        return this.get(this.size() - 1);
    }

    /**
     * Initializes a 'Task' subclass based on TaskType.
     * TODO: Think about how this can be neater.
     * @param taskDesc The task description from the user input
     * @param taskType TaskType enum that specifies the subclass to create
     */
    public static Task createTask(TaskType taskType, String taskDesc) throws DukeException {
        Task newTask;
        try {
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
            case RECUR:
                newTask = new Recurring(taskDesc);
                break;
            default:
                newTask = null;
                break;
            }
        } catch (DukeException f) {
            throw f;
        } catch (Exception e) {
            throw new DukeException("Unable to create task.\n");
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
        Task newTask;
        try {
            newTask = TaskList.createTask(taskType, parsedInput[1]);
        } catch (DukeException e) {
            newTask = new Task(parsedInput[1]);
            newTask.recordTaskDetails(parsedInput[1]);
            newTask.setTaskType(taskType);
        }
        if (Boolean.parseBoolean(parsedInput[2])) {
            newTask.markDone();
        }
        return newTask;
    }

    // ---- Get Methods

    /**
     * Returns a String representing each task in the taskList.
     * @return String containing all the Tasks to be printed by the User.
     */
    public String getPrintableTasks() {
        StringBuilder outputStr = new StringBuilder();
        for (int index = 0; index < this.size(); ++index) {
            try {
                outputStr.append(index + 1)
                        .append(". ")
                        .append(this.get(index).genTaskDesc())
                        .append("\n");
                ;
            } catch (Exception e) {
                outputStr.append("Unable to print Task ")
                         .append(index + 1)
                        .append("\n")
                ;
            }
        }
        return outputStr.toString();
    }

    /**
     * Loads all queued Tasks from the now-done Task to the main TaskList.
     * @param mainTask The Task that has been marked done
     */
    public void loadQueuedTasks(Task mainTask) {
        TaskList queuedTasks = mainTask.getQueuedTasks();
        if (queuedTasks == null) {
            return;
        }
        for (Task newTask : queuedTasks) {
            this.addTask(newTask);
        }
        mainTask.setQueuedTasks(null);
    }
}
