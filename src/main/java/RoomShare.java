import CustomExceptions.RoomShareException;
import Enums.*;
import Model_Classes.*;
import Operations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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


    /**
     * Constructor of a Duke class. Creates all necessary objects and collections for Duke to run
     * Also loads the ArrayList of tasks from the data.txt file
     */
    public RoomShare() throws RoomShareException {
        ui = new Ui();
        ui.startUp();
        storage = new Storage();
        parser = new Parser();
        ArrayList<Task> tempStorage = new ArrayList<>();
        tempDeleteList = new TempDeleteList(tempStorage);
        try {
            taskList = new TaskList(storage.loadFile("data.txt"));
        } catch (RoomShareException e) {
            ui.showLoadError();
            ArrayList<Task> emptyList = new ArrayList<>();
            taskList = new TaskList(emptyList);
        }
        recurHandler = new RecurHandler(taskList);
        if (recurHandler.checkRecurrence()) {
            ui.showChangeInTaskList();
            taskList.list();
        }
    }

    /**
     * Deals with the operation flow of Duke.
     */
    public void run() {
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

            case list:
                ui.showList();
                try {
                    taskList.list();
                } catch (RoomShareException e) {
                    ui.showWriteError();
                }
                break;

            case bye:
                isExit = true;
                try {
                    storage.writeFile(TaskList.currentList(), "data.txt");
                } catch (RoomShareException e) {
                    ui.showWriteError();
                }
                ui.showBye();
                break;

            case done:
                try {
                    taskList.done(parser.getIndexRange());
                    ui.showDone();
                } catch (RoomShareException e) {
                    ui.showIndexError();
                }
                break;

            case delete:
                try {
                    int[] index = parser.getIndexRange();
                    taskList.delete(index, tempDeleteList);
                    ui.showDeleted(index);
                } catch (RoomShareException e) {
                    ui.showIndexError();
                }
                break;

             case restore:
                int restoreIndex = parser.getIndex();
                tempDeleteList.restore(restoreIndex, taskList);
                break;

            case find:
                ui.showFind();
                taskList.find(parser.getKey().toLowerCase());
                break;

            case priority:
                boolean success = true;
                try {
                    taskList.list();
                    ui.priority();
                    taskList.setPriority(parser.getPriority());
                } catch (RoomShareException e) {
                    success = false;
                    ui.priority();
                } finally {
                    if (success) {
                        taskList.sortPriority();
                        ui.prioritySet();
                    }
                }
                break;

            case add:
                String input = parser.getCommandLine();
                taskCreator = new TaskCreator();
                taskList.add(taskCreator.create(input));
                break;

            case snooze :
                try {
                    int index = parser.getIndex();
                    TaskList.currentList().get(index - 1);
                    ui.showSnooze();
                    int amount = parser.getAmount();
                    TimeUnit timeUnit = parser.getTimeUnit();
                    taskList.snooze(index, amount, timeUnit);
                    ui.showSnoozeComplete();
                } catch (IndexOutOfBoundsException e) {
                    ui.showIndexError();
                } catch (IllegalArgumentException e) {
                    ui.showTimeError();
                }
                break;

            case reorder:
                int firstIndex = parser.getIndex();
                ui.promptSecondIndex();
                int secondIndex = parser.getIndex();
                ui.showReordering();
                taskList.reorder(firstIndex, secondIndex);
                break;

            case subtask:
                TaskList.currentList().get(parser.getIndex()).setSubTasks(parser.getCommandLine());
                break;

            default:
                ui.showCommandError();
                break;
            }
        }
    }

    /**
     * Main function of Duke.
     * Creates a new instance of Duke class
     * @param args command line arguments
     * @throws RoomShareException Custom exception class within Duke program
     */
    public static void main(String[] args) throws RoomShareException {
        new RoomShare().run();
    }
}
