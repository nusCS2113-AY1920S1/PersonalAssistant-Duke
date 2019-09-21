package compal.logic.parser;

import compal.logic.commands.*;
import compal.main.Duke;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;

public class ParserManager{
    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    static final String CMD_EXIT = "bye";
    static final String CMD_LIST = "list";
    static final String CMD_CLEAR = "clear";
    static final String CMD_DONE = "done";
    static final String CMD_DELETE = "delete";
    static final String CMD_TODO = "todo";
    static final String CMD_EVENT = "event";
    static final String CMD_DEADLINE = "deadline";
    static final String CMD_DO_AFTER_TASK = "doaftertask";
    static final String CMD_FIXED_DURATION_TASK = "fixeddurationtask";
    static final String CMD_RECUR_TASK = "recurtask";
    static final String CMD_VIEW = "view";
    static final String CMD_FIND = "find";


    /**
     * status tells the parser if ComPAL is expecting an answer from a prompt it gave. Parser will then
     * know where to redirect the input command.
     * Can be an enum e.g State.INIT, State.NORMAL, State.READTIMETABLE etc.
     */
    public String status = "normal";
    /**
     * stage tells the parser which stage of the current prompt sequence ComPAL is on.
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
     * Constructor for the parser. Called in Duke when initializing
     *
     * @param d Duke
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
     * This function handles the main CLI parsing. Just pass in the cmd string and it will work its magic.
     * It uses regex to understand the command entered.
     *
     * @param userInput No return value
     * @Function
     * @UsedIn: COMPal.Duke.handleUserInput()
     */
    public void processCMD(String userInput) throws ParseException, Duke.DukeException {
        char sadFace = '\u2639';
        Scanner sc = new Scanner(userInput);
        String cmd = sc.next();
        switch (cmd) {
        case CMD_EXIT:
            ByeCommand bye = new ByeCommand(duke);
            bye.Command(cmd);
            break;
        case CMD_LIST:
            ListCommand list = new ListCommand(duke);
            list.Command(cmd);
            break;
        case CMD_CLEAR:
            ClearCommand clear = new ClearCommand(duke);
            clear.Command(cmd);
            break;
        case CMD_DONE:
            DoneCommand done = new DoneCommand(duke);
            done.Command(userInput);
            break;
        case CMD_DELETE:
            DeleteCommand delete = new DeleteCommand(duke);
            delete.Command(userInput);
            break;
        case CMD_TODO:
            ToDoCommand todo = new ToDoCommand(duke);
            todo.Command(userInput);
            break;
        case CMD_EVENT:
            EventCommand event = new EventCommand(duke);
            event.Command(userInput);
            break;
        case CMD_DEADLINE:
            DeadlineCommand deadline = new DeadlineCommand(duke);
            deadline.Command(userInput);
            break;
        case CMD_DO_AFTER_TASK:
            DoAfterCommand doafter = new DoAfterCommand(duke);
            doafter.Command(userInput);
            break;
        case CMD_FIXED_DURATION_TASK:
            FixedDurationCommand fixedduration = new FixedDurationCommand(duke);
            fixedduration.Command(userInput);
            break;
        case CMD_RECUR_TASK:
            RecurTaskCommand recurTask = new RecurTaskCommand(duke);
            recurTask.Command(userInput);
            break;
        case CMD_FIND:
            FindCommand findCommand = new FindCommand(duke);
            findCommand.Command(userInput);
            break;
        case CMD_VIEW:
            ViewCommand viewCommand = new ViewCommand(duke);
            viewCommand.Command(userInput);
            break;
        default:
            throw new Duke.DukeException(sadFace + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }


    //----------------------->




    //***CONTROL PARSING LOGIC***---------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    public void setStatus(String status) {
        this.status = status;
        stage = 0; //reset stage everytime status is changed
    }


    //----------------------->


}
