package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.EditCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

//@@author jaedonkey
public class EditCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(EditCommandParser.class);


    @Override
    public Command parseCommand(String restOfInput) throws ParserException, ParseException {
        logger.info("Attempting to parse edit command");
        int taskId = getTaskID(restOfInput);
        String description = getTokenDescription(restOfInput);
        Date date = getDate(restOfInput);
        Task.Priority priority = getTokenPriority(restOfInput);
        String startTime = getTokenStartTime(restOfInput);
        String endTime = getTokenEndTime(restOfInput);

        return new EditCommand(taskId, description, date, startTime, endTime, priority);


    }

    @Override
    public Task.Priority getTokenPriority(String restOfInput) throws ParserException {
        Task.Priority priorityField;
        if (restOfInput.contains(TOKEN_PRIORITY)) {
            int startPoint = restOfInput.indexOf(TOKEN_PRIORITY);
            String priorityStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(priorityStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String commandPriority = scanner.next();
            if (isPriorityValid(commandPriority)) {
                priorityField = Task.Priority.valueOf(commandPriority.toLowerCase());
            } else {
                throw new ParserException(MESSAGE_INVALID_PRIORITY);
            }
        } else {
            priorityField = null;
        }
        return priorityField;
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
            String sub = scanner.nextLine();
            int splitPoint = sub.indexOf("/");
            if (splitPoint == -1) {
                description = sub;
            } else {
                description = sub.substring(0,splitPoint);
            }
        } else {
            System.out.println("No desc found!!!");
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
