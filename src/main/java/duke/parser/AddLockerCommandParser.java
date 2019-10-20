package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AddLockerCommand;
import duke.logic.commands.Command;

import duke.models.Address;
import duke.models.Locker;
import duke.models.SerialNumber;
import duke.models.Tag;
import duke.models.Zone;


import java.util.stream.Stream;

import static duke.parser.PrefixSyntax.PREFIX_ADDRESS;
import static duke.parser.PrefixSyntax.PREFIX_SERIAL;
import static duke.parser.PrefixSyntax.PREFIX_ZONE;

public class AddLockerCommandParser {

    /**
     * This function is used to parse the user input for adding a new locker to the list.
     * Later it will include all checks for validating the user input
     * @param userInput stores the user input
     * @return reference to the class AddLockerCommand
     * @throws DukeException when the command format is invalid
     */
    public Command parse(String userInput) throws DukeException {
        MapPrefixesToArguments mapPrefixesToArguments =
                ParserTokenizer.tokenize(userInput, PREFIX_SERIAL, PREFIX_ADDRESS, PREFIX_ZONE);
        if (!checkAllPrefixesPresent(mapPrefixesToArguments,
                PREFIX_SERIAL, PREFIX_ADDRESS, PREFIX_ZONE)
                || !mapPrefixesToArguments.getTextBeforeFirstPrefix().isEmpty()) {
            throw new DukeException(" Invalid command format");
        }

        SerialNumber serialNumber = ParserCheck.parseSerialNumber(
                mapPrefixesToArguments.getValue(PREFIX_SERIAL).get());
        Address address = ParserCheck.parseAddress(
                mapPrefixesToArguments.getValue(PREFIX_ADDRESS).get());
        Zone zone = ParserCheck.parseZone(mapPrefixesToArguments.getValue(PREFIX_ZONE).get());
        Tag tag = new Tag("not-in-use");
        Locker locker = new Locker(serialNumber, address, zone, tag);
        return new AddLockerCommand(locker);
    }

    private static boolean checkAllPrefixesPresent(MapPrefixesToArguments mapPrefixesToArguments,
                                                   Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> mapPrefixesToArguments
                .getValue(prefix).isPresent());
    }
}
