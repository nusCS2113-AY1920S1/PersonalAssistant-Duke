package duke.logic.Parser;

import duke.exception.DukeException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses the command line from user input to tokens and
 * packages the tokens to {@code Command} object.
 */
public class Parser {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    /**
     * Converts a LocalDateTime to a user readable string.
     *
     * @param localDateTime LocalDateTime object that we wish to convert
     * @return String that is a formatted date and time
     */
    public static String formatTime(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Converts a {@code String} to a {@code LocalDateTime}.
     *
     * @param string {@code String} to convert.
     * @return {@code LocalDateTime} corresponding to the string.
     * @throws DukeException if the string cannot be parsed into a {@code LocalDateTime} object.
     */
    public static LocalDateTime parseTime(String string) throws DukeException {
        try {
            return LocalDateTime.parse(string, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_PARSER_TIME_INVALID, string));
        }
    }

    /**
     * Returns a formatted BigDecimal representing Money.
     * @param string String we want to format
     * @return a formatted BigDecimal representing Money.
     * @throws DukeException
     */
    public static BigDecimal parseMoney(String string) throws DukeException {
        try {
            double amountDouble = Double.parseDouble(string);
            BigDecimal amount = BigDecimal.valueOf(amountDouble);
            BigDecimal scaledAmount = amount.setScale(2, RoundingMode.HALF_EVEN);
            return scaledAmount;
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_EXPENSE_AMOUNT_INVALID, string));
        }

    }
}

