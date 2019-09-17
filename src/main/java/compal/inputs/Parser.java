package compal.inputs;

import compal.main.Duke;
import compal.tasks.Task;

import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
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

    /**
     * Constructor for the parser. Called in Duke when initializing
     *
     * @param d Duke
     */
    public Parser(Duke d) {
        this.duke = d;
    }

    /**
     * This function handles the main CLI parsing. Just pass in the cmd string and it will work its magic.
     * It uses regex to understand the command entered.
     *
     * @param cmd No return value
     * @Function
     * @UsedIn: COMPal.Duke.handleUserInput()
     */
    public void processCommands(String cmd) throws ParseException {
        char sadFace = '\u2639';
        if (cmd.equals("bye")) {
            duke.exitDuke();
        } else if (cmd.equals("list")) {
            duke.ui.listTasks();
        } else if (cmd.matches("done ([0-9]+)")) {
            duke.tasklist.taskDone(cmd);
        } else if (cmd.matches("delete ([0-9]+)")) {
            duke.tasklist.deleteTask(cmd);
        } else if (cmd.matches("(todo|event|deadline|doaftertask) .+")) {
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("[T|t]ask .+")) { //draft task adding for ComPAL
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("find (.*)")) {
            duke.tasklist.findTask(cmd);
        } else if (status.equals("init")) {
            duke.ui.firstTimeInit(cmd, stage++);
        } else if (cmd.matches("(todo|event|deadline)")) {
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


    }


    public void setStatus(String status) {
        this.status = status;
        stage = 0; //reset stage everytime status is changed
    }


}
