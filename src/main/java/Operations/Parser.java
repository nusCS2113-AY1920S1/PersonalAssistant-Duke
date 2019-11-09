package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.SortType;
import Enums.TimeUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Scanner;

/**
 * A class for handling all parsing for Duke. Makes sure that inputs by the user.
 * are properly formatted as parameters for other classes.
 */
public class Parser {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for the Parser object.
     */
    public Parser() {
    }

    /**
     * Returns the command that the user has given RoomShare.
     * @return The command given by the user to RoomShare
     */
    public String getCommand() {
        return scanner.next().toLowerCase().trim();
    }

    /**
     * Return the line of command that the user has given Duke.
     * @return The line of command given by the user to RoomShare
     */
    public String getCommandLine() {
        return scanner.nextLine().toLowerCase().trim();
    }

    /**
     * Returns the index number requested by the user for commands like 'snooze, update'.
     * @param input the input the user has entered
     * @return the index the user wishes to perform operations on.
     * @throws RoomShareException when the format is invalid
     */
    public Integer getIndex(String input) throws RoomShareException {
        try {
            String[] arr = input.trim().split(" ");
            return Integer.parseInt(arr[0]) - 1;
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.emptyIndex);
        }
    }

    /**
     * Return the first/second/... index number requested by the user for command like 'reorder'.
     * @param input the input the user has entered
     * @param ordinal the first/second/...
     * @return the index the user wishes to perform operations on.
     * @throws RoomShareException when the format is invalid
     */
    public Integer getIndex(String input, int ordinal) throws RoomShareException {
        try {
            String[] arr = input.trim().split(" ");
            return Integer.parseInt(arr[ordinal]) - 1;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.wrongIndexFormat);
        }
    }

    /**
     * Returns the index number requested by the user for subTask.
     * @return index Index the user wishes to assign subtasks to.
     */
    public Integer getIndexSubtask(String input) throws RoomShareException {
        try {
            String[] arr = input.trim().split(" ");
            return Integer.parseInt(arr[0]) - 1;
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongIndexFormat);
        }
    }

    /**
     * Return the sub-tasks list from the user's input.
     * @param input the input the user has entered
     * @return the sub-tasks list as a String
     * @throws RoomShareException when there is no sub-tasks list
     */
    public String getSubTasks(String input) throws RoomShareException {
        try {
            String[] arr = input.trim().split(" ", 2);
            return arr[1];
        } catch (IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.emptySubTask);
        }
    }

    /**
     * Return a single index number or a range of index number requested by users for command 'done' and 'delete'.
     * @return a single index or a range of index
     */
    public int[] getIndexRange(String input) throws RoomShareException {
        String[] temp = input.trim().split("-",2);
        try {
            int[] index;
            if (temp.length == 1) {
                index = new int[]{Integer.parseInt(temp[0].trim()) - 1};
            } else {
                index = new int[]{Integer.parseInt(temp[0].trim()) - 1, Integer.parseInt(temp[1].trim()) - 1};
            }
            return index;
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongIndexFormat);
        }
    }

    /**
     * Returns a Date object from a raw date that is stored as a String in any format.
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     * @throws RoomShareException if the input is uninterpretable.
     */
    public Date formatDate(String by) throws RoomShareException {
        Date date;
        if (this.formatDateTomorrowToday(by) != null) {
            date = this.formatDateTomorrowToday(by);
        } else if (this.formatDateByDay(by) != null) {
            date = this.formatDateByDay(by);
        } else {
            date = this.formatDateDDMMYY(by);
        }
        return date;
    }


    /**
     * Returns a Date object from a raw date that is stored as a String in a DD/MM/YYYY HH:MM format.
     * If the format of the input string is unacceptable, will throw a DukeException and will not return anything.
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     * @throws RoomShareException If by is not in dd/MM/yyyy HH:mm format
     */
    public Date formatDateDDMMYY(String by) throws RoomShareException {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            format.setLenient(false);
            Date date = format.parse(by);
            date.setSeconds(0);
            return date;
        } catch (ParseException | IndexOutOfBoundsException | IllegalArgumentException e2) {
            throw new RoomShareException(ExceptionType.wrongDateFormat);
        }
    }

    /**
     * Returns a Date object from a raw date that is stored as a String with special key words like "tomorrow, today".
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     */
    public Date formatDateTomorrowToday(String by) {
        try {
            Date date = new Date();
            String[] temp = by.split(" ");
            String day = temp[0];
            String[] time = temp[1].trim().split(":");
            // validate hours and minute
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setLenient(false);
            format.parse(temp[1].trim());
            // extract hours and minutes
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            date.setHours(hours);
            date.setMinutes(minutes);
            date.setSeconds(0);
            if (day.toLowerCase().equals("tomorrow") || day.toLowerCase().equals("tmr")) {
                date.setDate(date.getDate() + 1);
                return date;
            } else if (day.toLowerCase().equals("today") || day.toLowerCase().equals("tdy")) {
                return date;
            } else {
                return null;
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException | ParseException e) {
            return null;
        }
    }

    /**
     * Returns a Date object from a raw date that is stored as a String with special key words like "next monday, this fri".
     * @param by Input String containing the date information.
     * @return A Date object containing the appropriately formatted date.
     */
    public Date formatDateByDay(String by) {
        try {
            LocalDate date = LocalDate.now();
            DayOfWeek currentDayOfWeek = date.getDayOfWeek();
            Date outputDate;
            String[] temp = by.split(" ");
            // validate hours and minute
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setLenient(false);
            format.parse(temp[2].trim());
            // Check if the user enter proper keyword "next" or "this"
            if (!temp[0].toLowerCase().equals("next") && !temp[0].toLowerCase().equals("this")) {
                return null;
            }
            // Check which day of the week the user input
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
            } else {
                return null;
            }
            if (temp[0].toLowerCase().equals("this")) {
                date = date.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            } else {
                if (currentDayOfWeek.getValue() < dayOfWeek.getValue()) {
                    date = date.with(TemporalAdjusters.next(dayOfWeek));
                    date = date.with(TemporalAdjusters.next(dayOfWeek));
                } else {
                    date = date.with(TemporalAdjusters.next(dayOfWeek));
                }
            }
            // Convert LocalDate object to Date object for storing compatibility
            ZoneId defaultZoneId = ZoneId.systemDefault();
            outputDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
            // Set hours and minute as specified
            String[] time = temp[2].trim().split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            outputDate.setHours(hours);
            outputDate.setMinutes(minutes);
            outputDate.setSeconds(0);
            return outputDate;
        } catch (IndexOutOfBoundsException | IllegalArgumentException | ParseException e) {
            return null;
        }
    }

    /**
     * Returns the keyword to be searched for.
     * @return key A string of the keyword to be searched for
     */
    public String getKey() {
        return scanner.nextLine().trim();
    }

    /**
     * Returns the amount of time the customer request to snooze.
     * @return the amount of time the customer request to snooze
     */
    public int getAmount(String input) throws RoomShareException {
        try {
            String[] arr = input.trim().split(" ");
            return Integer.parseInt(arr[1]);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.wrongTimeFormat);
        }
    }

    /**
     * Returns the unit of time the customer request to snooze.
     * @return the unit of time the customer request to snooze
     */
    public TimeUnit getTimeUnit(String input) throws RoomShareException {
        try {
            String[] arr = input.trim().split(" ");
            return TimeUnit.valueOf(arr[2]);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.wrongTimeFormat);
        }

    }



    /**
     * Returns the index of the task and priority the user wants to set it to.
     * @return the index and priority of the task the user wants to set
     */
    public String[] getPriority() {
        return scanner.nextLine().trim().split(" ", 2);
    }

    /**
     * Gets the input and converts it into a sort type.
     * @param input input of the sort type
     * @return the input as an enum of sort type
     * @throws RoomShareException when sort type specified is not valid
     */
    public SortType getSort(String input) throws RoomShareException {
        try {
            return SortType.valueOf(input.trim());
        } catch (IllegalArgumentException e) {
            throw new RoomShareException(ExceptionType.wrongSortFormat);
        }
    }

    /**
     * Closes the scanner used in Parser class.
     */
    public void close() {
        scanner.close();
    }
}
