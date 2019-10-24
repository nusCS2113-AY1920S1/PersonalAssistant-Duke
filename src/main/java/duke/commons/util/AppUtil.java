package duke.commons.util;

/**
 * A container for app specific utility functions. Adapted from AddressBook 4.
 */
public class AppUtil {
    private static final String VALIDATION_NON_EMPTY_REGEX = "[^\\s].*";

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkEmpty(String test, String errorMessage) {
        checkArgument(test.matches(VALIDATION_NON_EMPTY_REGEX), errorMessage);
    }

    public static void checkNegativeDouble(Double number, String errorMessage) {
        if (number < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
