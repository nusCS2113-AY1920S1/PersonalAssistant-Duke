package dolla.command;

import dolla.DollaData;
import dolla.action.redo;
import dolla.action.undo;
import dolla.parser.DebtsParser;
import dolla.parser.EntryParser;
import dolla.parser.MainParser;
import dolla.parser.Parser;

public class AddActionCommand extends Command{
    private String mode;
    private String command;
    private String UserInput;
    private int prevPosition;

    public AddActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    public void undoCommand() {
        UserInput = undo.processCommand(mode);
        String[] parser = UserInput.split(" ",2);
        if(parser[0].equals("remove")) {
            UserInput = parser[0] + " " + parser[1];
        } else {
            prevPosition = Integer.parseInt(parser[0]);
            UserInput = parser[1];
        }
    }

    public void redoCommand() {
        UserInput = redo.processRedo();
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        if(command.equals("undo")) {
            undoCommand();
//            redo.redoReady(mode); move to somewhere else
        } else if (command.equals("redo")) {
            redo.redoReady(mode);
            redoCommand();
        }
        if(mode.equals("debt")) {
            DebtsParser.setPrePosition(prevPosition);
        } else if(mode.equals("entry")) {
            EntryParser.setPrePosition(prevPosition);
        }
        Command c = MainParser.handleInput(mode, UserInput);
        c.execute(dollaData);
    }
}
