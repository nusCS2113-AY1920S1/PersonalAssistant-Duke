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
    private TaskList doneTaskList;
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
            TaskType type;
            try {
                String command = parser.getCommand();
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

            case list:
                Ui.clearScreen();
                ui.startUp();
                listRoutine.list();
                break;

            case done:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int[] index = parser.getIndexRange(input);
                    taskList.done(index);
                    ui.showDone();
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    storage.writeFile(TaskList.currentList(), "data.txt");
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case delete:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int[] index = parser.getIndexRange(input);
                    taskList.delete(index, tempDeleteList);
                    ui.showDeleted(index);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    storage.writeFile(TaskList.currentList(), "data.txt");
                    ui.showError(e);
                }
                listRoutine.list();
                break;

            case restore:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int restoreIndex = parser.getIndex(input);
                    tempDeleteList.restore(restoreIndex, taskList);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    storage.writeFile(TaskList.currentList(), "data.txt");
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
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    success = false;
                    ui.showError(e);
                    ui.priorityInstruction();
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } finally {
                    if (success) {
                        TaskList.sortTasks();
                        ui.prioritySet();
                    }
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                listRoutine.list();
                break;

            case add:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    if(!(CheckAnomaly.checkTask((taskCreator.create(input))))) {
                        if( !(CheckAnomaly.checkDuplicate((taskCreator.create(input)))) ) {
                            taskList.add(taskCreator.create(input));
                            ui.showAdd();
                            storage.writeFile(TaskList.currentList(), "data.txt");
                        } else {
                            throw new RoomShareException(ExceptionType.duplicateTask);
                        }
                    } else {
                        throw new RoomShareException(ExceptionType.timeClash);
                    }
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                listRoutine.list();
                break;

            case snooze:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int index = parser.getIndex(input);
                    int amount = parser.getAmount(input);
                    TimeUnit timeUnit = parser.getTimeUnit(input);
                    if (amount < 0)
                        throw new RoomShareException(ExceptionType.negativeTimeAmount);
                    taskList.snooze(index, amount, timeUnit);
                    ui.showSnoozeComplete(index + 1, amount, timeUnit);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                listRoutine.list();
                break;

            case reorder:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int firstIndex = parser.getIndex(input, 0);
                    int secondIndex = parser.getIndex(input, 1);
                    taskList.reorder(firstIndex, secondIndex);
                    ui.showReordering();
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                listRoutine.list();
                break;

            case subtask:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int index = parser.getIndexSubtask(input);
                    String subTasks = parser.getSubTasks(input);
                    new subTaskCreator(index, subTasks);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                listRoutine.list();
                break;

            case update:
                Ui.clearScreen();
                ui.startUp();
                try {
                    String input = parser.getCommandLine();
                    int index = parser.getIndex(input);
                    Task oldTask = taskList.get(index);
                    taskCreator.updateTask(input,oldTask);
                    ui.showUpdated(index+1);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                listRoutine.list();
                break;
                
            case sort:
                Ui.clearScreen();
                ui.startUp();
                SortType sortType;
                try {
                    String input = parser.getCommandLine();
                    sortType = parser.getSort(input);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    sortType = SortType.priority;
                    storage.writeFile(TaskList.currentList(), "data.txt");
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
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                break;

            case completed:
                Ui.clearScreen();
                ui.startUp();
                listRoutine.list();
                try {
                    taskList.showCompleted();
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showError(e);
                    storage.writeFile(TaskList.currentList(), "data.txt");
                }
                break;

            case overdue:
                Ui.clearScreen();
                ui.startUp();
                listRoutine.list();
                try {
                    taskList.showOverdue();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            default:
                Ui.clearScreen();
                ui.startUp();
                listRoutine.list();
                ui.showCommandError();
                storage.writeFile(TaskList.currentList(), "data.txt");
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
