package logic.parser;


import logic.command.DoneCommand;
import common.DukeException;

public class DoneCommandParser {
    //@@author chenyuheng

    /**
     * add javadoc please
     */
    public static DoneCommand parseDoneCommand(String partialCommand) throws DukeException {
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

}
