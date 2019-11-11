//@@author Dng132FEI
package mistermusik.commons.events.formatting;

/**
 * Model_Class.Predicate checks if an input satisfies a
 * certain predicate.
 */

//@@author
public class Predicate<T> {

    /**
     * compare_func codes.
     */
    private static final int EQUAL = 0;
    private static final int GREATER_THAN = 1;
    private static final int SMALLER_THAN = 2;

    /**
     * compare_type codes.
     */
    private static final int JAVA_DATE = 0;
    private static final int STRING = 1;

    /**
     * The item used as the reference to check if the predicate is true.
     */
    private T reference;

    /**
     * The function used for the following comparison: reference (compare_func) input.
     */
    private int compareFunc;

    /**
     * The type of variable that is used as the reference.
     */
    private int variableType;

    /**
     * Creates a new Model_Class.Predicate Object.
     *
     * @param ref  the reference used to check if the predicate is true
     * @param comp the comparator function used in the following comparison: reference (comp) input
     */
    public Predicate(T ref, int comp) {
        switch (comp) {
        case EQUAL:
            this.compareFunc = EQUAL;
            break;
        case GREATER_THAN:
            this.compareFunc = GREATER_THAN;
            break;
        case SMALLER_THAN:
            this.compareFunc = SMALLER_THAN;
            break;
        default:
            break;
        }
        this.reference = ref;
        if (this.reference instanceof EventDate) {
            this.variableType = JAVA_DATE;
        } else if (this.reference instanceof String) {
            this.variableType = STRING;
        }
    }

    /**
     * Compares the input date against the reference to check if the predicate is true.
     */
    protected boolean compare_dates(T input) {
        EventDate x = (EventDate) reference;
        EventDate y = (EventDate) input;
        switch (this.compareFunc) {
        case EQUAL:
            return x.compare(y) == 0;
        case GREATER_THAN:
            return x.compare(y) == 1;
        case SMALLER_THAN:
            return x.compare(y) == -1;
        default:
            break;
        }
        return false;
    }

    /**
     * Compares the input string against the reference to check if the predicate is true.
     */
    protected boolean compare_str(T input) {
        String x = (String) reference;
        String y = (String) input;
        switch (this.compareFunc) {
        case EQUAL:
            return x.compareTo(y) == 0;
        case GREATER_THAN:
            return x.compareTo(y) > 0;
        case SMALLER_THAN:
            return x.compareTo(y) < 0;
        default:
            break;
        }
        return false;
    }

    /**
     * Checks if the input satisfies the predicate. This function
     * will direct to the correct protected comparator functions
     * based on the type of the reference.
     */
    public boolean check(T item) {
        switch (this.variableType) {
        case JAVA_DATE:
            return compare_dates(item);
        case STRING:
            return compare_str(item);
        default:
            break;
        }
        return false;
    }
}

