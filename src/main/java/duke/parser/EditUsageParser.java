package duke.parser;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.logic.commands.EditUsageCommand;
import duke.logic.commands.EditUsageCommand.EditLockerDate;
import duke.logic.commands.EditUsageCommand.EditStudent;
import duke.models.locker.SerialNumber;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;

import java.util.logging.Level;
import java.util.logging.Logger;

import static duke.parser.utilities.Syntax.TOKEN_EMAIL;
import static duke.parser.utilities.Syntax.TOKEN_END_DATE;
import static duke.parser.utilities.Syntax.TOKEN_START_DATE;
import static duke.parser.utilities.Syntax.TOKEN_STUDENTID;
import static duke.parser.utilities.Syntax.TOKEN_STUDENT_COURSE;
import static duke.parser.utilities.Syntax.TOKEN_STUDENT_NAME;
import static java.util.Objects.requireNonNull;

/**
 * Parses the user input and returns a new EditUsageCommand object.
 */
public class EditUsageParser {
    private static final Logger logger = Log.getLogger();
    private static final String LOG_PARSE_FOR_EDIT_USAGE = "Attempting to parse user input for EditUsageCommand";

    /**
     * Parses the user input for editing the subscription details of a in-use locker.
     * @param userInput stores the user input
     * @return reference to the class EditUsageCommand
     * @throws DukeException when the user input is invalid
     */
    public EditUsageCommand parse(String userInput) throws DukeException {
        logger.log(Level.INFO, LOG_PARSE_FOR_EDIT_USAGE);
        requireNonNull(userInput);
        MapTokensToArguments mapTokensToArguments = ParserTokenizer
                .tokenize(userInput, TOKEN_STUDENT_NAME, TOKEN_STUDENTID, TOKEN_EMAIL,
                        TOKEN_STUDENT_COURSE, TOKEN_START_DATE, TOKEN_END_DATE);
        EditStudent editStudent = new EditStudent();
        EditLockerDate editDate = new EditLockerDate();
        getParametersForStudent(editStudent, mapTokensToArguments);
        getParametersForLockerDate(editDate, mapTokensToArguments);
        if (!(editDate.checkAnyFieldUpdated() || editStudent.checkAnyFieldUpdated())) {
            throw new DukeException(EditUsageCommand.INVALID_FORMAT);
        }
        SerialNumber serialNumber = ParserCheck.parseSerialNumber(mapTokensToArguments
                .getTextBeforeFirstToken());
        return new EditUsageCommand(serialNumber, editStudent, editDate);
    }

    /**
     * Stores all the fields associated to a student that are to be edited in {@code editStudent}.
     * @param mapTokens stores the mapping of tokens to their respective arguments
     * @throws DukeException when the command format is invalid
     */
    private void getParametersForStudent(EditStudent editStudent,
                                         MapTokensToArguments mapTokens) throws DukeException {
        if (mapTokens.getValue(TOKEN_STUDENT_NAME).isPresent()) {
            editStudent.setName(ParserCheck.parseName(
                    mapTokens.getValue(TOKEN_STUDENT_NAME).get()));
        }

        if (mapTokens.getValue(TOKEN_EMAIL).isPresent()) {
            editStudent.setEmail(ParserCheck.parseEmail(
                    mapTokens.getValue(TOKEN_EMAIL).get()));
        }

        if (mapTokens.getValue(TOKEN_STUDENTID).isPresent()) {
            editStudent.setStudentId(ParserCheck.parseMatricNumber(
                    mapTokens.getValue(TOKEN_STUDENTID).get()));
        }

        if (mapTokens.getValue(TOKEN_STUDENT_COURSE).isPresent()) {
            editStudent.setMajor(ParserCheck.parseMajor(
                    mapTokens.getValue(TOKEN_STUDENT_COURSE).get()));
        }
    }

    /**
     * Stores all the fields associated to the rental period that are to be edited in {@code editDate}.
     * @param mapTokens stores the mapping of tokens to their respective arguments
     * @throws DukeException when the command format is invalid
     */
    private void getParametersForLockerDate(EditLockerDate editDate,
                                            MapTokensToArguments mapTokens) throws DukeException {

        if (mapTokens.getValue(TOKEN_START_DATE).isPresent()) {
            editDate.setStartDate(ParserCheck.parseDate(mapTokens.getValue(
                    TOKEN_START_DATE).get()));
        }

        if (mapTokens.getValue(TOKEN_END_DATE).isPresent()) {
            editDate.setEndDate(ParserCheck.parseDate(
                    mapTokens.getValue(TOKEN_END_DATE).get()));
        }
    }
}

