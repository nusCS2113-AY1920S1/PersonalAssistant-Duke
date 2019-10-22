package dolla.command;

import dolla.DollaData;
import dolla.action.Undo;
import dolla.parser.DebtsParser;
import dolla.parser.EntryParser;
import dolla.parser.MainParser;

public class AddActionCommand extends Command {
    private String mode;
    private String command;
    private String userInput;
    private int prevPosition;

    public AddActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    /**
     * //TODO: add javadoc.
     */
    public void undoCommand() {
        userInput = Undo.processCommand(mode);
        String[] parser = userInput.split(" ",2);
        if (parser[0].equals("remove")) {
            userInput = parser[0] + " " + parser[1];
        } else {
            prevPosition = Integer.parseInt(parser[0]);
            userInput = parser[1];
        }
    }

    //process Redo

    @Override
    public void execute(DollaData dollaData) throws Exception {
        if (command.equals("undo")) {
            undoCommand();
        } else if (command.equals("redo")) {
            //Redo
        }
        if (mode.equals("debt")) {
            DebtsParser.setPrePosition(prevPosition);
        } else if (mode.equals("entry")) {
            EntryParser.setPrePosition(prevPosition);
        }
        Command c = MainParser.handleInput(mode, userInput);
        c.execute(dollaData);
    }
}
