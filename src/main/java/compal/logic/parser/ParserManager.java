package compal.logic.parser;

import compal.commons.Compal;
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
import compal.logic.commands.SetReminderCommand;
import compal.logic.commands.ViewCommand;
import compal.logic.commands.ViewReminderCommand;
import compal.model.tasks.TaskList;

import static compal.commons.Messages.MESSAGE_INVALID_COMMAND;
import static compal.commons.Messages.MESSAGE_MISSING_INPUT;

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
    static final String CMD_SET_REMINDER = "set-reminder";
    static final String CMD_VIEW_REMIND = "view-reminder";
    static final String CMD_LECT = "lect";
    static final String CMD_TUT = "tut";
    static final String CMD_SECT = "sect";
    static final String CMD_LAB = "lab";

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
    Compal compal;
    TaskList tasklist;
    //----------------------->

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructs ParserManager object.
     *
     * @param d        Compal.
     * @param tasklist list of tasks.
     */
    public ParserManager(Compal d, TaskList tasklist) {
        this.compal = d;
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
     * @throws ParseException       If input date is invalid.
     * @throws Compal.DukeException If command input is unknown or user input is empty.
     */
    public void processCmd(String userInput) throws ParseException, Compal.DukeException {
        Scanner sc = new Scanner(userInput);
        if (sc.hasNext()) {
            String cmd = sc.next();
            if (status.equals("init")) {
                compal.ui.firstTimeInit(cmd, stage++);
            } else {
                switch (cmd) {
                case CMD_EXIT:
                    ByeCommand bye = new ByeCommand(compal);
                    bye.parseCommand(cmd);
                    break;
                case CMD_LIST:
                    ListCommand list = new ListCommand(compal);
                    list.parseCommand(cmd);
                    break;
                case CMD_CLEAR:
                    ClearCommand clear = new ClearCommand(compal);
                    clear.parseCommand(cmd);
                    break;
                case CMD_DONE:
                    DoneCommand done = new DoneCommand(compal);
                    done.parseCommand(userInput);
                    break;
                case CMD_DELETE:
                    DeleteCommand delete = new DeleteCommand(compal);
                    delete.parseCommand(userInput);
                    break;
                case CMD_EVENT:
                    EventCommand event = new EventCommand(compal);
                    event.parseCommand(userInput);
                    break;
                case CMD_DEADLINE:
                    DeadlineCommand deadline = new DeadlineCommand(compal);
                    deadline.parseCommand(userInput);
                    break;
                case CMD_DO_AFTER_TASK:
                    DoAfterCommand doafter = new DoAfterCommand(compal);
                    doafter.parseCommand(userInput);
                    break;
                case CMD_FIXED_DURATION_TASK:
                    FixedDurationCommand fixedduration = new FixedDurationCommand(compal);
                    fixedduration.parseCommand(userInput);
                    break;
                case CMD_RECUR_TASK:
                case CMD_LECT:
                case CMD_TUT:
                case CMD_SECT:
                case CMD_LAB:
                    RecurTaskCommand recurTask = new RecurTaskCommand(compal);
                    recurTask.parseCommand(userInput);
                    break;
                case CMD_FIND:
                    FindCommand findCommand = new FindCommand(compal);
                    findCommand.parseCommand(userInput);
                    break;
                case CMD_VIEW:
                    ViewCommand viewCommand = new ViewCommand(compal);
                    viewCommand.parseCommand(userInput);
                    break;
                case CMD_VIEW_REMIND:
                    ViewReminderCommand viewReminderCommand = new ViewReminderCommand(compal);
                    viewReminderCommand.parseCommand(userInput);
                    break;
                case CMD_SET_REMINDER:
                    SetReminderCommand setReminderCommand = new SetReminderCommand(compal);
                    setReminderCommand.parseCommand(userInput);
                    break;
                default:
                    compal.ui.printg(MESSAGE_INVALID_COMMAND);
                    throw new Compal.DukeException(MESSAGE_INVALID_COMMAND);
                }
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_INPUT);
            throw new Compal.DukeException(MESSAGE_MISSING_INPUT);
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
