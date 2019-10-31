package controlpanel;

import moneycommands.MoneyCommand;

import java.util.Stack;

/**
 * This class is in charge of storing and retrieving the last 5 commands issued to the program.
 */
public class UndoCommandHandler {

    private static Stack<MoneyCommand> lastIssuedCommands;

    //@@ author Chianhaoplanks
    /**
     * The constructor initializes the stack that is used to store previously issued commands to the program.
     */
    public UndoCommandHandler() {
        lastIssuedCommands = new Stack<>();
    }

    /**
     * This method stores the last 5 commands issued to the program.
     * @param c MoneyCommand to be stored into stack of lastIssuedCommands
     */
    public void updateLastIssuedCommands(MoneyCommand c) {
        lastIssuedCommands.push(c);
        if (lastIssuedCommands.size() > 5) {
            lastIssuedCommands.remove(0);
        }
    }

    /**
     * This method gets the last command issued to the program from the stack and pops it from the stack.
     * @return the last MoneyCommand that was entered into the program
     * @throws DukeException when there are no commands left in the stack
     */
    public MoneyCommand getLastIssuedCommand() throws DukeException {
        if (lastIssuedCommands.isEmpty()) {
            throw new DukeException("No commands to undo!");
        } else {
            MoneyCommand c = lastIssuedCommands.lastElement();
            lastIssuedCommands.pop();
            return c;
        }
    }

    /**
     * This method gets the size of the stack.
     * @return size of the stack
     */
    public int getLastIssuedCommandsSize() {
        return lastIssuedCommands.size();
    }
}
