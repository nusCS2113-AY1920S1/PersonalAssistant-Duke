package javacake.tasks;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import javacake.exceptions.CakeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deadline extends Task {
    private String by;
    private Date dateNow;

    /**
     * Initialises the description of the task.
     * @param description String containing description
     *                    of the task inputted by user
     * @param by The details of when task is to be done
     */
    public Deadline(String description, String by) throws CakeException {
        super(description);
        taskType = TaskType.DEADLINE;

        String formatDate = getFormattedDateX(by);
        boolean noTime = false;
        if (formatDate == null) {
            formatDate = getFormattedDate(by);
            noTime = true;
        }
        try {
            assert formatDate != null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            simpleDateFormat.setLenient(false);
            dateNow = simpleDateFormat.parse(by);
            this.by = by;
            if (noTime) {
                by += " 0000";
                formatDate = getFormattedDateX(by);
                simpleDateFormat = new SimpleDateFormat(formatDate);
                simpleDateFormat.setLenient(false);
                dateNow = simpleDateFormat.parse(by);
            }
            System.out.println("Format:" + formatDate);
        } catch (ParseException | NullPointerException e) {
            throw new CakeException("[!] Date cannot be parsed: " + by);
        }


        //        Parser parser = new Parser();
        //        List<DateGroup> groups = parser.parse(by);
        //        dateNow = groups.get(0).getDates().get(0);
        System.out.println("Date:" + dateNow.toString());

    }

    /**
     * Gets the task type in [] format and
     * its description.
     * @return String containing type and description
     */
    @Override
    public String toString() {
        return "[D]" + description + " (by: " + by + ")";
    }

    @Override
    public String getFullString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }

    /**
     * Method to get date of task if possible.
     * @return String containing the date of Task
     */
    @Override
    public Date getDateTime() {
        return dateNow;
    }

    /**
     * Method to get details of extra details
     * concerning the task.
     * @return String containing details of when task
     *         is to be done by
     */
    @Override
    public String getExtra() {
        return this.by;
    }

    @Override
    public void changeDate(String newDate) throws CakeException {

        String formatDate = getFormattedDateX(newDate);
        boolean noTime = false;
        if (formatDate == null) {
            formatDate = getFormattedDate(newDate);
            noTime = true;
        }
        try {
            assert formatDate != null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            simpleDateFormat.setLenient(false);
            dateNow = simpleDateFormat.parse(newDate);
            this.by = newDate;
            if (noTime) {
                newDate += " 0000";
                formatDate = getFormattedDateX(newDate);
                simpleDateFormat = new SimpleDateFormat(formatDate);
                simpleDateFormat.setLenient(false);
                dateNow = simpleDateFormat.parse(newDate);
            }
            System.out.println("Format:" + formatDate);
        } catch (ParseException | NullPointerException e) {
            throw new CakeException("[!] Date cannot be parsed: " + newDate);
        }

    }

    //no time mode
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
            put("^\\d{8}$", "ddMMyyyy");
            put("^\\d{6}$", "ddMMyy");
            //normal mode
            put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
            put("^\\d{1,2}-\\d{1,2}-\\d{2}$", "dd-MM-yy");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "dd/MM/yyyy");
            put("^\\d{1,2}/\\d{1,2}/\\d{2}$", "dd/MM/yy");
            put("^\\d{1,2}\\s\\d{1,2}\\s\\d{4}$", "dd MM yyyy");
            put("^\\d{1,2}\\s\\d{1,2}\\s\\d{2}$", "dd MM yy");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{2}$", "dd MMM yy");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{2}$", "dd MMMM yy");
        }};

    private String getFormattedDate(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null;
    }

    //with time mode
    private static final Map<String, String> DATE_FORMAT_REGEXPS_X = new HashMap<String, String>() {{
            //no space in date
            put("^\\d{8}\\s\\d{4}$", "ddMMyyyy HHmm");
            put("^\\d{6}\\s\\d{4}$", "ddMMyy HHmm");
            put("^\\d{8}\\s\\d{1,2}:\\d{2}$", "ddMMyyyy HH:mm");
            put("^\\d{6}\\s\\d{1,2}:\\d{2}$", "ddMMyy HH:mm");
            //no colon
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}\\d{2}$", "dd-MM-yyyy HHmm");
            put("^\\d{1,2}-\\d{1,2}-\\d{2}\\s\\d{1,2}\\d{2}$", "dd-MM-yy HHmm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}\\d{2}$", "dd/MM/yyyy HHmm");
            put("^\\d{1,2}/\\d{1,2}/\\d{2}\\s\\d{1,2}\\d{2}$", "dd/MM/yy HHmm");
            put("^\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}\\d{2}$", "dd MM yyyy HHmm");
            put("^\\d{1,2}\\s\\d{1,2}\\s\\d{2}\\s\\d{1,2}\\d{2}$", "dd MM yy HHmm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}\\d{2}$", "dd MMM yyyy HHmm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{2}\\s\\d{1,2}\\d{2}$", "dd MMM yy HHmm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}\\d{2}$", "dd MMMM yyyy HHmm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{2}\\s\\d{1,2}\\d{2}$", "dd MMMM yy HHmm");
            //colon in time
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
            put("^\\d{1,2}-\\d{1,2}-\\d{2}\\s\\d{1,2}:\\d{2}$", "dd-MM-yy HH:mm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "dd/MM/yyyy HH:mm");
            put("^\\d{1,2}/\\d{1,2}/\\d{2}\\s\\d{1,2}:\\d{2}$", "dd/MM/yy HH:mm");
            put("^\\d{1,2}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MM yyyy HH:mm");
            put("^\\d{1,2}\\s\\d{1,2}\\s\\d{2}\\s\\d{1,2}:\\d{2}$", "dd MM yy HH:mm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{2}\\s\\d{1,2}:\\d{2}$", "dd MMM yy HH:mm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{2}\\s\\d{1,2}:\\d{2}$", "dd MMMM yy HH:mm");
        }};

    private String getFormattedDateX(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS_X.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS_X.get(regexp);
            }
        }
        return null;
    }
}
