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
    private static final String EMPTY_STACK_MESSAGE = "empty stack";
    private static final String NULL_MESSAGE = "null";

    //@@author yetong1895
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
        if (!actionInput.equals(EMPTY_STACK_MESSAGE)) {
            String[] parser = actionInput.split(" ", 2);
            if (parser[0].equals("remove")) {
                actionInput = parser[0] + " " + parser[1];
            } else {
                prevPosition = Integer.parseInt(parser[0]);
                actionInput = parser[1];
            }
        }
    }

    /**
     * This method will get teh action input from redo.
     */
    private void redoCommand() {
        actionInput = Redo.processRedo(mode);
    }

    /**
     * This method will get the value of actionInput by calling the respective method to get it.
     */
    private void processActionInput() {
        switch (command) {
        case "undo":
            undoCommand();
            break;
        case "redo":
            redoCommand();
            break;
        case "repeat":
            actionInput = Repeat.getRepeatInput(mode);
            break;
        default:
            break;
        }
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        processActionInput();
        if (!actionInput.equals(EMPTY_STACK_MESSAGE) && !actionInput.equals(NULL_MESSAGE)) {
            switch (command) {
            case "undo":
                setPrevPosition();
                break;
            case "redo":
                setRedoFlag();
                break;
            default:
                break;
            }

            Command c = MainParser.handleInput(mode, actionInput);
            c.execute(dollaData);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }


    /**
     * This method will set the previous position int in the respective mode.
     */
    private void setPrevPosition() {
        Parser.setPrevPosition(prevPosition);
    }

    /**
     * This method will call the setRedoFlag method in the respective mode.
     */
    private void setRedoFlag() {
        Parser.setRedoFlag();
    }
}
