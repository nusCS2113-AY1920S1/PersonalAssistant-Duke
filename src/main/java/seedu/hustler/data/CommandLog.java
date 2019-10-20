package seedu.hustler.data;

import java.io.IOException;
import java.util.ArrayList;
import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.parser.CommandParser;
import seedu.hustler.logic.CommandLineException;

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
                } catch (CommandLineException | IOException e) {

                }
            }
            while (numberOfCommandsToUndo > 0) {
                System.out.println("       _____________________________________");
                System.out.println("       These commands have been undone: ");
                while (numberOfCommandsToUndo > 0) {
                    System.out.println("       " + commandlog.get(restoreDataUntil));
                    redoLog.add(commandlog.get(restoreDataUntil));
                    commandlog.remove(restoreDataUntil);
                    numberOfCommandsToUndo--;
                }
                System.out.println("       _____________________________________");
            }
            while (commandlog.size() > restoreDataUntil) {
                commandlog.remove(restoreDataUntil);
            }
        } else {
            System.out.println("Error! You are attempting to undo more commands than is possible!");
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
            restoreData(0);
            System.out.println("All previously undone commands have been redone!");
        } else {
            System.out.println("Redo commands can only be used immediately after undo commands!");
        }
        isRestoring = false;
    }

    public static void clearRedoLog() {
        while (redoLog.size() > 0) {
            redoLog.remove(0);
        }
    }

    public static boolean isRestoring() {
        return isRestoring;
    }
}
