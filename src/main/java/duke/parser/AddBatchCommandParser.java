package duke.parser;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.logic.commands.AddBatchCommand;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


import static duke.parser.utilities.Syntax.TOKEN_ADDRESS;
import static duke.parser.utilities.Syntax.TOKEN_SERIAL;
import static duke.parser.utilities.Syntax.TOKEN_SIZE;
import static duke.parser.utilities.Syntax.TOKEN_ZONE;
import static java.util.Objects.requireNonNull;

/**
 * Parses user input and creates a new AddBatchCommand object.
 */
public class AddBatchCommandParser {
    private static final Logger logger = Log.getLogger();
    private static final String LOG_ADD_BATCH_COMMAND_PARSER = " Attempting to parse input for AddBatchCommand";

    /**
     * Parse the user input for adding batches (multiple) of lockers to the list of lockers.
     * It includes all the possible checks for the validity of the command
     * @param userInput stores the command entered by the user
     * @return reference to the the class AddBatchCommand
     * @throws DukeException when the command syntax/format is invalid
     */
    public AddBatchCommand parse(String userInput) throws DukeException {
        logger.log(Level.INFO, LOG_ADD_BATCH_COMMAND_PARSER);

        MapTokensToArguments mapTokensToArguments = ParserTokenizer
                .tokenize(userInput, TOKEN_SIZE, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE);
        if (!checkAllTokensPresent(mapTokensToArguments,
                TOKEN_SIZE, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE)
                || !mapTokensToArguments.getTextBeforeFirstToken().isEmpty()) {
            throw new DukeException(AddBatchCommand.INVALID_FORMAT);
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
        addBatchOfLockers = addLockersToList(addBatchOfLockers, serialNumber, address, zone, size);
        return new AddBatchCommand(addBatchOfLockers);
    }

    /**
     * Returns true if none of the tokens contain empty values.
     */
    private static boolean checkAllTokensPresent(
            MapTokensToArguments mapTokensToArguments, Token... tokens) {
        return Stream.of(tokens).allMatch(token -> mapTokensToArguments
                .getValue(token).isPresent());
    }

    /**
     * Adds the multiple lockers into a single list.
     * @param addBatchOfLockers stores the list of lockers to be added
     * @param serialNumber stores the starting serial number for the batch of lockers
     * @param address stores the address of the batch
     * @param zone stores the zone
     * @param size stores the number of lockers to be added as a batch
     * @return the list that will be added
     * @throws DukeException if the serial number is invalid
     */
    private static List<Locker> addLockersToList(List<Locker> addBatchOfLockers,
                                                 SerialNumber serialNumber, Address address,
                                                 Zone zone, int size) throws DukeException {
        requireNonNull(addBatchOfLockers);
        for (int i = 1; i <= size; i++) {
            addBatchOfLockers.add(
                    new Locker(serialNumber, address, zone, new Tag(Tag.NOT_IN_USE),null));
            int serial = Integer.parseInt(serialNumber.getSerialNumberForLocker()) + 1;
            serialNumber = ParserCheck.parseSerialNumber(Integer.toString(serial));
        }
        return addBatchOfLockers;
    }
}
