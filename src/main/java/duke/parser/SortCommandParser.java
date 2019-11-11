package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.SortCommand;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;
import duke.parser.utilities.Token;

import static duke.parser.utilities.Syntax.TOKEN_ASCENDING;
import static duke.parser.utilities.Syntax.TOKEN_DESCENDING;


public class SortCommandParser {

    /**
     * This function is used to parse the user input for sorting the list based on the single argument.
     * Later it will include all checks for validating the user input
     * @param userInput stores the user input
     * @return reference to the class SortCommand
     * @throws DukeException when the command format is invalid
     */

    public SortCommand parse(String userInput) throws DukeException {

        String sortByInput;

        int checkAscOrDes;

        MapTokensToArguments mapTokensToArguments =
                ParserTokenizer.tokenize(userInput, TOKEN_ASCENDING, TOKEN_DESCENDING);

        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_ASCENDING)) {

            checkAscOrDes = 1;
            sortByInput = mapTokensToArguments.getValue(TOKEN_ASCENDING).get();

        } else if (checkTokenPresent(mapTokensToArguments,
                TOKEN_DESCENDING)) {

            checkAscOrDes = 0;
            sortByInput = mapTokensToArguments.getValue(TOKEN_DESCENDING).get();

        } else {
            throw new DukeException(SortCommand.INVALID_FORMAT);
        }

        if (sortByInput.equalsIgnoreCase("serialNumber")) {

            return new SortCommand("serialNumber", checkAscOrDes);

        } else if (sortByInput.equalsIgnoreCase("address")) {

            return new SortCommand("address", checkAscOrDes);

        } else if (sortByInput.equalsIgnoreCase("zone")) {

            return new SortCommand("zone", checkAscOrDes);

        } else if (sortByInput.equalsIgnoreCase("tags")) {

            return new SortCommand("tags", checkAscOrDes);

        } else {
            throw new DukeException(" Invalid naming convention. \n"
                    + " \t Inputs allowed: \n" + " \t - serialNumber\n" + " \t - address\n"
                    + " \t - zone \n"
                    + " \t - tags \n");
        }
    }

    private static boolean checkTokenPresent(MapTokensToArguments mapTokensToArguments,
                                             Token tokens) {

        return mapTokensToArguments.getValue(tokens).isPresent();

    }
}