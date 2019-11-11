/**
 * The task contain all messages used when errors occur.
 *
 * @author tygq13
 */

package cube.util.exception;

public class UtilErrorMessage {
    public static final String READ_ERROR
            = "OOPS!!! IO Exception has been caught. Unable to read from ";
    public static final String WRITE_ERROR
            = "OOPS!!! IO Exception has been caught. Unable to write to ";
    public static final String CSV_MAPPER_ERROR
            = "OOPS!!! There is a problem importing your CSV file. Do check the format again!";
}