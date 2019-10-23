package duke.logic.parser.commons;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.commons.util.StringUtil;
import duke.logic.parser.exceptions.ParseException;
import duke.model.product.Product;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * TODO: Fill in details.
 */
public class ParserUtil {

    private static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String SEPARATOR_INDEX_INTERVAL = "~";
    private static final String SEPARATOR_INDEX_MULTIPLE = ",";

    ///Common utilities.

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses the string argument as a {@code Status}.
     * The string is case-insensitive.
     *
     * @throws ParseException if the string cannot be converted
     */
    public static Product.Status parseProductStatus(String statusString) throws ParseException {
        try {
            return Product.Status.valueOf(statusString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Message.MESSAGE_INVALID_STATUS);
        }
    }

    /**
     * Returns a set of unique {@code Index} parsed from user input.
     * The input string can be a) a single index e.g. 1;
     * or b) some indices separated by commas, e.g. 1, 2, 4;
     * or c) an interval of index e.g. 1~4.
     *
     * @throws ParseException if user input is not in the specified format
     */
    public static Set<Index> getIndices(String indexString) throws ParseException {
        Set<Index> indices;
        if (indexString.contains("~")) {
            indices = getIndicesInInterval(indexString);
        } else {
            indices = getIndicesFromString(indexString);
        }
        return indices;
    }

    private static Set<Index> getIndicesInInterval(String interval) throws ParseException {
        String[] startAndEndIndices = interval.split(SEPARATOR_INDEX_INTERVAL);
        int start;
        int end;
        try {
            start = Integer.parseInt(startAndEndIndices[0].strip());
            end = Integer.parseInt(startAndEndIndices[1].strip());
        } catch (NumberFormatException e) {
            throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
        }
        if (start > end) {
            throw new ParseException(Message.MESSAGE_INVALID_RANGE);
        }
        Set<Index> result = new HashSet<>();
        for (int i = start; i <= end; i++) {
            result.add(Index.fromOneBased(i));
        }
        return result;
    }

    private static Set<Index> getIndicesFromString(String string) throws ParseException {
        String[] indexStrings = string.split(SEPARATOR_INDEX_MULTIPLE);
        Set<Index> result = new HashSet<>();
        for (String indexString : indexStrings) {
            try {
                result.add(Index.fromOneBased(Integer.parseInt(indexString.strip())));
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
        return result;
    }
}
