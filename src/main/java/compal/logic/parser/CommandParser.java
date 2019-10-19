package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParserException;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;


public interface CommandParser {

    String TOKEN_TASK_ID = "/id";
    String TOKEN_STATUS = "/s";
    String MESSAGE_MISSING_TOKEN = "Error: Missing token!";
    String MESSAGE_MISSING_INPUT = "Error: Missing input!";

    Command parseCommand(String input) throws ParserException;

    /**
     * Returns the task ID in the String input.
     *
     * @param input String input of user
     * @return taskID
     * @throws ParserException if the token (/id) or id number is missing
     */
    default int getTaskID(String input) throws ParserException {
        if (input.contains(TOKEN_TASK_ID)) {
            int startPoint = input.indexOf(TOKEN_TASK_ID);
            String taskIdInput = input.substring(startPoint);
            Scanner scanner = new Scanner(taskIdInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            int taskId = Integer.parseInt(scanner.next());
            return taskId;
        } else {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }
    }

    /**
     * Returns the reminder status in the String input.
     *
     * @param input String input of user
     * @return reminder status
     * @throws ParserException if the token (/s) or reminder status is missing
     */
    default String getStatus(String input) throws ParserException {
        if (input.contains(TOKEN_STATUS)) {
            int startPoint = input.indexOf(TOKEN_STATUS);
            String statusInput = input.substring(startPoint);
            Scanner scanner = new Scanner(statusInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String status = scanner.next();
            return status;
        } else {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }
    }

    /**
     * Check if the date input is of valid format.
     *
     * @param date the string of the date input
     * @return true or false.
     */
    default boolean isDateValid(String date) {
        final  String DATE_FORMAT = "dd/MM/yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}