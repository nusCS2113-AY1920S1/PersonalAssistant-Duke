package duke.logic.command.order;

import duke.logic.parser.commons.TimeParser;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.order.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_CONTACT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_DEADLINE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_ITEM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_STATUS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_TOTAL;

/**
 * Contains helper methods for testing order commands.
 */
public class OrderCommandTestUtil {
    public static final String VALID_NAME_ALICE = "Alice Wang";
    public static final String VALID_NAME_RORY = "Rory Liu";
    public static final String VALID_CONTACT_ALICE = "Alice@gmail.com";
    public static final String VALID_CONTACT_RORY = "12345678";
    public static final String VALID_REMARKS_ALICE = "No nuts.";
    public static final String VALID_REMARKS_RORY = "no";
    public static final double VALID_TOTAL_ALICE = 50.0;
    public static final double VALID_TOTAL_RORY = 60.0;
    public static final Order.Status VALID_STATUS_ALICE = Order.Status.ACTIVE;
    public static final Order.Status VALID_STATUS_RORY = Order.Status.ACTIVE;
    public static final Date VALID_DELIVERY_DATE_ALICE = new Date(2019, Calendar.NOVEMBER, 10, 18, 0);
    public static final Date VALID_DELIVERY_DATE_RORY = new Date(2018, Calendar.DECEMBER, 11, 18, 0);
    public static final Set<Item<String>> VALID_ITEMS_ALICE = new HashSet<>() {{
        add(new Item<>("Cake", new Quantity(5)));
    }};
    public static final Set<Item<String>> VALID_ITEMS_RORY = new HashSet<>() {{
        add(new Item<>("Cake", new Quantity(10)));
        add(new Item<>("Bread", new Quantity(5)));
    }};

    public static final String DESC_NAME_ALICE = " " + PREFIX_CUSTOMER_NAME + " " + VALID_NAME_ALICE;
    public static final String DESC_NAME_RORY = " " + PREFIX_CUSTOMER_NAME + " " + VALID_NAME_RORY;
    public static final String DESC_CONTACT_ALICE = " " + PREFIX_CUSTOMER_CONTACT + " " + VALID_CONTACT_ALICE;
    public static final String DESC_CONTACT_RORY = " " + PREFIX_CUSTOMER_CONTACT + " " + VALID_CONTACT_RORY;

    public static final String DESC_RMK_ALICE = " " + PREFIX_ORDER_REMARKS + " " + VALID_REMARKS_ALICE;
    public static final String DESC_RMK_RORY = " " + PREFIX_ORDER_REMARKS + " " + VALID_REMARKS_RORY;

    public static final String DESC_TOTAL_ALICE = " " + PREFIX_ORDER_TOTAL + " " + VALID_TOTAL_ALICE;
    public static final String DESC_TOTAL_RORY = " " + PREFIX_ORDER_TOTAL + " " + VALID_TOTAL_RORY;

    public static final String DESC_STATUS_ALICE = " " + PREFIX_ORDER_STATUS + " " + VALID_STATUS_ALICE;
    public static final String DESC_STATUS_RORY = " " + PREFIX_ORDER_STATUS + " " + VALID_STATUS_RORY;

    public static final String DESC_DATE_ALICE = " " + PREFIX_ORDER_DEADLINE + " " + convertToValidDateString(VALID_DELIVERY_DATE_ALICE);
    public static final String DESC_DATE_RPRY = " " + PREFIX_ORDER_DEADLINE + " " + convertToValidDateString(VALID_DELIVERY_DATE_RORY);

    public static final String DESC_ITEMS_ALICE = convertToValidItemsString(VALID_ITEMS_ALICE);
    public static final String DESC_ITEMS_RORY = convertToValidItemsString(VALID_ITEMS_RORY);

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    private static String convertToValidDateString(Date date) {
        return TimeParser.inputDateFormat.format(date);
    }

    private static String convertToValidItemsString(Set<Item<String>> items) {
        StringBuilder builder = new StringBuilder();
        for (Item<String> item : items) {
            builder.append(" ");
            builder.append(PREFIX_ORDER_ITEM);
            builder.append(" ");
            builder.append(item.getItem());
            builder.append(", ");
            builder.append(item.getQuantity().getNumber());
        }
        return builder.toString();
    }
}
