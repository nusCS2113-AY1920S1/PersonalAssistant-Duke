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
        this.by = by;
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
        this.by = newDate;
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

    }

    //no time mode
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
            put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "dd/MM/yyyy");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
            put("^\\d{8}$", "ddMMyyyy");
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
            put("^\\d{12}$", "ddMMyyyyHHmm");
            put("^\\d{14}$", "ddMMyyyyHHmmss");
            put("^\\d{8}\\s\\d{4}$", "ddMMyyyy HHmm");
            put("^\\d{8}\\s\\d{1,2}:\\d{2}$", "ddMMyyyy HH:mm");
            put("^\\d{8}\\s\\d{6}$", "ddMMyyyy HHmmss");
            put("^\\d{8}\\s\\d{1,2}:\\d{2}:\\d{2}$", "ddMMyyyy HH:mm:ss");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}\\d{2}$", "dd-MM-yyyy HHmm");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}\\d{2}$", "yyyy-MM-dd HHmm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}\\d{2}$", "dd/MM/yyyy HHmm");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}\\d{2}$", "yyyy/MM/dd HHmm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}\\d{2}$", "dd MMM yyyy HHmm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}\\d{2}$", "dd MMMM yyyy HHmm");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "dd/MM/yyyy HH:mm");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
            //kill yourself with seconds mode
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd/MM/yyyy HH:mm:ss");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
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
