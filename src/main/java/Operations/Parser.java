package Operations;

import CustomExceptions.RoomShareException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Scanner;

import Enums.ExceptionType;
import Enums.TimeUnit;

/**
 * A class for handling all parsing for Duke. Makes sure that inputs by the user
 * are properly formatted as parameters for other classes.
 */
public class Parser {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for the Parser object
     */
    public Parser() {
    }

    /**
     * Returns the command that the user has given RoomShare
     * @return command The command given by the user to RoomShare
     */
    public String getCommand() {
        String command = scanner.next().toLowerCase().trim();
        return command;
    }

    /**
     * Return the line of command that the user has given Duke
     * @return command The line of command given by the user to RoomShare
     */
    public String getCommandLine() {
        String command = scanner.nextLine().toLowerCase().trim();
        return command;
    }

    /**
     * Returns the index number requested by the user for commands like 'snooze'
     * @return index Index the user wishes to perform operations on.
     */
    public Integer getIndex() throws RoomShareException {
        try {
            String temp = scanner.next().trim();
            int index = Integer.parseInt(temp) - 1;
            return index;
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongIndexFormat);
        }
    }

    /**
     * Returns the index number requested by the user for subTask.
     * @return index Index the user wishes to assign subtasks to.
     */
    public Integer getIndexSubtask() throws RoomShareException{
        try {
            String temp = scanner.next().trim();
            int index = Integer.parseInt(temp) - 1;
            return index;
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongIndexFormat);
        }
    }

    /**
     * Return a single index number or a range of index number requested by users for command 'done' and 'delete'
     * @return a single index or a range of index
     */
    public int[] getIndexRange() throws RoomShareException {
        String[] temp = scanner.nextLine().trim().split("-",2);
        try {
            int[] index;
            if (temp.length == 1) {
                index = new int[]{Integer.parseInt(temp[0].trim()) - 1};
            } else
                index = new int[]{Integer.parseInt(temp[0].trim()) - 1, Integer.parseInt(temp[1].trim()) - 1};
            return index;
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongIndexFormat);
        }
    }

    /**
     * Returns a Date object from a raw date that is stored as a String.
     * If the format of the input string is unacceptable, will throw a DukeException and will not return anything.
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     * @throws RoomShareException If by is not in dd/MM/yyyy HH:mm format
     */
    public Date formatDate(String by) throws RoomShareException {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(by);
        } catch (ParseException e2) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
    }

    /**
     * Returns a Date object from a raw date that is stored as a String with special key words like "tomorrow, today"
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     */
    public Date formatDateCustom_1(String by) {
        try {
            Date date = new Date();
            String[] temp = by.split(" ");
            String day = temp[0];
            String[] time = temp[1].split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            date.setHours(hours);
            date.setMinutes(minutes);
            if (day.toLowerCase().equals("tomorrow") || day.toLowerCase().equals("tmr")) {
                date.setDate(date.getDate() + 1);
                return date;
            }
            else if (day.toLowerCase().equals("today") || day.toLowerCase().equals("tdy")) {
                return date;
            }
            else return null;
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Returns a Date object from a raw date that is stored as a String with special key words like "next monday, this fri"
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     */
    public Date formatDateCustom_2(String by) {
        try {
            LocalDate date = LocalDate.now();
            DayOfWeek currentDayOfWeek = date.getDayOfWeek();
            Date outputDate;

            String[] temp = by.split(" ");

            // Check if the user enter proper keyword "next" or "this"
            if (!temp[0].toLowerCase().equals("next") && !temp[0].toLowerCase().equals("this"))
                return null;

            // Check which day of the next week the user input
            String day = temp[1].trim();
            DayOfWeek dayOfWeek;
            if (day.toLowerCase().equals("monday") || day.toLowerCase().equals("mon")) {
                dayOfWeek = DayOfWeek.MONDAY;
            } else if (day.toLowerCase().equals("tuesday") || day.toLowerCase().equals("tues")) {
                dayOfWeek = DayOfWeek.TUESDAY;
            } else if (day.toLowerCase().equals("wednesday") || day.toLowerCase().equals("wed")) {
                dayOfWeek = DayOfWeek.WEDNESDAY;
            } else if (day.toLowerCase().equals("thursday") || day.toLowerCase().equals("thurs")) {
                dayOfWeek = DayOfWeek.THURSDAY;
            } else if (day.toLowerCase().equals("friday") || day.toLowerCase().equals("fri")) {
                dayOfWeek = DayOfWeek.FRIDAY;
            } else if (day.toLowerCase().equals("saturday") || day.toLowerCase().equals("sat")) {
                dayOfWeek = DayOfWeek.SATURDAY;
            } else if (day.toLowerCase().equals("sunday") || day.toLowerCase().equals("sun")) {
                dayOfWeek = DayOfWeek.SUNDAY;
            } else
                return null;

            if (temp[0].toLowerCase().equals("this")) {
                date = date.with(TemporalAdjusters.next(dayOfWeek));
            }
            else {
                if (currentDayOfWeek.getValue() < dayOfWeek.getValue()) {
                    date = date.with(TemporalAdjusters.next(dayOfWeek));
                    date = date.with(TemporalAdjusters.next(dayOfWeek));
                }
                else
                    date = date.with(TemporalAdjusters.next(dayOfWeek));
            }

            // Convert LocalDate object to Date object for storing compatibility
            ZoneId defaultZoneId = ZoneId.systemDefault();
            outputDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());

            // Set hours and minute as specified
            String[] time = temp[2].split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            outputDate.setHours(hours);
            outputDate.setMinutes(minutes);

            return outputDate;
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Returns the keyword to be searched for.
     * @return key A string of the keyword to be searched for
     */
    public String getKey() {
        String key = scanner.nextLine().trim();
        return key;
    }

    /**
     * Returns the amount of time the customer request to snooze
     * @return the amount of time the customer request to snooze
     */
    public int getAmount() throws RoomShareException{
        try {
            String temp = scanner.next().trim();
            return Integer.parseInt(temp);
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongTimeFormat);
        }
    }

    /**
     * Returns the unit of time the customer request to snooze
     * @return the unit of time the customer request to snooze
     */
    public TimeUnit getTimeUnit() throws RoomShareException{
        try {
            String temp = scanner.next().trim();
            return TimeUnit.valueOf(temp);
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongTimeFormat);
        }

    }

    /**
     * Returns the index of the task and priority the user wants to set it to
     * @return the index and priority of the task the user wants to set
     */
    public String[] getPriority() {
        return scanner.nextLine().trim().split(" ", 2);
    }

}
