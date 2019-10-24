package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.LogList;

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
     * Executes the search command according to the mode and the command
     * @param dollaData the dolla data
     * @throws Exception handle exception
     */
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
        case "limit":
            logList = dollaData.getLogList(mode);
            break;
        default:
            break;
        }

        boolean listIsEmpty = (logList.size() == 0);

        if (listIsEmpty) {
            Ui.printEmptyListError(mode);
            return;
        } else if (mode.equals("entry")) {
            if (component.equals("description")) {
                Ui.printSearchDesc(mode, logList, searchContent);
            } else if (component.equals("date")) {
                Ui.printSearchDate(mode, logList, searchContent);
            }
        } else if (mode.equals("debt")) {
            if (component.equals("description")) {
                Ui.printSearchDesc(mode, logList, searchContent);
            } else if (component.equals("name")) {
                Ui.printSearchName(mode, logList, searchContent);
            } else if (component.equals("date")) {
                Ui.printSearchDate(mode, logList, searchContent);
            }
        } else if (mode.equals("limit")) {
            if (component.equals("description")) {
                Ui.printSearchDesc(mode, logList, searchContent);
            } else if (component.equals("date")) {
                Ui.printSearchDate(mode, logList, searchContent);
            }
        }
    }
}
