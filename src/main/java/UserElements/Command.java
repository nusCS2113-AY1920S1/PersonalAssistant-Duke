package UserElements;

import Events.EventTypes.Deadline;
import Events.EventTypes.Event;
import Events.EventTypes.ToDo;
import Events.Storage.TaskList;
import main.java.Events.EventTypes.Task;
import main.java.Events.Storage.Storage;
import main.java.UserElements.UI;

/**
 * Represents a command that is passed via user input.
 * Multiple types of commands are possible, executed using switch case method.
 */
public class Command {

    /**
     * The String representing the type of command e.g add/delete task
     */
    protected String command;

    /**
     * The String representing the continuation of the command, if it exists.
     * Contains further specific instructions about the command passed e.g which task to add or delete
     */
    protected String continuation;

    /**
     * Creates a new command with the command type and specific instructions
     *
     * @param command      The Model_Class.Command type
     * @param continuation The Model_Class.Command specific instructions
     */
    public Command(String command, String continuation) {
        this.command = command;
        this.continuation = continuation;
    }

    /**
     * Creates a new command where only command param is passed.
     * Specific instructions not necessary for these types of commands.
     *
     * @param command The Model_Class.Command type
     */
    public Command(String command) {
        this.command = command;
        this.continuation = "";
    }

    /**
     * Executes the command stored.
     *
     * @param tasks   Class containing the list of tasks and all relevant methods.
     * @param ui      Class containing all relevant user interface instructions.
     * @param storage Class containing access to the storage file and related instructions.
     */
    public void execute(TaskList tasks, UI ui, Storage storage) {
        boolean changesMade = true;
        switch (command) {
            case "list":
                ui.printListOfTasks(tasks);
                changesMade = false;
                break;

            case "done":
                try {
                    int taskNo = Integer.parseInt(continuation);
                    tasks.getTask(taskNo - 1).markAsDone();
                    ui.taskDone(tasks.getTask(taskNo - 1));
                    break;
                } catch (IndexOutOfBoundsException outOfBoundsE) {
                    ui.noSuchTask();
                    break;
                } catch (NumberFormatException notInteger) {
                    ui.notAnInteger();
                    break;
                }

            case "delete":
                try {
                    int taskNo = Integer.parseInt(continuation);
                    Task currTask = tasks.getTask(taskNo - 1);
                    tasks.deleteTask(taskNo - 1);
                    ui.taskDeleted(currTask);
                    break;
                } catch (IndexOutOfBoundsException outOfBoundsE) {
                    ui.noSuchTask();
                    break;
                } catch (NumberFormatException notInteger) {
                    ui.notAnInteger();
                    break;
                }

            case "find":
                String searchFor = continuation;
                String allTasksFound = "";
                for (Task taskFound : tasks.getTaskArrayList()) {
                    int index = 1;
                    if (taskFound.getDescription().contains(searchFor)) {
                        allTasksFound += index + ". " + taskFound.toString() + "\n";
                    }
                    ++index;
                }

                boolean tasksFound = !allTasksFound.isEmpty();
                ui.searchTasks(allTasksFound, tasksFound);
                changesMade = false;
                break;

            case "todo":
                if (continuation.isEmpty()) {
                    ui.taskDescriptionEmpty();
                    break;
                }
                ui.taskAdded(new ToDo(continuation), tasks.getNumTasks());
                break;

            case "deadline":
                if (continuation.isEmpty()) {
                    ui.taskDescriptionEmpty();
                    break;
                }
                try {
                    int slashPos = continuation.indexOf("/by"); //to find index of position and date
                    String date = continuation.substring(slashPos + 4);
                    String description = continuation.substring(0, slashPos);
                    boolean succeeded = tasks.addTask(new Deadline(description, date));
                    if (succeeded) {
                        ui.taskAdded(new Deadline(description, date), tasks.getNumTasks());
                    } else {
                        ui.scheduleClash(new Deadline(description, date));
                    }
                    break;
                } catch (StringIndexOutOfBoundsException outOfBoundsE) {
                    ui.deadlineFormatWrong();
                    break;
                }

            case "event":
                if (continuation.isEmpty()) {
                    ui.taskDescriptionEmpty();
                    break;
                }
                if (continuation.contains("/every")) {
                    try {
                        int datePos = continuation.indexOf("/at"); //to find index of position and date
                        int periodPos = continuation.indexOf("/every"); //to find index of position and period
                        String description = continuation.substring(0, datePos);
                        String date = continuation.substring(datePos + 4, periodPos);
                        int period = Integer.parseInt(continuation.substring(periodPos + 7));
                        boolean succeeded = tasks.addRecurringEvent(new Event(description, date), period);
                        if (succeeded) {
                            ui.recurringTaskAdded(new Event(description, date), tasks.getNumTasks(), period);
                        } else {
                            ui.scheduleClash(new Event(description, date));
                        }
                        break;
                    } catch (StringIndexOutOfBoundsException outOfBoundsE) {
                        ui.recursionFormatWrong();
                        break;
                    }
                }
                try {
                    int slashPos = continuation.indexOf("/at"); //to find index of position and date
                    String date = continuation.substring(slashPos + 4);
                    String description = continuation.substring(0, slashPos);
                    boolean succeeded = tasks.addTask(new Event(description, date));
                    if (succeeded) {
                        ui.taskAdded(new Event(description, date), tasks.getNumTasks());
                    } else {
                        ui.scheduleClash(new Event(description, date));
                    }
                    break;
                } catch (StringIndexOutOfBoundsException outOfBoundsE) {
                    ui.eventFormatWrong();
                    break;
                }

            default:
                ui.printInvalidCommand();
                break;
        }
        if (changesMade) {
            storage.saveToFile(tasks, ui);
        }
    }
}