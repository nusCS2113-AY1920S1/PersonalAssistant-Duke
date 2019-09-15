package Model_Class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model_Class.DateObj object stores the input date and time as a java object.
 */

public class DateObj {
	
    /**
     * The dateobj variable stores the date and time as a java Date object
     * if the input is in the format dd/mm/yyyy HHmm (24-hr clock) or 
     * dd/mm/yyyy.
     */
	protected Date dateobj;
	
	/**
	 * The java date variable stores the date and time as a string if the input is 
	 * not in either of the two formats defined for the dateobj variable.
	 */
    protected String date;
    
    /**
     * Stores the format type of the date input.
     * If the input is in the format dd/mm/yyyy HHmm (24-hr clock),
     * set format = 1 and store the input in dateobj as a Date object.
     * If the input is in the format dd/mm/yyyy,
     * set format = 2 and store the input in dateobj as a Date object.
     * Otherwise, 
     * set format = 3 and store the input as a Date object.
     */
    protected int format;

    /**
     * Creates a custom "date object".
     * It processes the input according to the description stated above.
     * @param inputDate the input keyed in for the date.
     */
    public DateObj(String inputDate){
    	try {
    	    SimpleDateFormat inputformat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    	    inputformat.setLenient(false);
    	    Date dateobj = inputformat.parse(inputDate);
    	    this.dateobj = dateobj;
    	    format = 1; // date and time
    	} catch (ParseException pe) {
    		try {
	    		SimpleDateFormat inputformat = new SimpleDateFormat("dd/MM/yyyy");
	    	    inputformat.setLenient(false);
	    	    Date dateobj = inputformat.parse(inputDate);
	    	    this.dateobj = dateobj;
	    	    format = 2; // only date
    		} catch (ParseException pe2) {
        	    format = 3; // other types; store as string
        	    this.date = inputDate;
    		}
    	}
    }

    /**
     * Converts deadline type task to string format for printing.
     * @return Formatted string representing the deadline and its date.
     */
	public String toOutputString(){
    	if (format == 1) {
    		SimpleDateFormat outputformat = new SimpleDateFormat("dd MMM yyyy, hh:mm aa");
    	 	return outputformat.format(dateobj);
    	} else if (format == 2) {
    		SimpleDateFormat outputformat = new SimpleDateFormat("dd MMM yyyy");
    	 	String out = outputformat.format(dateobj);
    	 	return outputformat.format(dateobj);
    	} else {
    		return date;
    	}
    }
}
