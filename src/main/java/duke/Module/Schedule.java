package duke.Module;

import duke.Task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Class manages the timetable for the user.
 */
public class Schedule {

    private String filePath;
    private Scanner fileInput;
    private ArrayList<TimeSlot> list;

    /**
     * Optional goal of the day.
     */
    private HashMap<Date,String> goals = new HashMap<>();

    public Schedule(String filePath) throws FileNotFoundException, ParseException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
        this.list = loadTimeSlot();
    }

    /**
     * This function saves the newly created TimeSlot into timeslots.txt
     * @param t The TimeSlot object created to be saved
     */
    public void saveTimeSlot(TimeSlot t) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat("HHmm");
            fileWriter.write(t.getClassName() + "-" + df.format(t.getStartTime()) + "-" + df.format(t.getEndTime()) + "-" + t.getLocation() + "\n");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * Reads filePath, takes in Strings and turns them into a list of TimeSlot objects
     */
    public ArrayList<TimeSlot> loadTimeSlot() throws ParseException {
        try {
            ArrayList<TimeSlot> temp = new ArrayList<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
                Date date1 = simpleDateFormat.parse(data[1]);
                Date date2 = simpleDateFormat.parse(data[2]);
                TimeSlot t = new TimeSlot(date1,date2,data[3],data[0]);
                temp.add(t);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function updates the list of tasks.
     * Erases the entire list that exists presently and rewrites the file.
     * @param up The updated ArrayList that must be used to recreate the updated duke.txt
     * @throws IOException io
     */
    public void updateTimeSlot(ArrayList<TimeSlot> up) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (TimeSlot t : up) {
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HHmm");
                fileWriter.write(t.getClassName() + "-" + df.format(t.getStartTime()) + "-" + df.format(t.getEndTime()) + "-" + t.getLocation() + "\n");
                fileWriter.close();
            } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
    }

    /**
     * Defined day which a class needs to be made;
     */
    private String day;

    public void setDay (String newDay) {
        day = newDay;
    }

    /**
     * Array of all possible months
     */
    private String[] months = {
        "January", "February", "March",
        "April", "May", "June",
        "July", "August", "September",
        "October", "November", "December"
    };

    /**
     * Array of all days in each month
     */
//    private int[] days = {
//        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
//    };

    /**
     * Will print out a formatted calender.
     *
     * @param numberOfDays days in the month
     * @param startDay     beginning day in the month
     */
    private static void printMonth(int numberOfDays, int startDay) {
        int weekdayIndex = 0;
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        for (int day = 1; day < startDay; day++) {
            System.out.print("    ");
            weekdayIndex++;
        }

        for (int day = 1; day <= numberOfDays; day++) {
            System.out.printf("%1$2d", day);
            weekdayIndex++;
            if (weekdayIndex == 7) {
                weekdayIndex = 0;
                System.out.println();
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }

    /**
     * Function gets the month of the current year.
     *
     * @return String of all the days in the month
     */
    public String getMonth() {
        Calendar cal = Calendar.getInstance();
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Set the calendar to monday of the current week
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("MMM");
        System.out.println("--------------------------");
        System.out.println(df.format(cal.getTime()) + " " + cal.get(Calendar.YEAR));
        printMonth(numDays, cal.get(Calendar.DAY_OF_MONTH));
        return "--------------------------";
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
            week.append("[").append(i + 1).append("]. ").append(df.format(cal.getTime())).append("\n");
            cal.add(Calendar.DATE, 1);
        }
        return week.toString();
    }

    /**
     * Function gets all the hours in the selected day.
     * Will load events if events have been allocated.
     * @param dayOfClass The selected day of the month. e.g 5/10/2019
     * @return String of every hour from 8am inside the day.
     */
    public String getDay(String dayOfClass) throws ParseException {
        for (int i=0; i<=24; i++) {
            String time = (i < 10) ? "0" + i + "00" : i + "00";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date now = simpleDateFormat.parse(dayOfClass + " " + time);
            DateFormat df = new SimpleDateFormat("HH:mm");
            boolean isAssignedClass = false;
            for (TimeSlot t : this.list) {
                if (now.equals(t.getStartTime())) {
                    isAssignedClass = true;
                    System.out.println(df.format(now) + " " + t.getClassName() + " from " + df.format(t.getStartTime()) + " to " + df.format(t.getEndTime()) + " at " + t.getLocation());
                }
            }
            if (!isAssignedClass) {
                System.out.println(df.format(now));
            }
        }

        return "--------------------------";
    }

    public String addClass(String startTime, String endTime, String location, String className , TaskList taskList) {
        Date start = taskList.dateConvert(startTime);
        Date end = taskList.dateConvert(endTime);
        TimeSlot timeSlot = new TimeSlot(start, end, location, className);
        this.list.add(timeSlot);
        saveTimeSlot(timeSlot);
        updateTimeSlot(this.list);
        return "New training has been added";
    }

    public String delClass(String name) {
        int index = 0;
        if (this.list.isEmpty())
            return "No class available";
        for (TimeSlot i: this.list) {
            if (i.getClassName().equals(name)){
                this.list.remove(index);
                updateTimeSlot(this.list);
                return "Class removed";
            }
            ++index;
        }
        return "Class not found";
    }

    public String addGoal(String date, String message) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(date);
        setGoal(today, message);
        return "New goal of the day has been added";
    }

    public void setGoal(Date day, String message) {
        goals.put(day,message);
    }

    public String removeGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                goals.remove(d);
            }
        }
        return "Goal of the day on " + day + " has been removed";
    }

    public String viewGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        String message = "";
        boolean hasGoal = false;
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                if (!goals.get(d).isEmpty()) {
                    hasGoal = true;
                    message += goals.get(d);
                }
            }
        }
        if (!hasGoal) {
            return "There is no goal of the day";
        } else {
            return message;
        }
    }
}
