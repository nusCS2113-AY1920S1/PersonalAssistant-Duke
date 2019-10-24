package duke.model;

import duke.commons.exceptions.DukeException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Goal is a public class that defines all user set dietary goals.
 */
public class Goal {
    private SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
    private String enddate;
    private String startdate;
    private HashMap<String, Integer> nutritionValue = new HashMap<String, Integer>();

    public Goal(String enddate, String details) throws DukeException {
        try {
            Date day;
            startdate = dateparser.format(Calendar.getInstance().getTime());
            day = dateparser.parse(enddate);
            this.enddate = dateparser.format(day);
        } catch (Exception e) {
            throw new DukeException("It appears an invalid date has been entered");
        }
        if (details.trim().length() != 0) {
            String[] splitString1 = details.split("/");
            for (String data : splitString1) {
                if (data.trim().length() != 0) {
                    String[] partitionedData = data.split(" ", 2);
                    String nutrient = partitionedData[0];
                    int value = Integer.valueOf(partitionedData[1].trim());
                    nutritionValue.put(nutrient, value);
                }
            }
        } else {
            throw new DukeException("It appears that there are fields missing in the setgoals command");
        }
    }

    public Goal(String enddate, String startdate, String[] details) throws DukeException {
        try {
            this.startdate = startdate;
            Date day;
            day = dateparser.parse(enddate);
            this.enddate = dateparser.format(day);
        } catch (Exception e) {
            throw new DukeException("It appears the previous save file has an invalid date");
        }
        for (int i = 1; i < details.length; i += 2) {
            nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

    /**
     * This is a getter for date.
     * @return description of the task
     */
    public String getEndDate() {
        return this.enddate;
    }

    public String getStartDate() {
        return this.startdate;
    }

    public HashMap<String, Integer> getNutritionalValue() {
        return this.nutritionValue;
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the goal
     */
    @Override
    public String toString() {
        String temp = "";
        for (String i : nutritionValue.keySet()) {
            temp += i + ":" + nutritionValue.get(i) + " ";
        }
        return "[NO]" + " " + this.enddate + " | " + temp;
        //TODO: refactor this by using type also
    }

}
