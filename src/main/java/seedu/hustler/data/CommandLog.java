package seedu.hustler.data;

import java.io.IOException;
import java.util.ArrayList;
import seedu.hustler.Hustler;
import seedu.hustler.MainWindow;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.CommandParser;

public class CommandLog {

    private static ArrayList<String> commandLog;
    private static ArrayList<String> redoLog;

    public CommandLog() {
        commandLog = new ArrayList<>();
        redoLog = new ArrayList<>();
    }

    public static void recordCommand(String command) {
        commandLog.add(command);
        redoLog.clear();
    }

    /**
     * Restores the data according to the number of commands to undo.
     */
    public static void restoreData(int numberOfCommandsToUndo) {
        int restoreDataUntil = commandLog.size() - numberOfCommandsToUndo;

        if (restoreDataUntil >= 0) {
            for (int i = 0; i < restoreDataUntil; i++) {
                try {
                    CommandParser parser = new CommandParser();
                    Command command = parser.parse(commandLog.get(i));
                    command.execute();
                    Hustler.saveStorage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            MainWindow.onPrinting();
            while (numberOfCommandsToUndo > 0) {
                System.out.println("\t_____________________________________");
                System.out.println("\tThese commands have been undone: ");
                while (numberOfCommandsToUndo > 0) {
                    System.out.println("\t" + commandLog.get(restoreDataUntil));
                    redoLog.add(commandLog.get(restoreDataUntil));
                    commandLog.remove(restoreDataUntil);
                    numberOfCommandsToUndo--;
                }
                System.out.println("\t_____________________________________");
            }

            while (commandLog.size() > restoreDataUntil) {
                commandLog.remove(restoreDataUntil);
            }
        } else {
            System.out.println("\tError! You are attempting to undo more commands than is possible!");
        }
    }

    /**
     * Redoes the previously undone commands.
     */
    public static void redo() {
        if (redoLog.size() > 0) {
            for (int i = 0; i < redoLog.size(); i++) {
                commandLog.add(redoLog.get(i));
            }
            redoLog.clear();
            Hustler.reloadBackup();
            restoreData(0);
            System.out.println("\tAll previously undone commands have been redone!");
        } else {
            System.out.println("\tRedo commands can only be used immediately after undo commands!");
        }
    }
}
