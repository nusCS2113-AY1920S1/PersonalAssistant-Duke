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
     * Only reloads backup-ed data state if the number of commands to undo is valid.
     *
     * @param numberOfCommandsToUndo number of commands to undo.
     * @return boolean whether the user is trying to undo more commands than is possible.
     */
    public static boolean isUndoUnderflow (int numberOfCommandsToUndo) {
        int restoreDataUntil = commandLog.size() - numberOfCommandsToUndo;
        if (restoreDataUntil >= 0) {
            return false;
        } else {
            return true;
        }
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
            MainWindow.onPrinting();
            System.out.println("\tError! You are attempting to undo more commands than is possible!");
        }
    }

    /**
     * Redoes the previously undone commands.
     */
    public static void redo() {
        if (redoLog.size() > 0) {
            System.out.println("\t_____________________________________");
            System.out.println("\tThese commands have been redone: ");
            for (int i = 0; i < redoLog.size(); i++) {
                System.out.println("\t" + redoLog.get(i));
            }
            System.out.println("\t_____________________________________");
            MainWindow.offPrinting();
            for (int i = 0; i < redoLog.size(); i++) {
                commandLog.add(redoLog.get(i));
            }
            redoLog.clear();
            Hustler.reloadBackup();
            restoreData(0);
            MainWindow.onPrinting();
        } else {
            System.out.println("\tRedo commands can only be used immediately after undo commands!");
        }
    }

    /**
     * Removes the last logged command if it is invalid.
     */
    public static void removeLastCommand() {
        commandLog.remove(commandLog.size() - 1);
    }
}
