package logic.parser;

import core.Duke;
import logic.command.LinkCommand;
import logic.command.UnlinkCommand;
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
                    + "e.g. link 1 2 3 /to Alice, Bob");
        }
        String[] indexesString = multimap.get("").split(" ");
        String[] membersNameString = multimap.get("/to").split(",");
        int[] tasksIndexes = new int[indexesString.length];
        for (int i = 0; i < tasksIndexes.length; i++) {
            try {
                int index = Integer.parseInt(indexesString[i]);
                tasksIndexes[i] = index - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Wrong index format, please check and try again.");
            }
        }
        for (int i = 0; i < membersNameString.length; i++) {
            membersNameString[i] = membersNameString[i].trim();
        }
        return new LinkCommand(tasksIndexes, membersNameString);
    }

    /**
     * parse unlink command.
     * @param partialParsedCommand the command line without the first word "unlink"
     * @return a new UnlinkCommand object
     * @throws DukeException if any parse related exception happen
     */
    public static UnlinkCommand parseUnlinkCommand(String partialParsedCommand) throws DukeException {
        HashMap<String, String> multimap = ArgumentTokenizer.tokenize(partialParsedCommand);
        if (!multimap.containsKey("") || !multimap.containsKey("/from")) {
            throw new DukeException("Wrong command format. \n"
                    + "Should be: unlink [task(s) index(es)] /from [member(s) name(s)]\n"
                    + "e.g. unlink 1 2 3 /from Alice Bob");
        }
        String[] indexesString = multimap.get("").split(" ");
        String[] membersNameString = multimap.get("/from").split(",");
        int[] tasksIndexes = new int[indexesString.length];
        for (int i = 0; i < tasksIndexes.length; i++) {
            try {
                int index = Integer.parseInt(indexesString[i]);
                tasksIndexes[i] = index - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Wrong index format, please check and try again.");
            }
        }
        for (int i = 0; i < membersNameString.length; i++) {
            membersNameString[i] = membersNameString[i].trim();
        }
        return new UnlinkCommand(tasksIndexes, membersNameString);
    }
}
