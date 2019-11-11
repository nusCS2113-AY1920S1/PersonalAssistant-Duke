package dolla.command;

import dolla.model.DollaData;
import dolla.model.Record;
import dolla.parser.ParserStringList;
import dolla.ui.ListUi;
import dolla.ui.SortUi;
import dolla.model.RecordList;
import dolla.command.sort.SortAmount;
import dolla.command.sort.SortDate;
import dolla.command.sort.SortDescription;
import dolla.command.sort.SortName;

import java.util.ArrayList;

public class SortCommand extends Command implements ParserStringList {

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
        recordList = dollaData.getRecordListObj(mode);

        try {
            list = recordList.getCloneList();
            list.get(0); //test if list is empty
            switch (mode) {
            case MODE_ENTRY:
                switch (type) {
                case SORT_TYPE_AMOUNT:
                    new SortAmount(list);
                    break;
                case SORT_TYPE_DATE:
                    new SortDate(list);
                    break;
                case SORT_TYPE_DESC:
                    new SortDescription(list);
                    break;
                default:
                    SortUi.printInvalidSort(mode);
                    break;
                }
                break;
            case MODE_DEBT:
                switch (type) {
                case SORT_TYPE_AMOUNT:
                    new SortAmount(list);
                    break;
                case SORT_TYPE_DATE:
                    new SortDate(list);
                    break;
                case SORT_TYPE_DESC:
                    new SortDescription(list);
                    break;
                case SORT_TYPE_NAME:
                    new SortName(list);
                    break;
                default:
                    SortUi.printInvalidSort(mode);
                    break;
                }
                break;
            case MODE_LIMIT:
                if (type.equals(SORT_TYPE_AMOUNT)) {
                    new SortAmount(list);
                } else {
                    SortUi.printInvalidSort(mode);
                }
                break;
            case MODE_SHORTCUT:
                if (type.equals(SORT_TYPE_AMOUNT)) {
                    new SortAmount(list);
                } else if (type.equals(SORT_TYPE_DESC)) {
                    new SortDescription(list);
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
        String command = "sort ";
        return command + type + " in " + mode;
    }
}
