package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.LogList;

import java.util.ArrayList;

public class SearchCommand extends Command {
    private String mode;
    private String component;
    private String searchContent;

    public SearchCommand(String mode, String component, String searchContent) {
        this.mode = mode;
        this.searchContent = searchContent;
        this.component = component;
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
