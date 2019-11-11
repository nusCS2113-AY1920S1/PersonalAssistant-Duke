package duke.storage;

import java.util.ArrayList;

public class CommandHistoryList {
    private ArrayList<String> commandHistoryList;

    public CommandHistoryList() {
        commandHistoryList = new ArrayList<>();
    }

    /**
     * This function update the list with command history typed by the user.
     * @param cmd the command typed in by the use.
     */
    public void updateHistoryList(String cmd) {
        commandHistoryList.add(cmd);
    }

    /**
     * This function return the list of command history typed  bythe user.
     * @return the list with history of commands.
     */
    public ArrayList<String> getHistoryList() {
        return commandHistoryList;
    }
}
