package controlpanel;

import moneycommands.MoneyCommand;
import moneycommands.UndoCommand;

public class UndoCommandHandler {
    private static MoneyCommand lastIssuedCommand;
    public UndoCommandHandler() {
        lastIssuedCommand = null;
    }
    public void updateLastIssuedCommands(MoneyCommand c) {
        lastIssuedCommand = c;
        if (c.getClass().equals(UndoCommand.class)) {
            lastIssuedCommand = null;
        }
    }
    public MoneyCommand getLastIssuedCommand() throws DukeException {
        if (lastIssuedCommand == null) {
            throw new DukeException("No commands to undo!");
        } else {
           return lastIssuedCommand;
        }
    }
}
