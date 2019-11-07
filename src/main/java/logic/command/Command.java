package logic.command;


import model.Model;
import common.DukeException;

//@@author JustinChia1997
/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String INDEX_NOT_IN_MEMlIST_MESSAGE = "Index is not within the member list.";
    public static final String INDEX_NOT_IN_TASK_MESSAGE = "Index is not within the task list.";

    /**
     * Executes the command and returns the output message. Every command MUST be passed a model
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandOutput execute(Model model) throws DukeException;

    //@@author yuyanglin28
    /**
     * javadoc
     */
    public boolean checkMemberIndex(int index, Model model) {
        if (index < 1 || index > model.getMemberListSize()) {
            return false;
        } else {
            return true;
        }
    }

    //@@author yuyanglin28
    /**
     * java doc
     */
    public boolean checkTaskIndex(int index, Model model) {
        if (index < 1 || index > model.getTaskListSize()) {
            return false;
        } else {
            return true;
        }
    }


}