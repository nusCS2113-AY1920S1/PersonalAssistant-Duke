import CustomExceptions.RoomShareException;
import Enums.*;
import Model_Classes.*;
import Modes.HelpMode;
import Modes.RecurringMode;
import Modes.ReportMode;
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

            case help:
                ui.help();
                HelpMode helpMode = new HelpMode();
                helpMode.run();
                break;

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

             case todo:
                 try {
                     String description = parser.getDescription();
                     ui.promptForAssigning();
                     String response = parser.getResponse();
                     if (response.equals("no")) {
                         ToDo temp = new ToDo(description);
                         taskList.add(temp);
                         ui.showAdd();
                      }
                     else {
                         ToDo temp = new ToDo(description, response);
                         taskList.add(temp);
                         ui.showAdd();
                     }
                 } catch (RoomShareException e) {
                     ui.showEmptyDescriptionError();
                 }
                 break;

             case deadline:
                 try {
                     String[] deadlineArray = parser.getDescriptionWithDate();
                     Date by = parser.formatDate(deadlineArray[1]);
                     ui.promptForAssigning();
                     String response = parser.getResponse();
                     if (response.equals("no")) {
                         Deadline temp = new Deadline(deadlineArray[0], by);
                         taskList.add(temp);
                         ui.showAdd();
                     }
                     else {
                         Deadline temp = new Deadline(deadlineArray[0], by, response);
                         taskList.add(temp);
                         ui.showAdd();
                      }
                 } catch (RoomShareException e) {
                     ui.showDateError();
                 }
                 break;

            case event:
                try {
                    String[] eventArray = parser.getDescriptionWithDate();
                    Date at = parser.formatDate(eventArray[1]);
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
                        int duration = parser.getAmount();
                        String unit = timeUnit.toString();
                        ui.promptForAssigning();
                        String response = parser.getResponse();
                        if (response.equals("no")) {
                            FixedDuration fixedDuration = new FixedDuration(eventArray[0], at, duration, unit);
                            //checks for clashes
                            if (CheckAnomaly.checkTime(fixedDuration)) {
                                taskList.add(fixedDuration);
                            } else {
                                throw new RoomShareException(ExceptionType.timeClash);
                            }
                        } else {
                            FixedDuration fixedDuration = new FixedDuration(eventArray[0], at, duration, response, unit);
                            //checks for clashes
                            if (CheckAnomaly.checkTime(fixedDuration)) {
                                taskList.add(fixedDuration);
                            } else {
                                throw new RoomShareException(ExceptionType.timeClash);
                            }
                        }
                        Timer timer = new Timer();
                        class RemindTask extends TimerTask {
                            public void run() {
                                System.out.println(eventArray[0] + " is completed");
                                timer.cancel();
                            }
                        }
                        RemindTask rt = new RemindTask();
                        switch (timeUnit) {
                        case hours:
                            timer.schedule(rt, duration * 1000 * 60 * 60);
                            break;

                        case minutes:
                            timer.schedule(rt, duration * 1000 * 60);
                            break;
                        }
                        ui.showAdd();
                        break; // end yes

                    case no:
                        ui.promptForAssigning();
                        String res = parser.getResponse();
                        if (res.equals("no")) {
                            Event event = new Event(eventArray[0], at);
                            taskList.add(event);
                            ui.showAdd();
                        } else {
                            Event event = new Event(eventArray[0], at, res);
                            taskList.add(event);
                            ui.showAdd();
                        }
                        break;
                     default:
                        ui.showCommandError();
                        break;
               }
            }
            catch (RoomShareException e) {
                ui.showDateError();
            }
            break;

            case recur:
                RecurringMode recurringMode = new RecurringMode(taskList);
                recurringMode.run();
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

            case report:
                ReportMode report = new ReportMode(taskList);
                report.run();
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
