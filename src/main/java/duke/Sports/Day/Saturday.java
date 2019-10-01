package duke.Sports.Day;

import duke.Sports.MyClass;

import java.util.Date;
import java.util.ArrayList;

/**
 * Represents a subclass of Day abstract class.
 */
public class Saturday extends Day {

    public Saturday(Date date) {
        super.date = date;
    }

    /**
     * Access the date of the day.
     * @return The Date object representing the date of the day.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Add a class to a day
     * @param myclass The class you want to add to that day.
     * @param times The list of times (start and end) of that class.
     */
    public void addClassToDay(MyClass myclass, ArrayList<Date> times) {
        Day.put(myclass,times);
    }

    /**
     * Remove a class from a day
     * @param className The name of the class you want to remove from that day.
     */
    public void removeClassFromDay(String className) {
        for (MyClass i : Day.keySet()) {
            if (i.getInfo().equals(className)) {
                Day.remove(i);
            }
        }
    }

    /**
     * Remove all the classes in a day
     */
    public void removeAllClasses() {
        Day.clear();
    }

    /**
     * Returns the total number of classes in a day
     */
    public int findNumClasses() {
        return Day.size();
    }

    /**
     * Access a class in a day
     * @param className The name of the class you want to access in a day
     * @return The MyClass object which represents the class you want to access
     */
    public MyClass accessClassInDay(String className) {
        MyClass temp = null;
        for (MyClass i : Day.keySet()) {
            if (i.getInfo().equals(className)) {
                temp = i;
            }
        }
        return temp;
    }

    /**
     * Access the start time of a class in a day
     * @param className The name of the class you want to access the start time of
     * @return The Date object which represents the start time of the class
     */
    public Date accessStartTimeInDay(String className) {
        Date temp = null;
        for (MyClass i : Day.keySet()) {
            if (i.getInfo().equals(className)) {
                temp = Day.get(i).get(0);
            }
        }
        return temp;
    }

    /**
     * Access the end time of a class in a day
     * @param className The name of the class you want to access the end time of
     * @return The Date object which represents the end time of the class
     */
    public Date accessEndTimeInDay(String className) {
        Date temp = null;
        for (MyClass i : Day.keySet()) {
            if (i.getInfo().equals(className)) {
                temp = Day.get(i).get(1);
            }
        }
        return temp;
    }

}
