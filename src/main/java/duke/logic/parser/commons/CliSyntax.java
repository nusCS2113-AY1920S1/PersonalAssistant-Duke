package duke.logic.parser.commons;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ORDER_ITEM = new Prefix("-item");
    public static final Prefix PREFIX_ORDER_REMARKS = new Prefix("-rmk");
    public static final Prefix PREFIX_ORDER_DEADLINE = new Prefix("-by");
    public static final Prefix PREFIX_ORDER_STATUS = new Prefix("-status");
    public static final Prefix PREFIX_ORDER_TOTAL = new Prefix("-total");
    public static final Prefix PREFIX_CUSTOMER_NAME = new Prefix("-name");
    public static final Prefix PREFIX_CUSTOMER_CONTACT = new Prefix("-contact");

    public static final Prefix PREFIX_SALE_DESCRIPTION = new Prefix("-desc");
    public static final Prefix PREFIX_SALE_VALUE = new Prefix("-val");
    public static final Prefix PREFIX_SALE_DATE = new Prefix("-at");
    public static final Prefix PREFIX_SALE_REMARKS = new Prefix("-rmk");
    public static final Prefix PREFIX_SALE_FILTER_START_DATE = new Prefix("-from");
    public static final Prefix PREFIX_SALE_FILTER_END_DATE = new Prefix("-to");

    public static final Prefix PREFIX_PRODUCT_NAME = new Prefix("-name");
    public static final Prefix PREFIX_PRODUCT_INGREDIENT = new Prefix("-ingt");
    public static final Prefix PREFIX_PRODUCT_INGREDIENT_COST = new Prefix("-cost");
    public static final Prefix PREFIX_PRODUCT_RETAIL_PRICE = new Prefix("-price");
    public static final Prefix PREFIX_PRODUCT_INDEX = new Prefix("-i");
    public static final Prefix PREFIX_PRODUCT_STATUS = new Prefix("-status");

    public static final Prefix PREFIX_INVENTORY_NAME = new Prefix("-name");
    public static final Prefix PREFIX_INVENTORY_QUANTITY = new Prefix("-qty");
    public static final Prefix PREFIX_INVENTORY_REMARKS = new Prefix("-rmk");
    public static final Prefix PREFIX_INVENTORY_INDEX = new Prefix("-i");

    public static final Prefix PREFIX_SHOPPING_NAME = new Prefix("-name");
    public static final Prefix PREFIX_SHOPPING_QUANTITY = new Prefix("-qty");
    public static final Prefix PREFIX_SHOPPING_REMARKS = new Prefix("-rmk");
    public static final Prefix PREFIX_SHOPPING_INDEX = new Prefix("-i");
    public static final Prefix PREFIX_SHOPPING_COST = new Prefix("-cost");
}
