package controlpanel;

import money.Item;
import moneycommands.MoneyCommand;

import java.util.Stack;

public class UndoCommandHandler {

    private static Stack<MoneyCommand> lastIssuedCommands;

    //@@ author Chianhaoplanks
    public UndoCommandHandler() {
        lastIssuedCommands = new Stack<>();
    }
    public void updateLastIssuedCommands(MoneyCommand c) {
        lastIssuedCommands.push(c);
        if (lastIssuedCommands.size() > 3) {
            lastIssuedCommands.remove(0);
        }
    }
    public MoneyCommand getLastIssuedCommand() throws DukeException {
        if (lastIssuedCommands.isEmpty()) {
            throw new DukeException("No commands to undo!");
        } else {
           MoneyCommand c = lastIssuedCommands.lastElement();
           lastIssuedCommands.pop();
           return c;
        }
    }
}
