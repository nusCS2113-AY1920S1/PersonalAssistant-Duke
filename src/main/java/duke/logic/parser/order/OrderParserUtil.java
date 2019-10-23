package duke.logic.parser.order;

import duke.commons.core.Message;
import duke.logic.command.order.OrderDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.TimeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.order.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_CONTACT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_DEADLINE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_ITEM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_STATUS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_TOTAL;

class OrderParserUtil {
    private static final double MAX_NUMBER = 5000.0;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_CONTACT_LENGTH = 20;
    private static final int MAX_REMARKS_LENGTH = 50;
    private static final String MESSAGE_NUMBER_EXCEED_LIMIT = "Numbers should be a positive double no more than "
                                                                + MAX_NUMBER;
    private static final String MESSAGE_NAME_EXCEED_LIMIT = "Name should be less than "
                                                            + MAX_NAME_LENGTH + " characters.";
    private static final String MESSAGE_CONTACT_EXCEED_LIMIT = "Contact should be less than "
                                                                + MAX_CONTACT_LENGTH + " characters.";
    private static final String MESSAGE_REMARKS_EXCEED_LIMIT = "Remarks should be less than "
                                                                + MAX_REMARKS_LENGTH + " characters.";

    static OrderDescriptor createDescriptor(ArgumentMultimap map) {
        OrderDescriptor descriptor = new OrderDescriptor();

        if (map.getValue(PREFIX_CUSTOMER_NAME).isPresent()) {
            String value = map.getValue(PREFIX_CUSTOMER_NAME).get();
            checkStringLength(value, MAX_NAME_LENGTH, MESSAGE_NAME_EXCEED_LIMIT);
            descriptor.setCustomerName(value);
        }
        if (map.getValue(PREFIX_CUSTOMER_CONTACT).isPresent()) {
            String value = map.getValue(PREFIX_CUSTOMER_CONTACT).get();
            checkStringLength(value, MAX_CONTACT_LENGTH, MESSAGE_CONTACT_EXCEED_LIMIT);
            descriptor.setCustomerName(value);
        }
        if (map.getValue(PREFIX_ORDER_DEADLINE).isPresent()) {
            descriptor.setDeliveryDate(TimeParser.convertStringToDate(
                    map.getValue(PREFIX_ORDER_DEADLINE).get()));
        }
        if (map.getValue(PREFIX_ORDER_REMARKS).isPresent()) {
            String value = map.getValue(PREFIX_ORDER_REMARKS).get();
            checkStringLength(value, MAX_REMARKS_LENGTH, MESSAGE_REMARKS_EXCEED_LIMIT);
            descriptor.setRemarks(value);
        }
        if (map.getValue(PREFIX_ORDER_ITEM).isPresent()) {
            descriptor.setItems(parseItems(map.getAllValues(PREFIX_ORDER_ITEM)));
        }
        if (map.getValue(PREFIX_ORDER_STATUS).isPresent()) {
            descriptor.setStatus(parseStatus(map.getValue(PREFIX_ORDER_STATUS).get()));
        }
        if (map.getValue(PREFIX_ORDER_TOTAL).isPresent()) {
            descriptor.setTotal(parseTotal(map.getValue(PREFIX_ORDER_TOTAL).get()));
        }
        return descriptor;
    }

    private static Set<Item<String>> parseItems(List<String> itemArg) throws ParseException {
        Set<Item<String>> items = new HashSet<>();
        for (String itemString : itemArg) {
            String[] itemAndQty = itemString.split(",");
            if (itemAndQty.length < 2) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }
            if (itemAndQty[0].strip().equals("") || itemAndQty[1].strip().equals("")) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }

            try {
                double amount = Double.parseDouble(itemAndQty[1].strip());
                checkNumber(amount);

                Item<String> item = new Item<>(itemAndQty[0].strip(),
                        new Quantity(amount));
                items.add(item);
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
        return items;
    }


    private static Order.Status parseStatus(String statusString) throws ParseException {
        try {
            return Order.Status.valueOf(statusString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Message.MESSAGE_INVALID_STATUS);
        }
    }

    private static double parseTotal(String totalString) throws ParseException {
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
        if (toCheck < 0 || toCheck > MAX_NUMBER) {
            throw new ParseException(MESSAGE_NUMBER_EXCEED_LIMIT);
        }
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
}
