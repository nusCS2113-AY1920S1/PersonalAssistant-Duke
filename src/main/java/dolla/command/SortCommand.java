package dolla.command;

import dolla.DollaData;
import dolla.ui.Ui;
import dolla.task.LogList;
import dolla.sort.SortDate;
import dolla.sort.SortDescription;
import dolla.sort.SortName;

import java.util.ArrayList;

public class SortCommand extends Command {
    private String mode;
    private String type;

    public SortCommand (String mode, String type) {
        this.mode = mode;
        this.type = type;
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
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
        } else {
            if(mode.equals("entry")) {
                if(type.equals("date")) {
                    new SortDate(logList.get());
                } else if(type.equals("description")){
                    new SortDescription(logList.get());
                } else {
                    Ui.printInvalidCommandError();
                }
                return;
            } else if(mode.equals("debt")) {
                if(type.equals("description")) {
                    new SortDescription(logList.get());
                } else if(type.equals("name")) {
                    new SortName(logList.get());
                } else {
                    Ui.printInvalidCommandError();
                }
            }
        }

    }
}
