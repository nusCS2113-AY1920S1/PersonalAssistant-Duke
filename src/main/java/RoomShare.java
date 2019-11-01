import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.SortType;
import Enums.TaskType;
import Enums.TimeUnit;
import Model_Classes.Task;
import Operations.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class of the RoomShare program.
 */
public class RoomShare {
    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;
    private RecurHandler recurHandler;
    private TempDeleteList tempDeleteList;
    private TaskCreator taskCreator;
    private Help help;
    private ListRoutine listRoutine;

    /**
     * Constructor of a RoomShare class. Creates all necessary objects and collections for RoomShare to run
     * Also loads the ArrayList of tasks from the data.txt file
     */
    public RoomShare() throws RoomShareException {
        ui = new Ui();
        help = new Help();
        ui.startUp();
        storage = new Storage();
        parser = new Parser();
        taskCreator = new TaskCreator();
        ArrayList<Task> tempStorage = new ArrayList<>();
        tempDeleteList = new TempDeleteList(tempStorage);
        try {
            taskList = new TaskList(storage.loadFile("data.txt"));
        } catch (RoomShareException e) {
            ui.showError(e);
            ui.showLoadError();
            ArrayList<Task> emptyList = new ArrayList<>();
            taskList = new TaskList(emptyList);
        }
        listRoutine = new ListRoutine(taskList);
        recurHandler = new RecurHandler(taskList);
        if (recurHandler.checkRecurrence()) {
            ui.showChangeInTaskList();
            taskList.list();
        }
        listRoutine.list();
    }

    /**
     * Deals with the operation flow of RoomShare.
     */
    public void run() throws RoomShareException, IOException, InterruptedException {
        boolean isExit = false;
        while (!isExit) {
            String command = parser.getCommand();
            TaskType type;
            try {
                type = TaskType.valueOf(command);
            } catch (IllegalArgumentException e) {
                type = TaskType.others;
            }
            switch (type) {
            case help:
                Ui.clearScreen();
                ui.startUp();
                help.helpCommandList();
                help.showHelp(parser.getCommandLine());
                break;

            case bye:
                isExit = true;
                try {
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                parser.close();
                ui.showBye();
                break;

            case done:
                Ui.clearScreen();
                ui.startUp();
                try {
                    taskList.done(parser.getIndexRange());
                    ui.showDone();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case delete:
                Ui.clearScreen();
                ui.startUp();
                try {
                    int[] index = parser.getIndexRange();
                    taskList.delete(index, tempDeleteList);
                    ui.showDeleted(index);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case restore:
                Ui.clearScreen();
                ui.startUp();
                int restoreIndex = parser.getIndex();
                try {
                    tempDeleteList.restore(restoreIndex, taskList);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case find:
                Ui.clearScreen();
                ui.startUp();
                listRoutine.list();
                ui.showFind();
                taskList.find(parser.getKey().toLowerCase());
                break;

            case priority:
                Ui.clearScreen();
                ui.startUp();
                boolean success = true;
                try {
                    taskList.list();
                    ui.priorityInstruction();
                    taskList.setPriority(parser.getPriority());
                } catch (RoomShareException e) {
                    success = false;
                    ui.showError(e);
                    ui.priorityInstruction();
                } finally {
                    if (success) {
                        TaskList.sortTasks();
                        ui.prioritySet();
                    }
                }
                listRoutine.list();
                break;

            case add:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    if(!(CheckAnomaly.checkTask((taskCreator.create(input))))) {
                        taskList.add(taskCreator.create(input));
                        ui.showAdd();
                    } else {
                        throw new RoomShareException(ExceptionType.timeClash);
                    }
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case snooze :
                Ui.clearScreen();
                ui.startUp();
                try {
                    int index = parser.getIndex();
                    int amount = parser.getAmount();
                    TimeUnit timeUnit = parser.getTimeUnit();
                    taskList.snooze(index, amount, timeUnit);
                    ui.showSnoozeComplete(index + 1, amount, timeUnit);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case reorder:
                Ui.clearScreen();
                ui.startUp();
                int firstIndex = parser.getIndex();
                parser.discardNext();
                int secondIndex = parser.getIndex();
                ui.showReordering();
                taskList.reorder(firstIndex, secondIndex);
                listRoutine.list();
                break;

            case subtask:
                Ui.clearScreen();
                ui.startUp();
                try {
                    int index = parser.getIndexSubtask();
                    String subTasks = parser.getCommandLine();
                    new subTaskCreator(index, subTasks);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case update:
                Ui.clearScreen();
                ui.startUp();
                try {
                    int index = parser.getIndex();
                    String input = parser.getCommandLine().trim();
                    Task oldTask = taskList.get(index);
                    taskCreator.updateTask(input,oldTask);
                    ui.showUpdated(index+1);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                listRoutine.list();
                break;
                
            case sort:
                Ui.clearScreen();
                ui.startUp();
                SortType sortType;
                try {
                    sortType = parser.getSort();
                } catch (RoomShareException e) {
                    ui.showError(e);
                    sortType = SortType.priority;
                }
                TaskList.changeSort(sortType);
                ui.showChangeInPriority(sortType);
                listRoutine.list();
                break;

            case log:
                Ui.clearScreen();
                ui.startUp();
                listRoutine.list();
                try {
                    String filePath = storage.writeLogFile(TaskList.currentList());
                    ui.showLogSuccess(filePath);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            default:
                ui.showCommandError();
                break;
            }
        }
    }

    /**
     * Main function of RoomShare.
     * Creates a new instance of RoomShare class
     * @param args command line arguments
     * @throws RoomShareException Custom exception class within RoomShare program
     */
    public static void main(String[] args) throws RoomShareException, IOException, InterruptedException {
        new RoomShare().run();
        System.exit(0);
    }
}
