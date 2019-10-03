package Events.Formatting;

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

    protected String formattedDateString;

    /**
     * Creates a custom "date object".
     * If no parameters are passed in, a DateObj with the current date and time is created.
     * The output will include both the date and time.
     */
    public DateObj(String splitDate) {
        this.splitDate = splitDate;
        formatDate();
    }

    public void formatDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
            dateFormat.setLenient(false);
            this.dateObject = dateFormat.parse(splitDate);
            this.formattedDateString = formatter.format(dateObject);
        } catch (ParseException pe) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
                dateFormat.setLenient(false);
                this.dateObject = dateFormat.parse(splitDate);
                this.formattedDateString = formatter.format(dateObject);
            } catch (ParseException pe2) {
                this.formattedDateString = splitDate;
            }
        }
    }

    public void formatToInputPattern() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
            dateFormat.setLenient(false);
            this.dateObject = dateFormat.parse(splitDate);
            this.formattedDateString = formatter.format(dateObject);
        } catch (ParseException pe) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
                dateFormat.setLenient(false);
                this.dateObject = dateFormat.parse(splitDate);
                this.formattedDateString = formatter.format(dateObject);
            } catch (ParseException pe2) {
                this.formattedDateString = splitDate;
            }
        }
    }

    /** Getter to obtain the stored built-in Java date object.
     * @return the Java date object stored in the DateObj.
     */
    public Date getCurrentJavaDate() {
        dateObject = new Date();
        return dateObject;
    }

    public String getSplitDate() {
        return splitDate;
    }

    public String getFormattedDateString() {
        return this.formattedDateString;
    }

    public Date getEventJavaDate() {
        return this.dateObject;
    }

    public String formatToString(Date dateToBeFormatted) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
        return formatter.format(dateToBeFormatted);
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
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, noOfDays);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        this.dateObject = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.splitDate = formatter.format(dateObject);
    }
  }
