package logic.command;


import model.Model;
import common.DukeException;

//@@author JustinChia1997
/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String NAME_NOT_IN_MEMlIST_MESSAGE = " is not in the member list.";
    public static final String INDEX_NOT_IN_TASK_MESSAGE = " index is not within the task list.";

    /**
     * Executes the command and returns the output message. Every command MUST be passed a model
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandOutput execute(Model model) throws DukeException;


    //@@author yuyanglin28
    /**
     * This method is to check input task index
     *
     * @param index the input index ( index in list -1)
     * @param model Model interface
     * @return if valid return true, else return false
     */
    public boolean checkTaskIndex(int index, Model model) {
        if (index < 1 || index > model.getTaskListSize()) {
            return false;
        } else {
            return true;
        }
    }

    //@@author yuyanglin28
    /**
     * This method is to check input member name
     *
     * @param name the input member name
     * @param model Model interface
     * @return if the member name exists return true, else return false
     */
    public boolean checkMemberName(String name, Model model) {
        int size = model.getMemberListSize();
        for (int i = 0; i < size; i++) {
            if (name.equals(model.getMemberNameById(i))) {
                return true;
            }
        }
        return false;
    }


}