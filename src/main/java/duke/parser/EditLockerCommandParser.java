package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.EditLockerCommand;
import duke.logic.commands.EditLockerCommand.EditLocker;
import duke.models.locker.SerialNumber;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;

import static duke.parser.utilities.Syntax.TOKEN_ADDRESS;
import static duke.parser.utilities.Syntax.TOKEN_CONDITION;
import static duke.parser.utilities.Syntax.TOKEN_SERIAL;
import static duke.parser.utilities.Syntax.TOKEN_ZONE;
import static java.util.Objects.requireNonNull;

public class EditLockerCommandParser {

    /**
     * This function is used to parse the user input for editing the status of a locker.
     * @param userInput stores the user input
     * @return reference to the class EditLockerCommand
     * @throws DukeException when the user input is invalid
     */
    public EditLockerCommand parse(String userInput) throws DukeException {
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