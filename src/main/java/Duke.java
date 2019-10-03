import CustomExceptions.DukeException;
import Enums.*;
import Model_Classes.*;
import Operations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * main class of the Duke program
 */
public class Duke {
    public static final int HOURS = 1000 * 60 * 60;
    public static final int MINUTES = 1000 * 60;
    public static final int SECONDS = 1000;
    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;
    private RecurHandler recurHandler;

    /**
     * Constructor of a Duke class. Creates all necessary objects and collections for Duke to run
     * Also loads the ArrayList of tasks from the data.txt file
     */
    public Duke() throws DukeException {
        ui = new Ui();
        ui.startUp();
        storage = new Storage();
        parser = new Parser();
        try {
            taskList = new TaskList(storage.loadFile("data.txt"));
        } catch (DukeException e) {
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
    public void run() throws DukeException {
        boolean isExit = false;
        boolean isExitRecur = false;
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
                    ui.help();
                    break;

                case list:
                    ui.showList();
                    try {
                        taskList.list();
                    } catch (DukeException e) {
                        ui.showWriteError();
                    }
                    break;

                case bye:
                    isExit = true;
                    try {
                        storage.writeFile(TaskList.currentList(), "data.txt");
                    } catch (DukeException e) {
                        ui.showWriteError();
                    }
                    ui.showBye();
                    break;

                case done:
                    try {
                        ui.showDone();
                        taskList.done(parser.getIndex());
                    } catch (DukeException e) {
                        ui.showIndexError();
                    }
                    break;

                case delete:
                    try {
                        int index = parser.getIndex();
                        taskList.delete(index);
                        ui.showDeleted(index);
                    } catch (DukeException e) {
                        ui.showIndexError();
                    }
                    break;

                case find:
                    ui.showFind();
                    taskList.find(parser.getKey());
                    break;

                case todo:
                    try {
                        ui.showAdd();
                        ToDo temp = new ToDo(parser.getDescription());
                        taskList.add(temp);
                    } catch (DukeException e) {
                        ui.showEmptyDescriptionError();
                    }
                    break;

                case deadline:
                    try {
                        ui.showAdd();
                        String[] deadlineArray = parser.getDescriptionWithDate();
                        String[] ar = parser.getDate(deadlineArray);
                        Date by = parser.formatDate(ar[1]);
                        Deadline temp = new Deadline(ar[0], by);
                        taskList.add(temp);
                    } catch (DukeException e) {
                        ui.showDateError();
                    }
                    break;

                case event:
                    try {
                        String[] eventArray = parser.getDescriptionWithDate();
                        String[] ar = parser.getDate(eventArray);
                        Date at = parser.formatDate(ar[1]);

                        ui.promptForReply();
                        ReplyType replyType;
                        try {
                            replyType = parser.getReply();
                        } catch (IllegalArgumentException e) {
                            replyType = ReplyType.others;
                        }
                        switch (replyType) {

                        case yes:
                            ui.promptForDuration();
                            TimeUnit timeUnit = parser.getTimeUnit();
                            ui.promptForTime();
                            int duration = parser.getAmount();
                            FixedDuration fixedDuration = new FixedDuration(ar[0], at, duration, timeUnit);
                            taskList.add(fixedDuration);
                            Timer timer = new Timer();
                            class RemindTask extends TimerTask {
                                public void run() {
                                    System.out.println(ar[0] + " is completed");
                                    timer.cancel();
                                }
                            }
                            RemindTask rt = new RemindTask();
                            switch (timeUnit) {
                            case hours:
                                timer.schedule(rt, duration * HOURS);
                                break;
                            case minutes:
                                timer.schedule(rt, duration * MINUTES);
                                break;
                            case seconds:
                                timer.schedule(rt, duration * SECONDS);
                                break;
                            }
                            ui.showAdd();
                            break;
                        case no:
                            if(CheckAnomaly.checkTime(at, TaskList.currentList())) {
                                Event temp = new Event(ar[0], at);
                                taskList.add(temp);
                                ui.showAdd();
                            }
                            else{
                                throw new DukeException(ExceptionType.timeClash);
                                }
                        break;
                        default:
                            ui.showCommandError();
                            break;
                        }
                    }
                    catch (DukeException e) {
                        ui.showDateError();
                    }
                    break;

                case recur:
                    ui.promptRecurringActions();
                    while (!isExitRecur) {
                        String temp = parser.getCommand();
                        RecurTaskType recurType;
                        try {
                            recurType = RecurTaskType.valueOf(temp);
                        } catch (IllegalArgumentException e) {
                            recurType = RecurTaskType.others;
                        }
                        switch (recurType) {
                        case list:
                            recurHandler.listRecurring();
                            break;
                        case find:
                            recurHandler.findRecurring(parser.getKey());
                            break;
                        case exit:
                            isExitRecur = true;
                            ui.showExit();
                            break;
                        case add:
                            recurHandler.addBasedOnOperation();
                            break;
                        default:
                            ui.showCommandError();
                            break;
                        }
                    }
                    isExitRecur = false;
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
                    }
                    catch (IndexOutOfBoundsException e){
                        ui.showIndexError();
                    }
                    catch (IllegalArgumentException e){
                        ui.showTimeError();
                    }
                    break;

                    default:
                    ui.showCommandError();
                    break;
            }
        }
    }

    /**
     * Main function of Duke
     * Creates a new instance of Duke class
     * @param args command line arguments
     * @throws DukeException Custom exception class within Duke program
     */
    public static void main(String[] args) throws DukeException {
        new Duke().run();
    }
}
