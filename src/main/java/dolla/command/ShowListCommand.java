package dolla.command;

import dolla.DollaData;
import dolla.ui.Ui;

import dolla.task.RecordList;


import java.util.ArrayList;

/**
 * Display all the tasks stored in the relevant RecordList depending on mode.
 */
public class ShowListCommand extends Command {

    private String mode;

    public ShowListCommand(String mode) {
        this.mode = mode;
    }

    /**
     * Prints out the logs from the specified RecordList in dollaData.
     * @param dollaData Data to be manipulated.
     */
    @Override
    public void execute(DollaData dollaData) {

        /*
        RecordList recordList = new RecordList(new ArrayList<Record>());
        RecordList entryList = new EntryList(new ArrayList<Entry>());

        switch (mode) {
        case "entries":
            recordList = dollaData.entryList;
            entryList = dollaData.entryList;
            entryList = dollaData.getLogList(mode);
         */

        RecordList recordList = new RecordList(new ArrayList<>());

        switch (mode) { //TODO: is this needed?
        case "entry":
            recordList = dollaData.getRecordList(mode);
            break;
        case "debt":
            recordList = dollaData.getRecordList(mode);
            break;
        case "limit":
            recordList = dollaData.getRecordList(mode);
            break;
        default:
            break; // TODO: What to do here?
        }

        boolean listIsEmpty = (recordList.size() == 0);

        if (listIsEmpty) { // TODO: Place this in proper place
            Ui.printEmptyListError(mode);
            return;
        //} else if (mode.equals("entries")) {
            //Ui.printList(mode, entryList);
        } else if (mode.equals("entry")) {
            Ui.printList(mode, recordList);
            return;
        } else if (mode.equals("debt")) {
            Ui.printList(mode, recordList);
            //System.out.println(recordList.get().size());//test
            return;
        } else if (mode.equals("limit")) {
            Ui.printList(mode, recordList);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    };
}
