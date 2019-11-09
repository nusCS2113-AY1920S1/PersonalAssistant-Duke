package duke.models;

import duke.data.ScheduleStorage;
import duke.data.Storage;
import duke.view.CliViewSchedule;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

//@@author Sfloydzy

/**
 * Class manages the timetable for the user.
 */
public class Schedule {

    /**
     * List that needs to be removed.
     */
    private ArrayList<TimeSlot> list = new ArrayList<>();

    private CliViewSchedule cliViewSchedule = new CliViewSchedule();

    /**
     * Function gets the month of the current year.
     *
     * @param selectMonth The month you want to view
     */
    public void getMonth(final int selectMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, selectMonth);

        // Set the calendar to monday of the current week
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // Print dates of the current week starting on Monday
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        DateFormat df = new SimpleDateFormat("MMM");
        String date = df.format(cal.getTime());
        int year = cal.get(Calendar.YEAR);

        //display the month selected
        cliViewSchedule.printMonthHeader(date, year);
        cliViewSchedule.printMonth(numDays, cal.get(Calendar.DAY_OF_MONTH));

    }

    /**
     * Method will get the table date a that needs to be shown.
     *
     * @param day   the day of that is being viewed
     * @param month the month that is being viewed
     * @return
     */
    public ArrayList<ToDo> getCells(int day, int month) {
        ArrayList<ToDo> toDoArrayList = new ArrayList<>();
        ScheduleStorage scheduleStorage = new ScheduleStorage();
        String date = "2019" + month + day;
        toDoArrayList.addAll(Objects.requireNonNull(scheduleStorage.load(date)));
        return toDoArrayList;
    }

    /**
     * Method will get a table for the schedule.
     *
     * @param day   the day of that is being viewed
     * @param month the month that is being viewed
     */
    public void getTable(int day, int month) {
        cliViewSchedule.tableDate(day, month);
        cliViewSchedule.tableHeader();
        cliViewSchedule.tableContents(getCells(day, month));
    }


    /**
     * Method will show the current days in the present week.
     *
     * @return List of all days in the week in the format [index] DAY DATE MONTH
     */
    public String getWeek() {
        StringBuilder week = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_WEEK);

        // Set the calendar to monday of the current week
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("EEE dd MMM");
        for (int i = 0; i < numDays; i++) {
            week.append("[").append(i + 1).append("]. ")
                .append(df.format(cal.getTime())).append("\n");
            cal.add(Calendar.DATE, 1);
        }
        return week.toString();
    }

    /**
     * Function gets all the hours in the selected day.
     * Will load events if events have been allocated.
     *
     * @param dayOfClass The selected day of the month. e.g 5/10/2019
     * @return String of every hour from 8am inside the day.
     * @throws ParseException if dayOfClass is in wrong format
     */
    public String getDay(final int dayOfClass, final int monthOfClass) throws ParseException {

        final int numberOfHoursInADay = 24;
        final int tempInt = 10;
        String selectedDate = dayOfClass + "/" + monthOfClass;
        String message = "";
        for (int i = 0; i <= numberOfHoursInADay; i++) {
            String time = (i < tempInt) ? "0" + i + "00" : i + "00";
            SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/MM HHmm");
            Date now = simpleDateFormat.parse(selectedDate + " " + time);
            DateFormat df = new SimpleDateFormat("HH:mm");
            boolean isAssignedClass = false;
            try {
                for (TimeSlot t : this.list) {
                    if (now.equals(t.getStartTime())) {
                        isAssignedClass = true;
                        message += df.format(now)
                            + " " + t.getClassName() + " from "
                            + df.format(t.getStartTime())
                            + " to " + df.format(t.getEndTime()) + " at "
                            + t.getLocation() + "\n";
                    }
                }
            } catch (NullPointerException e) {
                cliViewSchedule.errMessage("Error with the loaded schedule");
            }
            if (!isAssignedClass) {
                message += df.format(now) + "\n";
            }
        }
        return message;
    }

    /**
     * Method to add a class.
     *
     * @param startTime       The start time of the class
     * @param endTime         The end time of the class
     * @param location        The location where the class is held
     * @param className       The name of the class
     * @param scheduleStorage The object responsible for storing the class
     * @return The outcome of the operation,whether the class was added or not
     * @throws ParseException if startTime or endTime is in wrong format
     */
    public String addClass(final String startTime,
                           final String endTime,
                           final String location,
                           final String className,
                           final Storage scheduleStorage)
        throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd/MM/yyyy HHmm");
        Date start = simpleDateFormat.parse(startTime);
        Date end = simpleDateFormat.parse(endTime);
        boolean hasClash = false;
        for (TimeSlot t : this.list) {
            if (!hasClash) {
                if (start.equals(t.getEndTime())) {
                    hasClash = true;
                    break;
                }
                Date temp = t.getStartTime();
                while (!temp.equals(t.getEndTime())) {
                    if (start.equals(temp)) {
                        hasClash = true;
                        break;
                    }
                    long curTimeInMs = temp.getTime();
                    final int numberOfMillisecondsInAMinute = 60000;
                    temp = new Date(curTimeInMs
                        + numberOfMillisecondsInAMinute);
                }
            }
        }
        if (!hasClash) {
            TimeSlot timeSlot = new TimeSlot(start, end, location, className);
            this.list.add(timeSlot);
            scheduleStorage.saveSchedule(timeSlot);
            scheduleStorage.updateSchedule(this.list);
            return "New class has been added";
        } else {
            return "Unable to add class."
                + "There is already another class in the same time slot.";
        }
    }

    /**
     * Method to delete a class.
     *
     * @param startTime       The start time of the class
     * @param name            The name of the class
     * @param scheduleStorage The object responsible for storing the class
     * @return The outcome of the operation,whether the class was deleted or not
     * @throws ParseException if the start time of the class is
     *                        in the wrong format
     */
    public String delClass(final String startTime,
                           final String name,
                           final ScheduleStorage scheduleStorage)
        throws ParseException {
        SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date start = simpleDateFormat.parse(startTime);
        int index = 0;
        if (this.list.isEmpty()) {
            return "No class available";
        }
        for (TimeSlot i : this.list) {
            if (i.getClassName().equals(name)
                && i.getStartTime().equals(start)) {
                this.list.remove(index);
                //                scheduleStorage.updateSchedule(this.list);
                return "Class removed";
            }
            ++index;
        }
        return "Class not found";
    }

    /**
     * Method will remove all the saved classes from the list.
     *
     * @param date            The date to remove all the classes.
     * @param scheduleStorage Where the save file will be located.
     * @return Success string
     */
    public String delAllClass(final String date,
                              final Storage scheduleStorage) {
        for (TimeSlot i : this.list) {
            DateFormat df = new SimpleDateFormat("HHmm");
            String today = df.format(i.getStartTime());
            String[] temp = today.split(" ");
            if (temp[0].equals(date)) {
                this.list.remove(i);
            }
        }
        scheduleStorage.updateSchedule(this.list);
        return "All classes on " + date + " are cleared";
    }

}
