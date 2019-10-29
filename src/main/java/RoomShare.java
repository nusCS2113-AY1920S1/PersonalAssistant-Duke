import CustomExceptions.RoomShareException;
import Enums.*;
import Model_Classes.*;
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
        recurHandler = new RecurHandler(taskList);
        if (recurHandler.checkRecurrence()) {
            ui.showChangeInTaskList();
            taskList.list();
        }
        Ui.showProgress(taskList);
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
                help.helpCommandList();
                help.showHelp(parser.getCommandLine());
                break;

            case list:
                Ui.clearScreen();
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                Ui.showProgress(taskList);
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
                try {
                    taskList.done(parser.getIndexRange());
                    ui.showDone();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                Ui.showProgress(taskList);
                break;

            case delete:
                Ui.clearScreen();
                try {
                    int[] index = parser.getIndexRange();
                    taskList.delete(index, tempDeleteList);
                    ui.showDeleted(index);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            case restore:
                int restoreIndex = parser.getIndex();
                tempDeleteList.restore(restoreIndex, taskList);
                break;

            case find:
                Ui.clearScreen();
                ui.showFind();
                taskList.find(parser.getKey().toLowerCase());
                break;

            case priority:
                Ui.clearScreen();
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
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            case add:
                Ui.clearScreen();
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
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                Ui.showProgress(taskList);
                break;

            case snooze :
                Ui.clearScreen();
                try {
                    int index = parser.getIndex();
                    int amount = parser.getAmount();
                    TimeUnit timeUnit = parser.getTimeUnit();
                    taskList.snooze(index, amount, timeUnit);
                    ui.showSnoozeComplete(index + 1, amount, timeUnit);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            case reorder:
                Ui.clearScreen();
                int firstIndex = parser.getIndex();
                ui.promptSecondIndex();
                int secondIndex = parser.getIndex();
                ui.showReordering();
                taskList.reorder(firstIndex, secondIndex);
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            case subtask:
                Ui.clearScreen();
                try {
                    int index = parser.getIndexSubtask();
                    String subTasks = parser.getCommandLine();
                    if (TaskList.currentList().get(index) instanceof Assignment) {
                        ((Assignment) TaskList.currentList().get(index)).setSubTasks(subTasks);
                    } else {
                        throw new RoomShareException(ExceptionType.subTask);
                    }
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;

            case update:
                try {
                    int index = parser.getIndex();
                    String input = parser.getCommandLine().trim();
                    System.out.println(input);
                    Task oldTask = taskList.get(index);
                    taskCreator.updateTask(input,oldTask);
                } catch (RoomShareException e) {
                    ui.showError(e);
                }
                break;
                
            case sort:
                SortType sortType = parser.getSort();
                TaskList.changeSort(sortType);
                break;

            case log:
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
