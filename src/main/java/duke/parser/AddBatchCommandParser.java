package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AddBatchCommand;
import duke.logic.commands.Command;
import duke.models.Address;
import duke.models.Locker;
import duke.models.SerialNumber;
import duke.models.Tag;
import duke.models.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import static duke.parser.PrefixSyntax.PREFIX_ADDRESS;
import static duke.parser.PrefixSyntax.PREFIX_SERIAL;
import static duke.parser.PrefixSyntax.PREFIX_SIZE;
import static duke.parser.PrefixSyntax.PREFIX_ZONE;
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
        MapPrefixesToArguments mapPrefixesToArguments = ParserTokenizer
                .tokenize(userInput, PREFIX_SIZE, PREFIX_SERIAL,PREFIX_ADDRESS,PREFIX_ZONE);
        if (!checkAllPrefixesPresent(mapPrefixesToArguments,
                PREFIX_SIZE,PREFIX_SERIAL,PREFIX_ADDRESS,PREFIX_ZONE)
                || !mapPrefixesToArguments.getTextBeforeFirstPrefix().isEmpty()) {
            throw new DukeException(" Invalid command format");
        }

        SerialNumber serialNumber = ParserCheck.parseSerialNumber(
                mapPrefixesToArguments.getValue(PREFIX_SERIAL).get());
        Address address = ParserCheck.parseAddress(
                mapPrefixesToArguments.getValue(PREFIX_ADDRESS).get());
        Zone zone  = ParserCheck.parseZone(
                mapPrefixesToArguments.getValue(PREFIX_ZONE).get());
        int size = ParserCheck.parseSize(
                mapPrefixesToArguments.getValue(PREFIX_SIZE).get());
        List<Locker> addBatchOfLockers = new ArrayList<>();
        addBatchOfLockers = addLockersToList(addBatchOfLockers,serialNumber,address,zone,size);
        return new AddBatchCommand(addBatchOfLockers);
    }

    private static boolean checkAllPrefixesPresent(
            MapPrefixesToArguments mapPrefixesToArguments,Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> mapPrefixesToArguments
                .getValue(prefix).isPresent());
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
