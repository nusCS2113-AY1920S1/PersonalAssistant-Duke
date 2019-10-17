package dolla.command;

import dolla.DollaData;
import dolla.Log;
import dolla.Ui;
import dolla.task.EntryList;
import dolla.task.DebtList;
import dolla.task.LogList;
import dolla.task.Task;
import dolla.task.TaskList;

import java.util.ArrayList;

/**
 * Display all the tasks stored in the relevant LogList depending on mode.
 */
public class ShowListCommand extends Command {

    private String mode;

    public ShowListCommand(String mode) {
        this.mode = mode;
    }

    /**
     * Prints out the logs from the specified LogList in dollaData.
     * @param dollaData Data to be manipulated.
     */
    @Override
    public void execute(DollaData dollaData) {
        LogList logList = new LogList(new ArrayList<>());

        switch (mode) {
        case "entry":
            logList = dollaData.getLogList(mode);
            break;
        case "debt":
            logList = dollaData.getLogList(mode);
            break;
        default:
            break; // TODO: What to do here?
        }

        boolean isListEmpty = (logList.size() == 0);

        if (isListEmpty) { // TODO: Place this in proper place
            Ui.printEmptyListError(mode);
            return;
        } else if (mode.equals("entry")) {
            Ui.printList(mode, logList);
            return;
        } else if (mode.equals("debt")) {
            Ui.printList(mode, logList);
            return;
        }
    }
}
