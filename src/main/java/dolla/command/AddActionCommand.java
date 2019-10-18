package dolla.command;

import dolla.DollaData;
import dolla.action.undo;
import dolla.parser.MainParser;

public class AddActionCommand extends Command{
    private String mode;
    private String command;
    private String UserInput;

    public AddActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    public void undoCommand() {
        UserInput = undo.processUndo();
    }

    //process redo

    @Override
    public void execute(DollaData dollaData) throws Exception {
        if(command.equals("undo")) {
            undoCommand();
        } else if (command.equals("redo")) {
            //redo
        }
        System.out.println(UserInput);
        Command c = MainParser.handleInput(mode, UserInput);
        c.execute(dollaData);
    }
}
