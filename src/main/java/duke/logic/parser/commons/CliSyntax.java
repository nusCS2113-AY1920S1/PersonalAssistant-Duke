package duke.logic.parser.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {
    /* Command keywords */
    public static final Set<String> KEYWORDS = new HashSet<>(Arrays
            .asList("order", "add", "remove", "edit", "short"));

    /* Prefix definitions */
    public static final Prefix PREFIX_ORDER_ITEM = new Prefix("-item");
    public static final Prefix PREFIX_CUSTOMER_NAME = new Prefix("-name");
    public static final Prefix PREFIX_CUSTOMER_CONTACT = new Prefix("-contact");
    public static final Prefix PREFIX_ORDER_REMARKS = new Prefix("-rmk");
    public static final Prefix PREFIX_ORDER_DEADLINE = new Prefix("-by");
    public static final Prefix PREFIX_ORDER_STATUS = new Prefix("-status");
    public static final Prefix PREFIX_ORDER_INDEX = new Prefix("-i");

    public static final Prefix PREFIX_SALE_DESCRIPTION = new Prefix("-desc");
    public static final Prefix PREFIX_SALE_VALUE = new Prefix("-val");
    public static final Prefix PREFIX_SALE_DATE = new Prefix("-at");
    public static final Prefix PREFIX_SALE_REMARKS = new Prefix("-rmk");

    public static final Prefix PREFIX_PRODUCT_NAME = new Prefix("-name");
    public static final Prefix PREFIX_PRODUCT_INGREDIENT = new Prefix("-ingt");
    public static final Prefix PREFIX_PRODUCT_COST = new Prefix("-cost");
    public static final Prefix PREFIX_PRODUCT_PRICE = new Prefix("-price");
    public static final Prefix PREFIX_PRODUCT_INDEX = new Prefix("-i");
    public static final Prefix PREFIX_PRODUCT_STATUS = new Prefix("-status");

    public static final Prefix PREFIX_INVENTORY_NAME = new Prefix("-ingt");
    public static final Prefix PREFIX_INVENTORY_QUANTITY = new Prefix("-qty");
    public static final Prefix PREFIX_INVENTORY_UNIT = new Prefix("-unit");
    public static final Prefix PREFIX_INVENTORY_INDEX = new Prefix("-i");

    public static ArrayList<String> getAllCliSyntax() {
        ArrayList<String> allSyntax = new ArrayList<>();
        allSyntax.add("product");
        allSyntax.add("order");
        allSyntax.add("sale");
        allSyntax.add("ingredient");
        allSyntax.add("name");
        allSyntax.add("ingt");
        allSyntax.add("price");
        allSyntax.add("cost");
        allSyntax.add("add");
        allSyntax.add("edit");
        return allSyntax;
    }
}
