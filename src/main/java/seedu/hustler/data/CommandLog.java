package seedu.hustler.data;

import java.util.ArrayList;
import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.parser.CommandParser;
import seedu.hustler.logic.CommandLineException;

public class CommandLog {

    private static ArrayList<String> commandlog;
    private static boolean isRestoring = false;

    public CommandLog() {
        commandlog = new ArrayList<String>();
    }

    public static void recordCommand(String command) {
        commandlog.add(command);
    }

    public static void restoreData(int numberOfCommandsToUndo) {
        isRestoring = true;
        int restoreDataUntil = commandlog.size() - numberOfCommandsToUndo;
        
        for (int i = 0; i < restoreDataUntil; i++) {
            try {
                CommandParser parser = new CommandParser();

                Command command = parser.parse(commandlog.get(i));
                command.execute();
            
                Hustler.saveStorage();
            } catch (CommandLineException e) {
            }
	}

        while  (commandlog.size() > restoreDataUntil) {
            commandlog.remove(restoreDataUntil);
        }
        isRestoring = false;
    }

    public static boolean isRestoring() {
        return isRestoring;
    }
}
