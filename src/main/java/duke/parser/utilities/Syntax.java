package duke.parser.utilities;

public class Syntax {

    // Used for command:addLocker
    public static final Token TOKEN_SERIAL = new Token("s/");
    public static final Token TOKEN_ADDRESS = new Token("a/");
    public static final Token TOKEN_ZONE = new Token("z/");

    //Used for command: addBatch
    public static final Token TOKEN_SIZE = new Token("u/");

    //Used for auto assigning lockers:assign
    public static final Token TOKEN_STUDENT_NAME = new Token("n/");
    public static final Token TOKEN_STUDENTID = new Token("m/");
    public static final Token TOKEN_EMAIL = new Token("e/");
    public static final Token TOKEN_STUDENT_COURSE = new Token("c/");
    public static final Token TOKEN_START_DATE = new Token("t/");
    public static final Token TOKEN_END_DATE = new Token("f/");
    public static final Token TOKEN_PREFERENCES = new Token("p/");

}
