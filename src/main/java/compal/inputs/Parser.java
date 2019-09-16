package compal.inputs;

import compal.main.Duke;


/**
 * @DEVNOTE: Parser is the key processing class of the entire application. It is the brain of the program; it helps
 * to make sense of what the user is typing and coordinates GUI input and output and also
 * receives and disseminates information to the other classes where and when needed
 */
public class Parser {
    Duke duke;

    /**
     * status tells the parser if ComPAL is expecting an answer from a prompt it gave. Parser will then
     * know where to redirect the input command.
     * Can be an enum e.g State.INIT, State.NORMAL, State.TIMETABLE
     */
    public String status = "normal";

    /**
     * stage tells the parser which stage of the current prompt sequence ComPAL is on.
     * e.g if stage == 1 and status == "init", then ComPAL is currently expecting the user to
     * confirm his/her name (YES or NO)
     * Note: stage is always reset to 0 upon a status change. This is done in the function below called setStatus()
     */
    public int stage = 0;

    /**
     * Constructor for the parser. Called in Duke when initializing
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
    public void processCommands(String cmd) {

        if (cmd.equals("bye")) {
            duke.exitDuke();
        } else if (cmd.equals("list")) {
            duke.ui.listTasks();
        } else if (cmd.matches("done ([0-9]+)")) {
            duke.tasklist.taskDone(cmd);
        } else if (cmd.matches("delete ([0-9]+)")) {
            duke.tasklist.deleteTask(cmd);
        } else if (cmd.matches("(todo|event|deadline) .+")) {
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("[T|t]ask .+")) { //draft task adding for ComPAL
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("find (.*)")) {
            duke.tasklist.findTask(cmd);
        } else if (status.equals("init")) {

            //parser only increments the stage number each time, as it only moves forward
            //to move backwards in terms of logic, directly set the stage number of Parser from wherever
            //the processing is taking place
            duke.ui.firstTimeInit(cmd, stage++);
        } else if (cmd.matches("(todo|event|deadline)")) {
            try {
                throw new Duke.DukeException("☹ OOPS!!! The description of a " + cmd + " cannot be empty.");
            } catch (Duke.DukeException e) {
                duke.ui.printg(e.toString());
            }
        } else {
            try {
                throw new Duke.DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            } catch (Duke.DukeException e) {
                duke.ui.printg(e.toString());
            }
        }


    }

    /**
     * Sets the parser's parsing logic flow to that of 'status'
     * Changing status will help parser know where to send the received user input to and
     * what ComPAL is doing now
     * @param status parser status
     */
    public void setStatus(String status) {
        this.status = status;
        stage = 0; //reset stage everytime status is changed
    }


}
