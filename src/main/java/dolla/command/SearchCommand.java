package dolla.command;

import dolla.DollaData;
import dolla.ui.SearchUi;
import dolla.ui.Ui;
import dolla.task.RecordList;

import java.util.ArrayList;

/**
 * SearchCommand is a command that runs when
 * the user wants to execute a search command.
 */
public class SearchCommand extends Command {
    private String mode;
    private String component;
    private String searchContent;

    /**
     * Instantiates a new SearchCommand.
     * @param mode mode Dolla is in
     * @param component the component to be searched on (description/name/date)
     * @param searchContent the content that the user wants to search for
     */
    public SearchCommand(String mode, String component, String searchContent) {
        this.mode = mode;
        this.searchContent = searchContent;
        this.component = component;
    }

    /**
     * Executes the search command according to the mode and the command.
     * @param dollaData the dolla data
     * @throws Exception handle exception
     */
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
        case "limit":
            recordList = dollaData.getRecordList(mode);
            break;
        default:
            break;
        }

        boolean listIsEmpty = (recordList.size() == 0);

        if (listIsEmpty) {
            Ui.printEmptyListError(mode);
            return;
        } else if (mode.equals("entry")) {
            if (component.equals("description")) {
                SearchUi.printSearchDesc(mode, recordList, searchContent);
            } else if (component.equals("date")) {
                SearchUi.printSearchDate(mode, recordList, searchContent);
            }
        } else if (mode.equals("debt")) {
            if (component.equals("description")) {
                SearchUi.printSearchDesc(mode, recordList, searchContent);
            } else if (component.equals("name")) {
                SearchUi.printSearchName(mode, recordList, searchContent);
            } else if (component.equals("date")) {
                SearchUi.printSearchDate(mode, recordList, searchContent);
            }
        } else if (mode.equals("limit") && component.equals("duration") && component != null ) {
            SearchUi.printSearchDuration(mode, recordList, searchContent);
        }
    }
}
