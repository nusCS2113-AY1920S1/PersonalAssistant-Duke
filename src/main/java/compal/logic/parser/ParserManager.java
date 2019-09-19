package compal.logic.parser;

import compal.logic.commands.*;
import compal.main.Duke;

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
    static final String CMD_RECUR_TASk = "recurtask";
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

    //----------------------->





    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructor for the parser. Called in Duke when initializing
     *
     * @param d Duke
     */
    public ParserManager(Duke d) {
        this.duke = d;
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
    public void processCommandFirstWord(String userInput) throws ParseException, Duke.DukeException {
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
            done.Command(cmd);
            break;
        case CMD_DELETE:
            DeleteCommand delete = new DeleteCommand(duke);
            delete.Command(cmd);
            break;
        case CMD_TODO:
            break;
        case CMD_EVENT:
            break;
        case CMD_DEADLINE:
            break;
        case CMD_DO_AFTER_TASK:
            break;
        case CMD_FIXED_DURATION_TASK:
            break;
        case CMD_RECUR_TASk:
            break;
        case CMD_FIND:
            break;
        //case CMD_NAME: f
            //break;
        case CMD_VIEW:
            break;
        default:
            throw new Duke.DukeException(sadFace + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public void processCommand(String userIn)
            /*
        } else if (cmd.matches("done ([0-9]+)")) {
            duke.tasklist.taskDone(cmd);
        } else if (cmd.matches("delete ([0-9]+)")) {
            duke.tasklist.deleteTask(cmd);
        } else if (cmd.matches("(todo|event|deadline|doaftertask|fixeddurationtask|recurtask) .+")) {
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("[T|t]ask .+")) { //draft task adding for ComPAL
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("find (.*)")) {
            duke.tasklist.findTask(cmd);
        } else if (status.equals("init")) {
            duke.ui.firstTimeInit(cmd, stage++);
        } else if (cmd.matches("(todo|event|deadline|fixeddurationtask|doaftertask|recurtask)")) {
            try {
                throw new Duke.DukeException(sadFace + " OOPS!!! The description of a " + cmd + " cannot be empty.");
            } catch (Duke.DukeException e) {
                duke.ui.printg(e.toString());
            }
        } else if (cmd.matches("view (.*)")) {
            //deadline return book /by 2/12/2019 1800
            if (cmd.matches("(view (3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$)")) {
                ArrayList<Task> viewDay = duke.tasklist.viewDate(cmd);
                if (viewDay.isEmpty()) {
                    return;
                }
                duke.ui.printTemp(viewDay);
            } else {
                duke.ui.printg("Wrong format of date for view command! Use view DD/MM/YYYY");
            }


        } else {
            try {
                throw new Duke.DukeException(sadFace + " OOPS!!! I'm sorry, but I don't know what that means :-(");
            } catch (Duke.DukeException e) {
                duke.ui.printg(e.toString());
            }
        }


    }*/



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
