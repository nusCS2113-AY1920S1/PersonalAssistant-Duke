package Events.Formatting;

/**
 * Model_Class.Predicate checks if an input satisfies a
 * certain predicate.
 */

public class Predicate<T> {
	
	/**
	 * compare_func codes
	 */
	static final int EQUAL = 0;
	static final int GREATER_THAN = 1;
	static final int SMALLER_THAN = 2;
	
	/**
	 * compare_type codes
	 */
	static final int JAVA_DATE = 0;
	static final int STRING = 1;
	
    /**
     * The item used as the reference to check if the predicate is true
     */
	private T reference;
	
	/**
	 * The function used for the following comparison: reference (compare_func) input
	 */
	private int compare_func;
	
	/**
	 * The type of variable that is used as the reference
	 */
	private int variable_type;
	
	/**
	 * Creates a new Model_Class.Predicate Object.
	 * @param ref the reference used to check if the predicate is true
	 * @param comp the comparator function used in the following comparison: reference (comp) input
	 */
	public Predicate(T ref, int comp) {
		switch (comp) {
		    case EQUAL:
		        this.compare_func = EQUAL;
		        break;
		    case GREATER_THAN:
		        this.compare_func = GREATER_THAN;
		        break;
		    case SMALLER_THAN:
		        this.compare_func = SMALLER_THAN;
		        break;
		}
		this.reference = ref;
		if (this.reference instanceof EventDate) {
			this.variable_type = JAVA_DATE;
		} else if (this.reference instanceof String) {
			this.variable_type = STRING;
		}
	}
	
	/**
	 * Compares the input date against the reference to check if the predicate is true.
	 */
	protected boolean compare_dates(T input) {
		EventDate x = (EventDate) reference;
		EventDate y = (EventDate) input;
		switch (this.compare_func) {
		    case EQUAL:
		    	return x.compare(y) == 0;
		    case GREATER_THAN:
		    	return x.compare(y) == 1;
		    case SMALLER_THAN:
		    	return x.compare(y) == -1;		    	
		}
		return false;
	}
	
	/**
	 * Compares the input string against the reference to check if the predicate is true.
	 */
	protected boolean compare_str(T input) {
		String x = (String) reference;
		String y = (String) input;
		switch (this.compare_func) {
		    case EQUAL:
		    	return x.compareTo(y) == 0;
		    case GREATER_THAN:
		    	return x.compareTo(y) > 0;
		    case SMALLER_THAN:
		    	return x.compareTo(y) < 0;		    	
		}
		return false;
	}
	
	/**
	 * Checks if the input satisfies the predicate. This function
	 * will direct to the correct protected comparator functions 
	 * based on the type of the reference. 
	 */
	public boolean check(T item) {
		switch (this.variable_type) {
		    case JAVA_DATE:
		    	return compare_dates(item);
		    case STRING:
		    	return compare_str(item);
		}
		return false;
	}
   
}
