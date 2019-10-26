package dolla.command;

import dolla.DollaData;
import dolla.ui.SortUi;
import dolla.ui.Ui;
import dolla.sort.SortAmount;
import dolla.task.Log;
import dolla.task.LogList;
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
        LogList logList = new LogList(new ArrayList<>());
        ArrayList<Log> list;
        switch (mode) {
        case "entry":
            logList = dollaData.getLogList(mode);
            break;
        case "debt":
            logList = dollaData.getLogList(mode);
            break;
        case "limit":
            logList = dollaData.getLogList(mode);
        default:
            Ui.printInvalidCommandError();
            break;
        }

        try {
            list = logList.getCloneList();
            list.get(0); //test if list is empty
            switch(mode) {
            case "entry":
                switch (type) {
                case "amount":
                    new SortAmount(list);
                    break;
                case "date":
                    new SortDate(list);
                    break;
                case "description":
                    new SortDescription(list);
                    break;
                default:
                    SortUi.printInvalidSort(mode);
                    break;
                }
                break;
                case "debt":
                switch (type) {
                case "amount":
                    new SortAmount(list);
                    break;
                case"date":
                    new SortDate(list);
                    break;
                case "description":
                    new SortDescription(list);
                    break;
                case "name":
                    new SortName(list);
                    break;
                default:
                    SortUi.printInvalidSort(mode);
                    break;
                }
                break;
            case "limit":
                if (type.equals("amount")) {
                    new SortAmount(list);
                } else {
                    SortUi.printInvalidSort(mode);
                }
                break;
            }
        } catch(Exception e) {
            Ui.printEmptyListError(mode);
        }
    }
}
