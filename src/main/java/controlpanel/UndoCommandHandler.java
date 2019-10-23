package controlpanel;

import moneycommands.MoneyCommand;

import java.util.ArrayDeque;

public class UndoCommandHandler {
    private static MoneyCommand lastIssuedCommand;

    //@@ author Chianhaoplanks
    public UndoCommandHandler() {
        lastIssuedCommand = null;
    }
    public void updateLastIssuedCommands(MoneyCommand c) {
        lastIssuedCommand = c;
    }
    public MoneyCommand getLastIssuedCommand() throws DukeException {
        if (lastIssuedCommand == null) {
            throw new DukeException("No commands to undo!");
        } else {
           return lastIssuedCommand;
        }
    }
}
