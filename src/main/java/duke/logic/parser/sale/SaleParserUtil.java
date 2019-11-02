package duke.logic.parser.sale;

import duke.commons.core.Message;
import duke.logic.command.sale.SaleDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.TimeParser;
import duke.logic.parser.exceptions.ParseException;
import javafx.util.Pair;

import java.util.Date;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DATE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DESCRIPTION;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_FROM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_IS_SPEND;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_TO;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_VALUE;

/**
 * A utility class for sale parser.
 */
class SaleParserUtil {
    private static final int MAX_DESC_LENGTH = 50;
    private static final double MAX_VAL = 50000.0;
    private static final int MAX_REMARKS_LENGTH = 50;
    private static final String MESSAGE_DESC_EXCEED_LIMIT = "Description should be no more than "
            + MAX_DESC_LENGTH + " characters.";
    private static final String MESSAGE_VAL_EXCEED_LIMIT = "Numbers should be a positive double no more than "
            + MAX_VAL;
    private static final String MESSAGE_TRUTH_VALUE_INVALID = "-spend should take an argument either true or false ";
    private static final String MESSAGE_REMARKS_EXCEED_LIMIT = "Remarks should be no more than "
            + MAX_REMARKS_LENGTH + " characters.";

    /**
     * Returns an {@code SaleDescriptor} from the given {@code ArgumentMultimap}.
     */
    static SaleDescriptor createDescriptor(ArgumentMultimap map) {
        SaleDescriptor descriptor = new SaleDescriptor();

        if (map.getValue(PREFIX_SALE_DESCRIPTION).isPresent()) {
            String value = map.getValue(PREFIX_SALE_DESCRIPTION).get();
            checkStringLength(value, MAX_DESC_LENGTH, MESSAGE_DESC_EXCEED_LIMIT);
            descriptor.setDescription(map.getValue(PREFIX_SALE_DESCRIPTION).get());
        }
        if (map.getValue(PREFIX_SALE_VALUE).isPresent()) {
            descriptor.setValue(parseValue(map.getValue(PREFIX_SALE_VALUE).get()));
        }
        if (map.getValue(PREFIX_SALE_IS_SPEND).isPresent()) {
            String value = map.getValue(PREFIX_SALE_IS_SPEND).get();
            checkBoolean(value, MESSAGE_TRUTH_VALUE_INVALID);
            descriptor.setSpend(Boolean.parseBoolean(map.getValue(PREFIX_SALE_IS_SPEND).get()));
        }
        if (map.getValue(PREFIX_SALE_DATE).isPresent()) {
            descriptor.setSaleDate(TimeParser.convertStringToDate(
                    map.getValue(PREFIX_SALE_DATE).get()));
        }
        if (map.getValue(PREFIX_SALE_REMARKS).isPresent()) {
            String value = map.getValue(PREFIX_SALE_REMARKS).get();
            checkStringLength(value, MAX_REMARKS_LENGTH, MESSAGE_REMARKS_EXCEED_LIMIT);
            descriptor.setRemarks(value);
        }
        return descriptor;
    }

    /**
     * Checks if a {@code toCheck}'s length is no more than {@code max}.
     * @throws ParseException if the string's is more than {@code max}
     */
    private static void checkStringLength(String toCheck, int max, String message) throws ParseException {
        if (toCheck.length() > max) {
            throw new ParseException(message);
        }
    }

    private static double parseValue(String totalString) throws ParseException {
        try {
            double result = Double.parseDouble(totalString);
            checkNumber(result);
            return result;
        } catch (NumberFormatException e) {
            throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    /**
     * Checks if number is within limit.
     * @throws ParseException if number is greater than {@code MAX_NUMBER} or smaller than zero.
     */
    private static void checkNumber(double toCheck) throws ParseException {
        if (toCheck < 0 || toCheck > MAX_VAL) {
            throw new ParseException(MESSAGE_VAL_EXCEED_LIMIT);
        }
    }

    /**
     * Checks if string really has boolean.
     * @throws ParseException if string is not "true" or "false".
     */
    private static void checkBoolean(String truth, String message) throws ParseException {
        if (truth.equals("true") || truth.equals("false")) {
            return;
        } else {
            throw new ParseException(message);
        }
    }


    public static Pair<Date, Date> createFilterDate(ArgumentMultimap map) {
        Date from = null;
        Date to = null;

        if (map.getValue(PREFIX_SALE_FROM).isPresent()) {
            from = TimeParser.convertStringToDate(map.getValue(PREFIX_SALE_FROM).get());
        }
        if (map.getValue(PREFIX_SALE_TO).isPresent()) {
            to = TimeParser.convertStringToDate(map.getValue(PREFIX_SALE_TO).get());
        }

        return new Pair(from, to);
    }

}