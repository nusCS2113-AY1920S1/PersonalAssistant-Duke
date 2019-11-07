package cube.logic.parser.exception;

public class ParserErrorMessage {
    public static final String NOT_ENOUGH_PARAMETER
        = "OOPS!!! The parameter you input is not enough";
    public static final String INVALID_DATE_FORMAT
        = "OOPS!!! The date format is invalid. Please specify date in 'dd/mm/yyyy'";
    public static final String INVALID_COMMAND
        = "OOPS!!! The command is invalid. Enter 'help' to view the list of command";
    public static final String INVALID_PARAMETER
        = "OOPS!!! Your input contains invalid parameter.";
    public static final String REPETITIVE_PARAMETER
        = "OOPS!!! Your input contains repetitive parameter";
    public static final String EMPTY_FIELD
        = "OOPS!!! Your input after a parameter is empty.";
    public static final String INVALID_NAME
        = "OOPS!!! The name should only contains alphanumeric characters and spaces, and cannot be blank or start with '-'";
    public static final String INVALID_NUMBER
        = "OOPS!!! The number inside input should only be non-negative numerical and less than 10000.00.";
    public static final String INVALID_SORT_TYPE
        = "OOPS!!! The sort type can only be expiry/name/stock.";
    public static final String INVALID_PARAM_RANGE
        = "OOPS!!! The input value range for %1$s parameter should be between %2$s and %3$s.";
}
