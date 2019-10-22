package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.EditCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//@@author jaedonkey
public class EditCommandParser implements CommandParser {

    @Override
    public Command parseCommand(String restOfInput) throws ParserException, ParseException {
        int taskId = getTokenTaskID(restOfInput);
        String description = getTokenDescription(restOfInput);
        Date date = getDate(restOfInput);
        Task.Priority priority = getTokenPriority(restOfInput);
        String startTime = getTokenStartTime(restOfInput);
        String endTime = getTokenEndTime(restOfInput);

        return new EditCommand(taskId, description, date, startTime, endTime, priority);


    }

    @Override
    public Date getDate(String restOfInput) throws ParserException, ParseException {
        if (restOfInput.contains(TOKEN_DATE)) {
            int startPoint = restOfInput.indexOf(TOKEN_DATE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String dateField = scanner.next();
            if (dateField.equals(EMPTY_INPUT_STRING) || dateField.charAt(0) == TOKEN_SLASH_CHAR) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            if (!isDateValid(dateField)) {
                throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
            }
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField);
            return date;
        } else {
            return null;
        }
    }

    @Override
    public String getTokenDescription(String restOfInput) throws ParserException {
        String description;
        if (restOfInput.contains(TOKEN_DESCRIPTION)) {
            int startPoint = restOfInput.indexOf(TOKEN_DESCRIPTION);
            String descriptionStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(descriptionStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            description = scanner.next();
        } else {
            return null;
        }
        return description;
    }

    @Override
    public String getTokenStartTime(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_START_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_START_TIME);
            String startTimeStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(startTimeStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            return scanner.next();
        } else {
            return null;
        }

    }

    @Override
    public String getTokenEndTime(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_END_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_END_TIME);
            String endTimeStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(endTimeStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String endTimeField = scanner.next();
            return endTimeField;
        } else {
            return null;
        }
    }
}
