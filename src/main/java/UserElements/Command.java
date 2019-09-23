package UserElements;

import Events.EventTypes.Event;
import Events.EventTypes.Task;
import Events.Formatting.DateObj;
import Events.Storage.EventList;
import Events.Storage.Storage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


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
    public void execute(EventList tasks, UI ui, Storage storage) {
        boolean changesMade = true;
        switch (command) {
            case "list":
                ui.printListOfTasks(tasks);
                changesMade = false;
                break;

            case "reminder":
                ui.printReminder(tasks);
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
                int index = 1;
                for (Task taskFound : tasks.getTaskArrayList()) {
                    if (taskFound.getDescription().contains(searchFor)) {
                        allTasksFound += index + ". " + taskFound.toString() + "\n";
                    }
                    index++;
                }

                boolean tasksFound = !allTasksFound.isEmpty();
                ui.searchTasks(allTasksFound, tasksFound);
                changesMade = false;
                break;

            case "event":
                if (continuation.isEmpty()) {
                    ui.taskDescriptionEmpty();
                    break;
                }
                int NO_PERIOD = -1;

                try {
                    EntryForRecEvent entryForRecEvent = new EntryForRecEvent().invoke(); //separate all info into relevant details
                    Event newEvent = new Event(entryForRecEvent.getDescription(), entryForRecEvent.getDate());
                    boolean succeeded = false;

                    if (entryForRecEvent.getPeriod() == NO_PERIOD) { //add non-recurring event
                        succeeded = tasks.addTask(newEvent);
                    } else { //add recurring event
                        succeeded = tasks.addRecurringEvent(newEvent, entryForRecEvent.getPeriod());
                    }

                    if (succeeded) {
                        if (entryForRecEvent.getPeriod() == NO_PERIOD) {
                            ui.taskAdded(newEvent, tasks.getNumTasks());
                        } else {
                            ui.recurringTaskAdded(newEvent, tasks.getNumTasks(), entryForRecEvent.getPeriod());
                        }
                    } else {
                        ui.scheduleClash(newEvent);
                    }
                    break;

                } catch (StringIndexOutOfBoundsException outOfBoundsE) {
                    ui.eventFormatWrong();
                    break;
                }

            case "view":
                if (continuation.isEmpty()) {
                    ui.taskDescriptionEmpty();
                    break;
                }
                String dateToView = continuation;
                String foundTask = "";
                int viewIndex = 1;
                DateObj findDate = new DateObj(dateToView);
                for (Event viewTask : tasks.getTaskArrayList()) {
                    if (viewTask.toString().contains(findDate.toOutputString())) {
                        foundTask += viewIndex + ". " + viewTask.toString() + "\n";
                        viewIndex++;
                    }
                }
                boolean isTasksFound = !foundTask.isEmpty();
                ui.searchTasks(foundTask, isTasksFound);
                changesMade = false;
                break;

            case "check":
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                DateObj today = new DateObj(f.format(new Date()));
                Queue<String> daysFree = new LinkedList<String>();
                int addDays = 1;
                while(daysFree.size() <= 3) {
                    boolean isFree = true;
                    for(Task viewTask : tasks.getTaskArrayList()) {
                        if(viewTask.toString().contains(today.toOutputString())) {
                            isFree = false;
                            break;
                        }
                    }
                    if(isFree) {
                        daysFree.add(today.toOutputString());
                    }
                    today.addDays(addDays);
                }
                ui.printFreeDays(daysFree);//print out the 3 free days
                break;

            default:
                ui.printInvalidCommand();
                break;
        }
        if (changesMade) {
            storage.saveToFile(tasks, ui);
        }
    }

    /**
     * Contains all info concerning a new entry for a recurring event.
     */
    private class EntryForRecEvent {
        private String description;
        private String date;
        private int period;

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public int getPeriod() {
            return period;
        }

        /**
         * contains all info regarding an entry for a non-recurring event
         * @return
         */
        public EntryForRecEvent invoke() {
            int NON_RECURRING = -1;
            int datePos = continuation.indexOf("/at"); //to find index of position and date
            int periodPos = continuation.indexOf("/every"); //to find index of position and period

            if (periodPos == -1) { //cant find period extension of command, event is non-recurring
                periodPos = continuation.length();
                date = continuation.substring(datePos + 4);
                period = NON_RECURRING;
            } else {
                period = Integer.parseInt(continuation.substring(periodPos + 7));
                date = continuation.substring(datePos + 4, periodPos);
            }
            description = continuation.substring(0, datePos);

            return this;
        }
    }
}