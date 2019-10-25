package dolla.command;

import dolla.DollaData;
import dolla.action.Redo;
import dolla.action.Repeat;
import dolla.action.Undo;
import dolla.parser.MainParser;
import dolla.parser.Parser;

public class AddActionCommand extends Command {
    private String mode;
    private String command;
    private String actionInput;
    private int prevPosition;

    /**
     * create an instance of AddActionCommand.
     * @param mode    the mode that the user is in.
     * @param command the command that the user input.
     */
    public AddActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    /**
     * This method will get the action input from undo with respect
     * the the mode that the user is in.
     */
    private void undoCommand() {
        actionInput = Undo.processCommand(mode);
        String[] parser = actionInput.split(" ",2);
        if (parser[0].equals("remove")) {
            actionInput = parser[0] + " " + parser[1];
        } else {
            prevPosition = Integer.parseInt(parser[0]);
            actionInput = parser[1];
        }
    }

    /**
     * This method will get teh action input from redo.
     */
    private void redoCommand() {
        actionInput = Redo.processRedo();
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        switch (command) {
        case "undo":
            undoCommand();
            setPrevPosition();
            break;
        case "redo":
            setRedoFlag();
            Redo.redoReady(mode);
            redoCommand();
            break;
        case "repeat":
            actionInput = Repeat.getRepeatInput(mode);
            break;
        default:
            System.out.println("Invalid input"); //TODO:move to UI
        }
        Command c = MainParser.handleInput(mode, actionInput);
        c.execute(dollaData);
        resetUndoFlag();
        resetRedoFlag();
    }

    /**
     * This method will set the previous position int in the respective mode.
     */
    private void setPrevPosition() {
        Parser.setPrevPosition(prevPosition);
    }

    /**
     * This method will call the resetUndoFlag method in the respective mode.
     */
    private void resetUndoFlag() {
        Parser.resetUndoFlag();
    }

    /**
     * This method will call the setRedoFlag method in the respective mode.
     */
    private void setRedoFlag() {
        Parser.setRedoFlag();
    }

    /**
     * This method will call the resetRedoFlag method in the respective mode.
     */
    private void resetRedoFlag() {
        Parser.resetUndoFlag();
    }
}
