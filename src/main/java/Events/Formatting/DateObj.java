package Events.Formatting;

import UserElements.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Model_Class.DateObj object stores the input date and time as a java object.
 */

public class DateObj {

    protected String splitDate;

    protected Date dateObject;

    protected UI ui;

    /**
     * Stores the format type of the date input.
     */
    protected int format;

    protected static int DATE_AND_TIME = 1;
    protected static int DATE = 2;

    /**
     * Creates a custom "date object".
     * If no parameters are passed in, a DateObj with the current date and time is created.
     * The output will include both the date and time.
     */
    public DateObj(String splitDate) {
    	this.splitDate = splitDate;
    	this.dateObject = new Date();
    	this.format = 0;
    	formatDate();
    }

    public String formatDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
            dateFormat.setLenient(false);
            Date taskJavaDate = dateFormat.parse(splitDate);
            format = DATE_AND_TIME;
            return taskJavaDate.toString();
        } catch (ParseException pe) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);
                Date taskJavaDateNoTime = dateFormat.parse(splitDate);
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
                String taskDateFormatNoTime = formatter.format(taskJavaDateNoTime);
                format = DATE;
                return taskDateFormatNoTime;
            } catch (ParseException pe2) {
                return splitDate;
            }
        }
    }

    public int getFormat() {
        return format;
    }

    /** Getter to obtain the stored built-in Java date object.
     * @return the Java date object stored in the DateObj.
     */
    public Date getCurrentJavaDate() {
        return dateObject;
    }

    public String getSplitDate() {
        return splitDate;
    }

    public Date getEventJavaDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
            dateFormat.setLenient(false);
            dateObject = dateFormat.parse(splitDate);
            format = DATE_AND_TIME;
            return dateObject;
        } catch (ParseException pe) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);
                dateObject = dateFormat.parse(splitDate);
                format = DATE;
                return dateObject;
            } catch (ParseException pe2) {
                return dateObject;
            }
        }
    }
    
    /**
     * Compares this dateObj with another input dateObj
     * If this == other, return 0.
     * If this < other, return -1.
     * If this > other, return 1.
     * If the two DateObjs cannot be compared as either one of
     * them stores the date as a string, return 2. 
     * @param other the input dateObj used for the comparison
     * @return Output the result of the comparison according to the algorithm stated above. 
     */
    public int compare(DateObj other) {
    	if (dateObject == null || other.getEventJavaDate() == null) {
    		return 2;
    	} else {
    		Date otherDate = other.getEventJavaDate();
    		if (dateObject.compareTo(otherDate) > 0) {
    			return 1;
    		} else if (dateObject.compareTo(otherDate) == 0) {
    			return 0;
    		} else if (dateObject.compareTo(otherDate) < 0) {
    			return -1;
    		}
    	}
    	return 2;
    }
    
    /**
     * Adds n days to the javaDate object.
     * @param noOfDays numbers of days to add
     */
    public void addDaysAndSetMidnight(int noOfDays) {
    	if (dateObject != null) {
    		Calendar c = Calendar.getInstance();
        	c.add(Calendar.DATE, noOfDays);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            dateObject = c.getTime();
    	}
    }
  }
