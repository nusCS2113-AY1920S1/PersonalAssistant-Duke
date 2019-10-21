package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.action.undo;
import dolla.task.Log;
import dolla.task.LogList;
import dolla.task.TaskList;

import java.util.ArrayList;

/**
 * RemoveCommand is a Command used to remove a Task from the TaskList.
 */
public class RemoveCommand extends Command {
    protected String logNumStr;
    protected String mode;

    public RemoveCommand(String mode, String logNumStr) {
        this.mode = mode;
        this.logNumStr = logNumStr;
    }

    /**
     * Removes a task from the specified TaskList.
     * <p>
     *     The method first converts taskNumStr into an int. It will then check if a task
     *     corresponding to that number exists in the specified TaskList and subsequently
     *     remove that task if so.
     * </p>
     * <p>
     *     If taskNumStr is not an int, the method will return without doing anything.
     * </p>
     * <p>
     *     If taskNumInt does not correspond to any task in the specified TaskList, an
     *     alert is printed to the user, and the method will return.
     * </p>
     * @param dollaData
     */
    //@Override
    public void execute(DollaData dollaData) {

        int logNumInt = stringToInt(logNumStr) - 1;

        LogList logList = new LogList(new ArrayList<>());
        logList = dollaData.getLogList(mode);

        boolean isListEmpty = (logList.size() == 0);

        if (logNumInt < 0 || isListEmpty) {
            return; // TODO: return error command
        }

        undo.addCommand(mode,logList.get().get(logNumInt).getUserInput(),logNumInt);

        Ui.echoRemove(logList.get().get(logNumInt).getLogText());
        dollaData.removeFromLogList(mode,logNumInt);
/*
        ArrayList<String> msg = new ArrayList<String>();
        try {
            tasks.getFromList(taskNumInt - 1); // Check if the task exists first
            msg.add("Noted. I've removed this task: ");
            msg.add("  " + tasks.getFromList(taskNumInt - 1).getTask());
            tasks.removeFromList(taskNumInt - 1);
            msg.add("Now you have " + tasks.size() + " tasks in the list.");
        } catch (IndexOutOfBoundsException e) {
            //Ui.printNoTaskAssocError(taskNumInt);
            return;
        }
        Ui.printMsg(msg);
 */
    }
}
