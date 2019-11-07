package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.model.task.bookingtasks.Booking;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static duke.common.BookingMessages.*;

/**
 * Handles the view booking schedule command by searching with date.
 */
public class ViewBookingScheduleCommand extends Command<BookingList, Ui, BookingStorage> {
    private static final Logger logger = Logger.getLogger(ViewBookingScheduleCommand.class.getName());

    /**
     * Set up a logger to log important information.
     */
    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);

        try {
            FileHandler fh = new FileHandler("logFile.log", true);
            fh.setLevel(Level.INFO);
            logger.addHandler(fh);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "File logger is not working.", e);
        }
    }

    /**
     * Constructor for class ViewBookingScheduleCommand.
     *
     * @param userInput string containing the input from the user
     */
    public ViewBookingScheduleCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Validates the date to be in required format .
     *
     * @param bookingDate String of date input by user
     * @return true if the date is in required format and false otherwise
     */
    private static boolean isDateParsable(String bookingDate) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
            return true;
        } catch (ParseException e) {
            logger.warning("Invalid date format entered.");
            return false;
        }
    }

    /**
     * Validates the date to be a valid date in the calendar after year 2000.
     *
     * @param bookingDate String of date input by user
     * @return True if the date is on the calendar and false otherwise
     */
    private static boolean isValidDate(String bookingDate) {
        String[] dateInput = bookingDate.split("/");

        int day = Integer.parseInt(dateInput[0]);
        int month = Integer.parseInt(dateInput[1]);
        int year = Integer.parseInt(dateInput[2]);

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day > 0 && day <= 31 && year >= 2000) {
                return true;
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 0 && day <= 30 && year >= 2000) {
                return true;
            }
        }
        if (month == 2) {
            if (year >= 2000 && (year - 2000) % 4 == 0) { //is a leap year
                if (day > 0 && day <= 29) {
                    return true;
                }
                return false;
            } else if (year >= 2000 && (year - 2000) % 4 != 0) { //is a common year
                if (day > 0 && day <= 28) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Validates whether two dates are the same.
     *
     * @param currDate    date input by user using this command
     * @param bookingDate the date of booking from the booking list
     * @return true if two are the same and false otherwise
     */
    private static boolean isCurrentTime(Date currDate, Date bookingDate) {
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTime(currDate);
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currMonth = calendar.get(Calendar.MONTH);
        int currYear = calendar.get(Calendar.YEAR);

        calendar.setTime(bookingDate);
        int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
        int taskMonth = calendar.get(Calendar.MONTH);
        int taskYear = calendar.get(Calendar.YEAR);

        if (taskYear == currYear && taskMonth == currMonth && taskDay == currDay) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Processes the view booking schedule command to check the availability of a particular date.
     *
     * @param bookingList    contains the booking list
     * @param ui             deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving bookings in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws ParseException {
        ViewBookingScheduleCommand.setupLogger();
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_DATE);
        } else if (userInput.trim().charAt(19) == ' ') {
            String inputDate = userInput.substring(20).trim();
            if (isDateParsable(inputDate)) {
                if (isValidDate(inputDate)) {
                    Date currDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);

                    ArrayList<Booking> scheduleList = new ArrayList<>();
                    for (Booking booking : bookingList.getBookingList()) {
                        Date bookingDate = booking.getDateTime();
                        if (isCurrentTime(currDate, bookingDate)) {
                            scheduleList.add(booking);
                        }
                    }
                    String outputDate = new SimpleDateFormat("dd MMMM yyyy").format(currDate);
                    if (scheduleList.isEmpty()) {
                        arrayList.add(MESSAGE_NO_BOOKING + outputDate + MESSAGE_PROMPT_ADDBOOKING);
                    } else {
                        arrayList.add(MESSAGE_BOOKING_ON + outputDate);
                        for (int i = 0; i < scheduleList.size(); i++) {
                            arrayList.add("      " + (i + 1) + ". " + scheduleList.get(i));
                        }
                    }
                } else {
                    arrayList.add(ERROR_MESSAGE_OVERFLOW_DATE);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_DATE);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_INVALID_VIEWBOOKINGSCHEDULE_COMMAND);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}