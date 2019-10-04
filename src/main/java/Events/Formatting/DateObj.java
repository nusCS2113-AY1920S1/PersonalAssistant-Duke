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

    protected String userInputDateString;

    protected Date dateObject;

    protected String formattedDateString;

    protected int STRING_TO_DATE = 1;

    protected int DATE_TO_STRING = 2;

    /**
     * Creates a custom "date object" for string to date.
     */
    public DateObj(String userInputDateString) {
    	this.userInputDateString = userInputDateString;
    	formatDate(STRING_TO_DATE);
    }

    /**
     * Creates a custom "date object" for date to string (used for recurring events).
     */
    public DateObj(Date dateClass) {
        this.dateObject = dateClass;
        formatDate(DATE_TO_STRING);
    }

    /**
     * Takes in an identifier and performs the corresponding actions.
     * @param identifier
     */
    private void formatDate(int identifier) {
        if (identifier == STRING_TO_DATE) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
                dateFormat.setLenient(false);
                this.dateObject = dateFormat.parse(userInputDateString);
                this.formattedDateString = formatter.format(dateObject);
            } catch (ParseException pe) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
                    dateFormat.setLenient(false);
                    this.dateObject = dateFormat.parse(userInputDateString);
                    this.formattedDateString = formatter.format(dateObject);
                } catch (ParseException pe2) {
                    this.formattedDateString = userInputDateString;
                }
            }
        } else if (identifier == DATE_TO_STRING) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
            this.formattedDateString = formatter.format(dateObject);
            formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
            this.userInputDateString = formatter.format(dateObject);
        }
    }

//    public void formatToInputPattern() {
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
//            dateFormat.setLenient(false);
//            this.dateObject = dateFormat.parse(splitDate);
//            this.formattedDateString = formatter.format(dateObject);
//        } catch (ParseException pe) {
//            try {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
//                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
//                dateFormat.setLenient(false);
//                this.dateObject = dateFormat.parse(splitDate);
//                this.formattedDateString = formatter.format(dateObject);
//            } catch (ParseException pe2) {
//                this.formattedDateString = splitDate;
//            }
//        }
//    }

    /** Getter to obtain the stored built-in Java date object.
     * @return the Java date object stored in the DateObj.
     */
    public Date getCurrentJavaDate() {
        dateObject = new Date();
        return dateObject;
    }

    public String getUserInputDateString() {
        return userInputDateString;
    }

    public String getFormattedDateString() {
        return this.formattedDateString;
    }

    public Date getEventJavaDate() {
        return this.dateObject;
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
        this.userInputDateString = formatter.format(dateObject);
        formatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
        this.formattedDateString = formatter.format(dateObject);
    }
  }
