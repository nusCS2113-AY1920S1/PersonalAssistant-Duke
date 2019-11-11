package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.FindCommand;
import duke.models.locker.Address;
import duke.models.locker.SerialNumber;
import duke.models.locker.Zone;
import duke.models.student.Email;
import duke.models.student.Major;
import duke.models.student.Name;
import duke.models.student.StudentId;
import duke.models.tag.Tag;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;
import duke.parser.utilities.Token;

import static duke.parser.utilities.Syntax.TOKEN_SERIAL;
import static duke.parser.utilities.Syntax.TOKEN_ZONE;
import static duke.parser.utilities.Syntax.TOKEN_CONDITION;
import static duke.parser.utilities.Syntax.TOKEN_ADDRESS;
import static duke.parser.utilities.Syntax.TOKEN_STUDENT_COURSE;
import static duke.parser.utilities.Syntax.TOKEN_STUDENT_NAME;
import static duke.parser.utilities.Syntax.TOKEN_STUDENTID;
import static duke.parser.utilities.Syntax.TOKEN_EMAIL;

public class FindCommandParser {

    /**
     * This function is used to parse the user input for finding a locker to the list.
     * Later it will include all checks for validating the user input
     * @param userInput stores the user input
     * @return reference to the class FindCommand
     * @throws DukeException when the command format is invalid
     */

    public FindCommand parse(String userInput) throws DukeException {

        MapTokensToArguments mapTokensToArguments =
                ParserTokenizer.tokenize(userInput, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE, TOKEN_CONDITION,
                        TOKEN_STUDENT_NAME, TOKEN_STUDENTID, TOKEN_STUDENT_COURSE, TOKEN_EMAIL);

        FindCommand.FindLocker findLocker = new FindCommand.FindLocker();
        FindCommand.FindStudent findStudent = new FindCommand.FindStudent();

        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_SERIAL)) {
            SerialNumber serialNumber = ParserCheck.parseSerialNumber(
                    mapTokensToArguments.getValue(TOKEN_SERIAL).get());

            findLocker.setSerialNumber(serialNumber);
        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_ADDRESS)) {
            Address address = ParserCheck.parseAddress(
                    mapTokensToArguments.getValue(TOKEN_ADDRESS).get());

            findLocker.setAddress(address);
        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_ZONE)) {

            Zone zone = ParserCheck.parseZone(
                    mapTokensToArguments.getValue(TOKEN_ZONE).get());

            findLocker.setZone(zone);

        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_CONDITION)) {

            Tag tag = ParserCheck.parseStatus(
                    mapTokensToArguments.getValue(TOKEN_CONDITION).get());

            findLocker.setTag(tag);
        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_STUDENT_NAME)) {

            Name name = ParserCheck.parseName(
                    mapTokensToArguments.getValue(TOKEN_STUDENT_NAME).get());

            findStudent.setName(name);

        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_STUDENTID)) {

            StudentId studentId = ParserCheck.parseMatricNumber(
                    mapTokensToArguments.getValue(TOKEN_STUDENTID).get());

            findStudent.setMatricNumber(studentId);

        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_STUDENT_COURSE)) {

            Major major = ParserCheck.parseMajor(
                    mapTokensToArguments.getValue(TOKEN_STUDENT_COURSE).get());

            findStudent.setMajor(major);

        }
        if (checkTokenPresent(mapTokensToArguments,
                TOKEN_EMAIL)) {

            Email email = ParserCheck.parseEmail(
                    mapTokensToArguments.getValue(TOKEN_EMAIL).get());

            findStudent.setEmail(email);

        }

        if (!findLocker.missingFields() && !findStudent.missingFields()) {
            throw new DukeException(FindCommand.INVALID_FORMAT);
        }

        return new FindCommand(findLocker, findStudent);
    }

    private static boolean checkTokenPresent(MapTokensToArguments mapTokensToArguments,
                                             Token tokens) {

        return mapTokensToArguments.getValue(tokens).isPresent();

    }

}
