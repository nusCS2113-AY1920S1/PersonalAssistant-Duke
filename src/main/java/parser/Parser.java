package parser;
import command.*;
import exception.DukeException;
import ui.Ui;

import java.time.LocalDateTime;
import java.text.ParseException;

/**
 * The parser class is used to parse and make sense of the different queries the user inputs into the program and tag
 * them for further processing!
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */

public class Parser {

    /**
     * Parses the user input of string type and returns the respective command type
     *
     * @param userInput This string is provided by the user to ask 'Duke' to perform a particular action
     * @return Command After processing the user's input it returns the correct command for further processing
     * @throws DukeException The DukeException class has all the respective methods and messages!
     *
     */
    public static Command parse(String userInput) throws DukeException {

        String command = userInput.split("\\s+", 2)[0].trim();
        String taskFeatures;
        String checkType;
        String description;
        Integer indexOfTask;

        switch (command) {
            case "todo":
                try {
                    taskFeatures = userInput.split("\\s+", 2)[1].trim();
                }catch (ArrayIndexOutOfBoundsException e)
                {
                    throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                }
                if (taskFeatures.isEmpty()) {
                    throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                } else {
                    return new AddCommand(command, taskFeatures, null, null, null);
                }
            case "deadline":
                //fall through to avoid rewriting the same code multiple times!
            case "event":
                try {
                    taskFeatures = userInput.split("\\s+", 2)[1].trim();
                }catch (ArrayIndexOutOfBoundsException e)
                {
                    throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                }
                if (taskFeatures.isEmpty()) {
                    throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                } else {
                    if (command.contains("deadline")) {
                        checkType = "/by";
                    }
                    else {
                        checkType = "/at";
                    }
                    String taskDescription = taskFeatures.split(checkType, 2)[0].trim();
                    if (taskDescription.isEmpty()) {
                        throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                    }
                    String dateTimeFromUser;
                    LocalDateTime atDate = LocalDateTime.now();
                    LocalDateTime toDate = LocalDateTime.now();
                    LocalDateTime fromDate = LocalDateTime.now();
                    try {
                        dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
                        System.out.println(dateTimeFromUser);
                        if (checkType.contains("/by")){
                            atDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
                        }
                        else
                        {
                            String obtainStartDate = dateTimeFromUser.split("-",2)[0].trim();
                            fromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
                            String obtainEndDate = dateTimeFromUser.split("-",2)[1].trim();
                            toDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new DukeException(DukeException.EMPTY_DATE_OR_TIME());
                    } catch (ParseException e) {
                        throw new DukeException(DukeException.WRONG_DATE_OR_TIME());
                    }
                    return new AddCommand(command, taskDescription, atDate, toDate, fromDate);
                }
            case "find":
                String findKeyWord = userInput.split(command, 2)[1].trim();
                if (findKeyWord.isEmpty()) {
                    throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                }
                return new FindCommand(findKeyWord);

            case "delete":
                description = userInput.split(command, 2)[1].trim();
                if (description.isEmpty()) {
                    throw new DukeException(DukeException.EMPTY_USER_DESCRIPTION());
                }
                indexOfTask = Integer.parseInt(description) - 1;
                return new DeleteCommand(indexOfTask);

            case "done":
                description = userInput.split(command, 2)[1].trim();
                if (description.isEmpty()) {
                    throw new DukeException(DukeException.UNKNOWN_USER_COMMAND());
                }

                indexOfTask = Integer.parseInt(description) - 1;
                return new DoneCommand(indexOfTask);
            case "list":
                return new ListCommand();
            case "bye":
                return new ExitCommand();
            default:
                // Empty string or unknown command.
                Ui.printUnknownInput();
                throw new DukeException(DukeException.UNKNOWN_USER_COMMAND());
        }
    }
}