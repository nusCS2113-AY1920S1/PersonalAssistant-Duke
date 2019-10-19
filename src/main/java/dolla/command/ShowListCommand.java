package dolla.command;

import dolla.DollaData;
import dolla.Ui;

import dolla.task.LogList;


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

////        LogList logList = new LogList(new ArrayList<Log>());
//        LogList entryList = new EntryList(new ArrayList<Entry>());
//
//        switch (mode) {
//        case "entries":
////            logList = dollaData.entryList;
////            entryList = dollaData.entryList;
//            entryList = dollaData.getLogList(mode);

        LogList logList = new LogList(new ArrayList<>());

        switch (mode) { //TODO: is this needed?
        case "entry":
            logList = dollaData.getLogList(mode);
            break;
        case "debt":
            logList = dollaData.getLogList(mode);
            break;
        case "limit":
            logList = dollaData.getLogList(mode);
            break;
        default:
            break; // TODO: What to do here?
        }

        
//        boolean isListEmpty = (logList.size() == 0);

        boolean isListEmpty = (logList.size() == 0);

        if (isListEmpty) { // TODO: Place this in proper place
            Ui.printEmptyListError(mode);
            return;
//        } else if (mode.equals("entries")) {
//            Ui.printList(mode, entryList);
        } else if (mode.equals("entry")) {
            Ui.printList(mode, logList);
            return;
        } else if (mode.equals("debt")) {
            Ui.printList(mode, logList);
//            System.out.println(logList.get().size());//test
            return;
        } else if (mode.equals("limit")) {
            Ui.printList(mode,logList);
        }
    }
}
