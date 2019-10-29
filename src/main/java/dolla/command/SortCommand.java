package dolla.command;

import dolla.DollaData;
import dolla.ui.Ui;
import dolla.task.RecordList;
import dolla.sort.SortDate;
import dolla.sort.SortDescription;
import dolla.sort.SortName;

import java.util.ArrayList;

public class SortCommand extends Command {
    private String mode;
    private String type;

    public SortCommand(String mode, String type) {
        this.mode = mode;
        this.type = type;
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        RecordList recordList = new RecordList(new ArrayList<>());
        switch (mode) {
        case "entry":
            recordList = dollaData.getRecordList(mode);
            break;
        case "debt":
            recordList = dollaData.getRecordList(mode);
            break;
        default:
            break; // TODO: What to do here?
        }

        boolean isListEmpty = (recordList.size() == 0);

        if (isListEmpty) { // TODO: Place this in proper place
            Ui.printEmptyListError(mode);
            return;
        } else {
            if (mode.equals("entry")) {
                if (type.equals("date")) {
                    new SortDate(recordList.get());
                } else if (type.equals("description")) {
                    new SortDescription(recordList.get());
                } else {
                    Ui.printInvalidCommandError();
                }
                return;
            } else if (mode.equals("debt")) {
                if (type.equals("description")) {
                    new SortDescription(recordList.get());
                } else if (type.equals("name")) {
                    new SortName(recordList.get());
                } else {
                    Ui.printInvalidCommandError();
                }
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
