package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.LogList;

import java.util.ArrayList;

public class SearchCommand extends Command {
    private String mode;
    private String searchContent;

    public SearchCommand (String mode, String searchContent) {
        this.mode = mode;
        this.searchContent = searchContent;
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
            case "limit":
                logList = dollaData.getLogList(mode);
            default:
                break;
        }

        boolean ListisEmpty = (logList.size() == 0);

        if (ListisEmpty) {
            Ui.printEmptyListError(mode);
            return;
        } else if (mode.equals("entry")) {
            Ui.printSearch(mode, logList, searchContent);
        } else if (mode.equals("debt")) {
            Ui.printSearch(mode, logList, searchContent);
        } else if (mode.equals("limit")) {
            Ui.printSearch(mode, logList, searchContent);
        }
    }
}
