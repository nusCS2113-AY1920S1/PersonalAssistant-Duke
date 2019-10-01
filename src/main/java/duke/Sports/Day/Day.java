package duke.Sports.Day;

import duke.Sports.MyClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Day class which will be inherited by specific *day subclasses
 * to run their specific functions
 */
public abstract class Day {

    /**
     * Represents the date of the day.
     */
    protected Date date;

    /**
     * A hash map with key-value pairs for every class of the day.
     * The class represents key and the list of start and end times represents values.
     */
    protected Map<MyClass, ArrayList<Date>> Day = new HashMap<>();

    /**
     * Access the date of the day.
     * @return The Date object representing the date of the day.
     */
    public abstract Date getDate();

    /**
     * Add a class to a day
     * @param myclass The class you want to add to that day.
     * @param times The list of times (start and end) of that class.
     */
    public abstract void addClassToDay(MyClass myclass, ArrayList<Date> times);

    /**
     * Remove a class from a day
     * @param className The name of the class you want to remove from that day.
     */
    public abstract void removeClassFromDay(String className);

    /**
     * Remove all the classes in a day
     */
    public abstract void removeAllClasses();

    /**
     * Returns the total number of classes in a day
     */
    public abstract int findNumClasses();

    /**
     * Access a class in a day
     * @param className The name of the class you want to access in a day
     * @return The MyClass object which represents the class you want to access
     */
    public abstract MyClass accessClassInDay(String className);

    /**
     * Access the start time of a class in a day
     * @param className The name of the class you want to access the start time of
     * @return The Date object which represents the start time of the class
     */
    public abstract Date accessStartTimeInDay(String className);

    /**
     * Access the end time of a class in a day
     * @param className The name of the class you want to access the end time of
     * @return The Date object which represents the end time of the class
     */
    public abstract Date accessEndTimeInDay(String className);

}
