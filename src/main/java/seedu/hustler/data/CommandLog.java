package seedu.hustler.data;

import java.io.IOException;
import java.util.ArrayList;
import seedu.hustler.Hustler;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.CommandParser;

public class CommandLog {

    private static ArrayList<String> commandlog;
    private static ArrayList<String> redoLog;
    private static boolean isRestoring = false;

    public CommandLog() {
        commandlog = new ArrayList<>();
        redoLog = new ArrayList<>();
    }

    public static void recordCommand(String command) {
        commandlog.add(command);
        clearRedoLog();
    }

    public static void restoreData(int numberOfCommandsToUndo) {
        isRestoring = true;
        int restoreDataUntil = commandlog.size() - numberOfCommandsToUndo;

        if (restoreDataUntil >= 0) {
            for (int i = 0; i < restoreDataUntil; i++) {
                try {
                    CommandParser parser = new CommandParser();
                    Command command = parser.parse(commandlog.get(i));
                    command.execute();
                    Hustler.saveStorage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            while (numberOfCommandsToUndo > 0) {
                System.out.println("\t_____________________________________");
                System.out.println("\tThese commands have been undone: ");
                while (numberOfCommandsToUndo > 0) {
                    System.out.println("       " + commandlog.get(restoreDataUntil));
                    redoLog.add(commandlog.get(restoreDataUntil));
                    commandlog.remove(restoreDataUntil);
                    numberOfCommandsToUndo--;
                }
                System.out.println("\t_____________________________________");
            }

            while (commandlog.size() > restoreDataUntil) {
                commandlog.remove(restoreDataUntil);
            }
        } else {
            System.out.println("\tError! You are attempting to undo more commands than is possible!");
        }
        isRestoring = false;
    }

    public static void redo() {
        isRestoring = true;
        if (redoLog.size() > 0) {
            for (int i = 0; i < redoLog.size(); i += 1) {
                commandlog.add(redoLog.get(i));
            }
            while (redoLog.size() == 0) {
                redoLog.remove(0);
            }
            Hustler.reloadBackup();
            restoreData(0);
            System.out.println("\tAll previously undone commands have been redone!");
        } else {
            System.out.println("\tRedo commands can only be used immediately after undo commands!");
        }
        isRestoring = false;
    }

    public static void clearRedoLog() {
        while (redoLog.size() > 0) {
            redoLog.remove(0);
        }
    }

    public static void deleteLatestLoggedCommand() {
        commandlog.remove(commandlog.size() - 1);
    }
    public static boolean isRestoring() {
        return isRestoring;
    }
}
