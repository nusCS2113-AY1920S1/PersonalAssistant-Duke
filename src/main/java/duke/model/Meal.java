package duke.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Task is a public class that represents the tasks in duke.
 * A task object encapsulates the description of the task, the type of task it is, and whether
 * the task is done.
 */
public class Meal {
    protected String description;
    protected String type = "";
    protected boolean isDone;
    protected SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
    protected String date = dateparser.format(Calendar.getInstance().getTime());
    protected HashMap<String, Integer> nutritionValue = new HashMap<String, Integer>();

    /**
     * This is the constructor of Task object.
     * @param description the description of the task
     */
    public Meal(String description, String details) {
        this.description = description.trim();
        //todo: date input can only be accepted at the back of the statement
        if (details.contains("/date")) {
            String[] splitString = details.split("/date", 2);
            try {
                Date day;
                day = dateparser.parse(splitString[1].trim());
                this.date = dateparser.format(day);
            } catch (Exception e) {
                //todo something here
            }
            details = splitString[0];
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
        }
    }

    /**
     * This is the secondary constructor of Task object.
     * used mainly in storage parsing
     * @param description the description of the task
     */
    public Meal(String description, String[] details) {
        this.description = description.trim();
        try {
            Date day;
            day = dateparser.parse(details[1]);
            this.date = dateparser.format(day);
        } catch (Exception e) {
            //toDo exception catching added here
        }
        for (int i = 2; i < details.length; i += 2) {
            nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

    /**
     * This is the no argument constructor for meal task object.
     * used to satisfy requirement for default constructor, not used otherwise
     */
    public Meal() {
    }

    /**
     * This function checks whether the particular task object is done and return the string accordingly.
     * @return <code>[\u2713]</code> if the task is done
     *          <code>[\u2718]</code> if the task is not done
     */
    public String getStatusIcon() {
        return (isDone ? "[YES]" : "[NO]"); //return tick or X symbols
    }

    /**
     * This is a getter for description.
     * @return description of the task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This is a setter for isDone.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This is a getter for isDone.
     * @return isDone status of the task
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * This is a getter for the type.
     * @return type of the task
     */
    public String getType() {
        return this.type;
    }

    /**
     * This is a getter for the date.
     * @return date of the meal
     */
    public String getDate() {
        return this.date;
    }

    public HashMap<String, Integer> getNutritionalValue() {
        return this.nutritionValue;
    }

    public void addNutritionalValue(String keyStr, int value) {
        this.nutritionValue.put(keyStr, value);
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the task
     */
    @Override
    public String toString() {
        String temp = "";
        for (String i : nutritionValue.keySet()) {
            temp += i + ":" + nutritionValue.get(i) + " ";
        }
        return this.getStatusIcon() + " " + this.description + " | " + temp;
        //TODO: refactor this by using type also
    }

}