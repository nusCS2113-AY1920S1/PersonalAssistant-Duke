package dolla.command;

import dolla.DollaData;
import dolla.ui.ListUi;

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

        RecordList recordList = new RecordList(new ArrayList<>());

        switch (mode) { //TODO: is this needed?
        case MODE_ENTRY:
            recordList = dollaData.getRecordListObj(mode);
            break;
        case MODE_DEBT:
            recordList = dollaData.getRecordListObj(mode);
            break;
        case MODE_LIMIT:
            recordList = dollaData.getRecordListObj(mode);
            break;
        default:
            break; // TODO: What to do here?
        }

        boolean listIsEmpty = (recordList.size() == 0);

        if (listIsEmpty) { // TODO: Place this in proper place
            ListUi.printEmptyListError(mode);
            return;
        //} else if (mode.equals("entries")) {
            //Ui.printList(mode, entryList);
        } else if (mode.equals(MODE_ENTRY)) {
            ListUi.printList(mode, recordList);
            return;
        } else if (mode.equals(MODE_DEBT)) {
            ListUi.printList(mode, recordList);
            //System.out.println(recordList.get().size());//test
            return;
        } else if (mode.equals(MODE_LIMIT)) {
            ListUi.printList(mode, recordList);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
