package diyeats.logic.parsers;

import diyeats.logic.commands.ListTransactionsCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu

/**
 * Parser class to handle ListTransactionCommand.
 */
public class ListTransactionCommandParser implements ParserInterface<ListTransactionsCommand> {
    private static Logger logger = Logger.getLogger(ListTransactionCommandParser.class.getName());

    /**
     * Parse userInput and return ListTransactionCommand.
     * @param userInputStr String input by user.
     * @return <code>ListTransactionCommand</code>
     */
    @Override
    public ListTransactionsCommand parse(String userInputStr) {
        HashMap<String, String> argumentInfoMap;
        LocalDate localDate = LocalDate.now();
        if (userInputStr.isBlank()) {
            logger.log(Level.INFO, "the date is unspecified, list today's transactions instead");
            return new ListTransactionsCommand();
        }
        argumentInfoMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        for (String details : argumentInfoMap.keySet()) {
            if (details.equals("date")) {
                String dateArgStr = "";
                try {
                    dateArgStr = argumentInfoMap.get(details);
                    localDate = LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "the date cannot be parsed");
                    return new ListTransactionsCommand(false, "Unable to parse \"" + dateArgStr
                                                        + "\" as a date.");
                }
            }
        }
        logger.log(Level.INFO, "the date is specified, list that day's transactions");
        return new ListTransactionsCommand(localDate);
    }
}
