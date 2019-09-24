package compal.logic.parser;

import compal.logic.commands.ByeCommand;
import compal.logic.commands.ClearCommand;
import compal.logic.commands.DeadlineCommand;
import compal.logic.commands.DeleteCommand;
import compal.logic.commands.DoAfterCommand;
import compal.logic.commands.DoneCommand;
import compal.logic.commands.EventCommand;
import compal.logic.commands.FindCommand;
import compal.logic.commands.FixedDurationCommand;
import compal.logic.commands.ListCommand;
import compal.logic.commands.RecurTaskCommand;
import compal.logic.commands.ReminderCommand;
import compal.logic.commands.ViewCommand;
import compal.main.Duke;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;

/**
 * Deals with user inputs.
 */
public class ParserManager {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    static final String CMD_EXIT = "bye";
    static final String CMD_LIST = "list";
    static final String CMD_CLEAR = "clear";
    static final String CMD_DONE = "done";
    static final String CMD_DELETE = "delete";
    static final String CMD_EVENT = "event";
    static final String CMD_DEADLINE = "deadline";
    static final String CMD_DO_AFTER_TASK = "doaftertask";
    static final String CMD_FIXED_DURATION_TASK = "fixeddurationtask";
    static final String CMD_RECUR_TASK = "recurtask";
    static final String CMD_VIEW = "view";
    static final String CMD_FIND = "find";
    static final String CMD_REMINDER = "reminder";

    /*
     * Status tells the parser if ComPAL is expecting an answer from a prompt it gave. Parser will then
     * know where to redirect the input command.
     * Can be an enum e.g State.INIT, State.NORMAL, State.READTIMETABLE etc.
     */
    public String status = "normal";

    /*
     * Stage tells the parser which stage of the current prompt sequence ComPAL is on.
     * e.g if stage == 1 and status == "init", then ComPAL is currently expecting the user to
     * confirm his/her name (YES or NO)
     * Note: stage is always reset to 0 upon a status change. This is done in the function below called setStatus()
     */
    public int stage = 0;
    Duke duke;
    TaskList tasklist;
    //----------------------->

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    /**
     * Constructs ParserManager object.
     *
     * @param d Duke.
     * @param tasklist list of tasks.
     */
    public ParserManager(Duke d, TaskList tasklist) {
        this.duke = d;
        this.tasklist = tasklist;
    }
    //----------------------->


    //***COMMAND PROCESSING***------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    /**
     * Processes command input by user.
     * Based on the command input by user, it instantiates different command classes
     * and executes the respective methods implemented.
     *
     * @param userInput Entire user string input.
     * @throws ParseException If input date is invalid.
     * @throws Duke.DukeException If command input is unknown or user input is empty.
     */
    public void processCmd(String userInput) throws ParseException, Duke.DukeException {
        Scanner sc = new Scanner(userInput);
        if (sc.hasNext()) {
            String cmd = sc.next();
            if (status.equals("init")) {
                duke.ui.firstTimeInit(cmd, stage++);
            } else {
                switch (cmd) {
                case CMD_EXIT:
                    ByeCommand bye = new ByeCommand(duke);
                    bye.parseCommand(cmd);
                    break;
                case CMD_LIST:
                    ListCommand list = new ListCommand(duke);
                    list.parseCommand(cmd);
                    break;
                case CMD_CLEAR:
                    ClearCommand clear = new ClearCommand(duke);
                    clear.parseCommand(cmd);
                    break;
                case CMD_DONE:
                    DoneCommand done = new DoneCommand(duke);
                    done.parseCommand(userInput);
                    break;
                case CMD_DELETE:
                    DeleteCommand delete = new DeleteCommand(duke);
                    delete.parseCommand(userInput);
                    break;
                case CMD_EVENT:
                    EventCommand event = new EventCommand(duke);
                    event.parseCommand(userInput);
                    break;
                case CMD_DEADLINE:
                    DeadlineCommand deadline = new DeadlineCommand(duke);
                    deadline.parseCommand(userInput);
                    break;
                case CMD_DO_AFTER_TASK:
                    DoAfterCommand doafter = new DoAfterCommand(duke);
                    doafter.parseCommand(userInput);
                    break;
                case CMD_FIXED_DURATION_TASK:
                    FixedDurationCommand fixedduration = new FixedDurationCommand(duke);
                    fixedduration.parseCommand(userInput);
                    break;
                case CMD_RECUR_TASK:
                    RecurTaskCommand recurTask = new RecurTaskCommand(duke);
                    recurTask.parseCommand(userInput);
                    break;
                case CMD_FIND:
                    FindCommand findCommand = new FindCommand(duke);
                    findCommand.parseCommand(userInput);
                    break;
                case CMD_VIEW:
                    ViewCommand viewCommand = new ViewCommand(duke);
                    viewCommand.parseCommand(userInput);
                    break;
                case CMD_REMINDER:
                    ReminderCommand reminderCommand = new ReminderCommand(duke);
                    reminderCommand.parseCommand(cmd);
                    break;
                default:
                    duke.ui.printg("CommandError: Unknown command input detected!");
                    throw new Duke.DukeException("CommandError: Unknown command input detected!");
                }
            }
        } else {
            duke.ui.printg("EmptyInput: Empty input detected!");
            throw new Duke.DukeException("CommandError: Empty input detected!");
        }
    }
    //----------------------->

    //***CONTROL PARSING LOGIC***---------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    /**
     * Resets stage by setting stage to be 0.
     *
     * @param status Input status.
     */
    public void setStatus(String status) {
        this.status = status;
        stage = 0; //reset stage everytime status is changed
    }
    //----------------------->
}
