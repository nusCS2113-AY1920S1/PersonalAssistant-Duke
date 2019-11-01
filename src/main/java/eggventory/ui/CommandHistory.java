package eggventory.ui;

import java.util.ArrayList;

//@@author Raghav-B
public class CommandHistory {

    private static ArrayList<String> commandHistory = new ArrayList<>();
    private static int curHistoryIndex = 0;

    /**
     * Append the latest command String to the end of the ArrayList.
     * @param command String input by the user.
     */
    public static void addToHistory(String command) {
        commandHistory.add(command);
        curHistoryIndex = commandHistory.size() - 1;
    }

    /**
     * Traverses either backwards or forwards through the command history to return
     * previously entered commands.
     * @param direction -1: Move backwards through history.
     *                  1: Move forwards through history.
     * @return Previously entered command String.
     */
    public static String getCommand(int direction) {
        String returnString = "";

        // Return empty String if there is no command history yet.
        if (commandHistory.isEmpty()) {
            return returnString;
        }

        if (direction == -1) {
            // Searching backwards through the history
            returnString = commandHistory.get(curHistoryIndex);

            // Used to ensure index remains within array bounds
            if (curHistoryIndex > 0) {
                curHistoryIndex -= 1;
            }
        } else if (direction == 1 && curHistoryIndex < commandHistory.size() - 1) {
            // Searching forwards through the history
            curHistoryIndex += 1;
            returnString = commandHistory.get(curHistoryIndex);
        }

        return returnString;
    }
}
//@@author
