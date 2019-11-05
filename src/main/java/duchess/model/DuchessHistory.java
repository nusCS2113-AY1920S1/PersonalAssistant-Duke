package duchess.model;

import duchess.logic.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class DuchessHistory {
    private static List<String> duchessLog;

    private static List<Command> validDuchessLog;

    public DuchessHistory() {
        duchessLog = new ArrayList<>();
        validDuchessLog = new ArrayList<>();
    }

    public void add(String input) {
        duchessLog.add(input);
    }

    public static void addValidCommands(Command command) {
        validDuchessLog.add(command);
    }

    public static List<String> getFullLog() {
        return duchessLog;
    }

    public static List<Command> getValidCommandLog() {
        return validDuchessLog;
    }
}