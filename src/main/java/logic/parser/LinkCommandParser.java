package logic.parser;

import logic.command.LinkCommand;
import common.DukeException;

import java.util.HashMap;

public class LinkCommandParser {

    /**
     * add javadoc please
     * */
    public static LinkCommand parseLinkCommand(String partialParsedCommand) throws DukeException {
        HashMap<String, String> multimap = ArgumentTokenizer.tokenize(partialParsedCommand);
        if (!multimap.containsKey("") || !multimap.containsKey("/to")) {
            throw new DukeException("Wrong command format. \n"
                    + "Should be: link [task(s) index(es)] /to [member(s) name(s)]\n"
                    + "e.g. link 1 2 3 /to Alice Bob");
        }
        String[] indexesString = multimap.get("").split(" ");
        String[] membersNameString = multimap.get("/to").split(" ");
        int[] tasksIndexes = new int[indexesString.length];
        for (int i = 0; i < tasksIndexes.length; i++) {
            try {
                int index = Integer.parseInt(indexesString[i]);
                tasksIndexes[i] = index;
            } catch (NumberFormatException e) {
                throw new DukeException("Wrong index format, please check and try again.");
            }
        }
        return new LinkCommand(tasksIndexes, membersNameString);
    }
}
