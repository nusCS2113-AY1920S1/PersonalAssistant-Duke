package dolla.command;

import dolla.DollaData;
import dolla.Log;
import dolla.Ui;
import dolla.task.*;

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
     * @param dollaData
     */
    @Override
    public void execute(DollaData dollaData) {
//        LogList logList = new LogList(new ArrayList<Log>());
        EntryList entryList = new EntryList(new ArrayList<Entry>());

        switch (mode) {
        case "entries":
//            logList = dollaData.entryList;
            entryList = dollaData.entryList;
            break;
        default:
            break; // TODO: What to do here?
        }
        
//        boolean isListEmpty = (logList.size() == 0);
        boolean isListEmpty = (entryList.size() == 0);

        if (isListEmpty) { // TODO: Place this in proper place
            Ui.printEmptyListError(mode);
            return;
        } else if (mode.equals("entries")) {
            Ui.printList(mode, dollaData.getEntryList());
            return;
        }

    }
}
