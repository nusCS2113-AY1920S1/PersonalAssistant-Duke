package logic.parser;


import logic.command.DoneCommand;
import logic.command.UndoneCommand;
import common.DukeException;

public class DoneCommandParser {
    public static final String USAGE_UNDONE = "Usage: undone [INDEX] ...\nFor example: undone 1";
    public static final String USAGE_DONE = "Usage: done [INDEX] ...\nFor example: done 1";

    //@@author chenyuheng
    /**
     * Parse done command.
     * @param partialCommand The indexe(s) of tasks to be marked as done.
     * @return the corresponding DoneCommand object.
     * @throws DukeException If command is not correct.
     */
    public static DoneCommand parseDoneCommand(String partialCommand) throws DukeException {
        if (partialCommand.trim().length() == 0) {
            throw new DukeException(USAGE_DONE);
        }
        String[] indexesString = partialCommand.split(" ");
        int[] indexes = new int[indexesString.length];
        for (int i = 0; i < indexes.length; i++) {
            try {
                int index = Integer.parseInt(indexesString[i]);
                indexes[i] = index - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Wrong number format, please check.");
            }
        }
        return new DoneCommand(indexes);
    }

    //@@author chenyuheng
    /**
     * Parse undone command.
     * @param partialCommand The indexe(s) of tasks to be marked as undone.
     * @return the corresponding UndoneCommand object.
     * @throws DukeException If command is not correct.
     */
    public static UndoneCommand parseUndoneCommand(String partialCommand) throws DukeException {
        if (partialCommand.trim().length() == 0) {
            throw new DukeException(USAGE_UNDONE);
        }
        String[] indexesString = partialCommand.split(" ");
        int[] indexes = new int[indexesString.length];
        for (int i = 0; i < indexes.length; i++) {
            try {
                int index = Integer.parseInt(indexesString[i]);
                indexes[i] = index - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Wrong number format, please check.");
            }
        }
        return new UndoneCommand(indexes);
    }
}
