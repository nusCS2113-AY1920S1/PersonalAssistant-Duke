package logic.parser;


import logic.command.DoneCommand;
import utils.DukeException;

public class DoneCommandParser {
    public static DoneCommand parseDoneCommand(String partialParsedCommand) throws DukeException {
        String[] indexesStirng = partialParsedCommand.split(" ");
        int[] indexes = new int[indexesStirng.length];
        for (int i = 0; i < indexes.length; i++) {
            try {
                int index = Integer.parseInt(indexesStirng[i]);
                indexes[i] = index - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Wrong number format, please check.");
            }
        }
        return new DoneCommand(indexes);
    }

}
