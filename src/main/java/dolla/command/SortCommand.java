package dolla.command;

import dolla.DollaData;
import dolla.task.Record;
import dolla.ui.ListUi;
import dolla.ui.SortUi;
import dolla.ui.Ui;
import dolla.sort.SortAmount;
import dolla.task.RecordList;
import dolla.sort.SortDate;
import dolla.sort.SortDescription;
import dolla.sort.SortName;

import java.util.ArrayList;

public class SortCommand extends Command {
    private String mode;
    private String type;

    //@@author yetong1895
    public SortCommand(String mode, String type) {
        this.mode = mode;
        this.type = type;
    }

    @Override
    public void execute(DollaData dollaData) {
        RecordList recordList = new RecordList(new ArrayList<>());
        ArrayList<Record> list;
        switch (mode) {
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
            Ui.printInvalidCommandError();
            break;
        }

        try {
            list = recordList.getCloneList();
            list.get(0); //test if list is empty
            switch (mode) {
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
                if (type != null && type.equals("amount")) {
                    new SortAmount(list);
                } else {
                    SortUi.printInvalidSort(mode);
                }
                break;
            default:
                break;
            }
        } catch (Exception e) {
            ListUi.printEmptyListError(mode);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
