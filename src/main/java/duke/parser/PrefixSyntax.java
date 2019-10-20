package duke.parser;

public class PrefixSyntax {

    // Used for command:addLocker
    public static final Prefix PREFIX_SERIAL = new Prefix("s/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_ZONE = new Prefix("z/");

    //Used for command: addBatch
    public static final Prefix PREFIX_SIZE = new Prefix("u/");

}
