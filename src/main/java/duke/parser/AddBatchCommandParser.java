package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AddBatchCommand;
import duke.logic.commands.Command;
import duke.models.locker.Address;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;
import duke.models.locker.Zone;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;
import duke.parser.utilities.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import static duke.parser.utilities.Syntax.TOKEN_ADDRESS;
import static duke.parser.utilities.Syntax.TOKEN_SERIAL;
import static duke.parser.utilities.Syntax.TOKEN_SIZE;
import static duke.parser.utilities.Syntax.TOKEN_ZONE;
import static java.util.Objects.requireNonNull;

public class AddBatchCommandParser {

    /**
     * This function is used to parse the user input for adding batches.
     * Later it will be included with all the possible checks for the validity of the command
     * @param userInput stores the command entered by the user
     * @return reference to the the class AddBatchCommand
     * @throws DukeException when the command syntax is invalid
     */
    public Command parse(String userInput) throws DukeException {
        MapTokensToArguments mapTokensToArguments = ParserTokenizer
                .tokenize(userInput, TOKEN_SIZE, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE);
        if (!checkAllTokensPresent(mapTokensToArguments,
                TOKEN_SIZE, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE)
                || !mapTokensToArguments.getTextBeforeFirstToken().isEmpty()) {
            throw new DukeException(" Invalid command format."
                    + "\n     1.All tokens should be present "
                    + "\n     2.There should not include any text between the command word and the first token");
        }

        SerialNumber serialNumber = ParserCheck.parseSerialNumber(
                mapTokensToArguments.getValue(TOKEN_SERIAL).get());
        Address address = ParserCheck.parseAddress(
                mapTokensToArguments.getValue(TOKEN_ADDRESS).get());
        Zone zone  = ParserCheck.parseZone(
                mapTokensToArguments.getValue(TOKEN_ZONE).get());
        int size = ParserCheck.parseSize(
                mapTokensToArguments.getValue(TOKEN_SIZE).get());
        List<Locker> addBatchOfLockers = new ArrayList<>();
        addBatchOfLockers = addLockersToList(addBatchOfLockers,serialNumber,address,zone,size);
        return new AddBatchCommand(addBatchOfLockers);
    }

    private static boolean checkAllTokensPresent(
            MapTokensToArguments mapTokensToArguments, Token... tokens) {
        return Stream.of(tokens).allMatch(token -> mapTokensToArguments
                .getValue(token).isPresent());
    }

    private static List<Locker> addLockersToList(List<Locker> addBatchOfLockers,
                                                 SerialNumber serialNumber, Address address,
                                                 Zone zone, int size) throws DukeException {
        requireNonNull(addBatchOfLockers);
        for (int i = 1; i <= size; i++) {
            addBatchOfLockers.add(
                    new Locker(serialNumber,address,zone,new Tag("not-in-use")));
            int serial = Integer.parseInt(serialNumber.getSerialNumberForLocker()) + 1;
            serialNumber = ParserCheck.parseSerialNumber(Integer.toString(serial));
        }
        return addBatchOfLockers;

    }
}
