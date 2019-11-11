package duke.parser;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.logic.commands.EditLockerCommand;
import duke.logic.commands.EditLockerCommand.EditLocker;
import duke.models.locker.SerialNumber;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;

import java.util.logging.Level;
import java.util.logging.Logger;

import static duke.parser.utilities.Syntax.TOKEN_ADDRESS;
import static duke.parser.utilities.Syntax.TOKEN_CONDITION;
import static duke.parser.utilities.Syntax.TOKEN_SERIAL;
import static duke.parser.utilities.Syntax.TOKEN_ZONE;
import static java.util.Objects.requireNonNull;

/**
 * Parses the user input and creates a new EditLockerCommand object.
 */
public class EditLockerCommandParser {
    private static final Logger logger = Log.getLogger();
    private static final String LOG_PARSER_FOR_EDIT_LOCKER = "Attempting to parse user input for EditLockerCommand";

    /**
     * Parses the user input for editing the various fields associated with a locker.
     * @param userInput stores the user input
     * @return reference to the class EditLockerCommand
     * @throws DukeException when the user input is invalid
     */
    public EditLockerCommand parse(String userInput) throws DukeException {
        logger.log(Level.INFO, LOG_PARSER_FOR_EDIT_LOCKER);
        requireNonNull(userInput);
        MapTokensToArguments mapTokensToArguments = ParserTokenizer
                .tokenize(userInput, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE,
                        TOKEN_CONDITION);
        SerialNumber serialNumber = ParserCheck.parseSerialNumber(mapTokensToArguments
                .getTextBeforeFirstToken().trim());
        EditLocker editLocker = new EditLocker();
        getParametersForLocker(editLocker, mapTokensToArguments);
        if (!editLocker.checkAnyFieldUpdated()) {
            throw new DukeException(EditLockerCommand.INVALID_FORMAT);
        }
        return new EditLockerCommand(serialNumber, editLocker);
    }

    /**
     * Stores all the fields that are to be edited in {@code editLocker} .
     * @param mapTokensToArguments stores the mapping of tokens to arguments
     * @throws DukeException if the command format is invalid
     */
    private void getParametersForLocker(EditLocker editLocker,
                                        MapTokensToArguments mapTokensToArguments) throws DukeException {
        if (mapTokensToArguments.getValue(TOKEN_SERIAL).isPresent()) {
            editLocker.setSerialNumber(ParserCheck.parseSerialNumber(
                    mapTokensToArguments.getValue(TOKEN_SERIAL).get()));
        }

        if (mapTokensToArguments.getValue(TOKEN_ADDRESS).isPresent()) {
            editLocker.setAddress(ParserCheck.parseAddress(
                    mapTokensToArguments.getValue(TOKEN_ADDRESS).get()));
        }

        if (mapTokensToArguments.getValue(TOKEN_ZONE).isPresent()) {
            editLocker.setZone(ParserCheck.parseZone(
                    mapTokensToArguments.getValue(TOKEN_ZONE).get()));
        }

        if (mapTokensToArguments.getValue(TOKEN_CONDITION).isPresent()) {
            editLocker.setCondition(ParserCheck.parseStatus(
                    mapTokensToArguments.getValue(TOKEN_CONDITION).get()));
        }

    }
}