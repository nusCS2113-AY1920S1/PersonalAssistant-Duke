package duke.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ORDER_ITEM = new Prefix("-item");
    public static final Prefix PREFIX_ORDER_NAME = new Prefix("-name");
    public static final Prefix PREFIX_ORDER_CONTACT = new Prefix("-contact");
    public static final Prefix PREFIX_ORDER_REMARKS = new Prefix("-rmk");
    public static final Prefix PREFIX_ORDER_DEADLINE = new Prefix("-by");
    public static final Prefix PREFIX_ORDER_STATUS = new Prefix("-status");
    public static final Prefix PREFIX_ORDER_INDEX = new Prefix("-i");

    public static final Prefix PREFIX_INVENTORY_QUANTITY = new Prefix("-qty");

    public static final Prefix PREFIX_RECIPE_NAME = new Prefix("-name");

}
