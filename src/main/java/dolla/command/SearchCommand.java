package dolla.command;

import dolla.model.DollaData;
import dolla.model.RecordList;
import dolla.ui.ListUi;
import dolla.ui.SearchUi;

import java.util.ArrayList;

//@@author tatayu
/**
 * SearchCommand is a command that runs when
 * the user wants to execute a search command.
 */
public class SearchCommand extends Command {
    private String mode;
    private String component;
    private String searchContent;

    protected static final String COMPONENT_DESCRIPTION = "description";
    protected static final String COMPONENT_NAME = "name";
    protected static final String COMPONENT_DATE = "date";
    protected static final String COMPONENT_DURATION = "duration";

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
            recordList = dollaData.getRecordListObj(mode);
            break;
        case "debt":
            recordList = dollaData.getRecordListObj(mode);
            break;
        case "limit":
            recordList = dollaData.getRecordListObj(mode);
            break;
        default:
            break;
        }


        boolean listIsEmpty = (recordList.size() == 0);

        try {
            if (listIsEmpty) {
                ListUi.printEmptyListError(mode);
                return;
            } else if (mode.equals(MODE_ENTRY)) {
                if (component.equals(COMPONENT_DESCRIPTION)) {
                    SearchUi.printSearchDesc(mode, recordList, searchContent);
                } else if (component.equals(COMPONENT_DATE)) {
                    SearchUi.printSearchDate(mode, recordList, searchContent);
                }
            } else if (mode.equals(MODE_DEBT)) {
                if (component.equals(COMPONENT_DESCRIPTION)) {
                    SearchUi.printSearchDesc(mode, recordList, searchContent);
                } else if (component.equals(COMPONENT_NAME)) {
                    SearchUi.printSearchName(mode, recordList, searchContent);
                } else if (component.equals(COMPONENT_DATE)) {
                    SearchUi.printSearchDate(mode, recordList, searchContent);
                }
            } else if (mode.equals(MODE_LIMIT) && component.equals(COMPONENT_DURATION)) {
                SearchUi.printSearchDuration(mode, recordList, searchContent);
            }
        } catch (Exception e) {
            SearchUi.printInvalidSearchFormat();
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
