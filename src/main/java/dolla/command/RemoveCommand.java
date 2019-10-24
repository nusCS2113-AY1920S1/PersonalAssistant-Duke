package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.action.Redo;
import dolla.action.Undo;
import dolla.task.LogList;

/**
 * RemoveCommand is a Command used to remove a Task from the TaskList.
 */
public class RemoveCommand extends Command {
    private String logNumStr;
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
     * @param dollaData dollaData
     */
    //@Override
    public void execute(DollaData dollaData) {
        int logNumInt;
        LogList logList = dollaData.getLogList(mode);
        boolean isListEmpty = (logList.size() == 0);

        if (isListEmpty) {
            return; // TODO: return error command
        }

        if (logNumStr.contains("/")) { //input from undo
            String[] parser = logNumStr.split("/", 2);
            logNumInt = stringToInt(parser[0]) - 1;
            Redo.addCommand(mode, logList.get().get(logNumInt).getUserInput()); //add undo input to redo
        } else if (logNumStr.contains("|")) { //input form redo
            String[] parser = logNumStr.split("//|", 2);
            logNumInt = stringToInt(parser[0]) - 1;
            Undo.addCommand(mode, logList.get().get(logNumInt).getUserInput(), logNumInt); //add the user input to undo
        } else { //normal user input
            logNumInt  = stringToInt(logNumStr) - 1;
            Undo.addCommand(mode, logList.get().get(logNumInt).getUserInput(), logNumInt);
            Redo.clearRedo(mode);
        }

        Ui.echoRemove(logList.get().get(logNumInt).getLogText());
        dollaData.removeFromLogList(mode,logNumInt);
    }
}
