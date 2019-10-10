package duke.logic.parser.commons;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.commons.util.StringUtil;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Item;
import duke.model.order.Quantity;
import duke.model.product.Product;
import duke.model.order.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * TODO: Fill in details.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    ///Common utilities.
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //=======Order utilities========

    public static Set<Item<Product>> parseItems(List<String> itemArg) throws ParseException {
        Set<Item<Product>> items = new HashSet<>();
        for (String itemString : itemArg) {
            String[] itemAndQty = itemString.split(",");
            if (itemAndQty.length < 2) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }
            if (itemAndQty[0].strip().equals("") || itemAndQty[1].strip().equals("")) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }

            try {
                Item<Product> item = new Item<>(new Product(itemAndQty[0].strip()),
                        new Quantity(Integer.parseInt(itemAndQty[1].strip())));
                items.add(item);
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
        return items;
    }

    public static Order.Status parseStatus(String statusString) throws ParseException {
        try {
            return Order.Status.valueOf(statusString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Message.MESSAGE_INVALID_STATUS);
        }
    }

    public static Product.Status parseProductStatus(String statusString) throws ParseException {
        try {
            return Product.Status.valueOf(statusString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Message.MESSAGE_INVALID_STATUS);
        }
    }

    public static List<Integer> getIndexes(String indexString) throws ParseException {
        List<Integer> indexes = new ArrayList<>();
        if (indexString.contains("~")) {
            indexes = getIndexesInInterval(indexString);
        } else {
            indexes = getIndexesFromString(indexString);
        }
        return indexes;
    }

    private static List<Integer> getIndexesInInterval(String interval) throws ParseException {
        String[] startAndEndIndexes = interval.split("~");
        int start;
        int end;
        try {
            start = Integer.parseInt(startAndEndIndexes[0]) - 1;
            end = Integer.parseInt(startAndEndIndexes[1]) - 1;
        } catch (NumberFormatException e) {
            throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
        }
        if (start > end) {
            throw new ParseException(Message.MESSAGE_INVALID_RANGE);
        }
        List<Integer> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(i);
        }
        return result;
    }

    private static List<Integer> getIndexesFromString(String string) throws ParseException {
        String[] indexStrings = string.split(",");
        List<Integer> result = new ArrayList<>();
        for (String indexString : indexStrings) {
            try {
                result.add(Integer.parseInt(indexString) - 1);
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
        return result;
    }
}
