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
    private static final String TYPE_AMOUNT = "amount";
    private static final String TYPE_DATE = "date";
    private static final String TYPE_DESC = "description";
    private static final String TYPE_NAME = "name";
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
            Ui.printInvalidCommandError();
            break;
        }

        try {
            list = recordList.getCloneList();
            list.get(0); //test if list is empty
            switch (mode) {
            case MODE_ENTRY:
                switch (type) {
                case TYPE_AMOUNT:
                    new SortAmount(list);
                    break;
                case TYPE_DATE:
                    new SortDate(list);
                    break;
                case TYPE_DESC:
                    new SortDescription(list);
                    break;
                default:
                    SortUi.printInvalidSort(mode);
                    break;
                }
                break;
            case MODE_DEBT:
                switch (type) {
                case TYPE_AMOUNT:
                    new SortAmount(list);
                    break;
                case TYPE_DATE:
                    new SortDate(list);
                    break;
                case TYPE_DESC:
                    new SortDescription(list);
                    break;
                case TYPE_NAME:
                    new SortName(list);
                    break;
                default:
                    SortUi.printInvalidSort(mode);
                    break;
                }
                break;
            case MODE_LIMIT:
                if (type.equals(TYPE_AMOUNT)) {
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
