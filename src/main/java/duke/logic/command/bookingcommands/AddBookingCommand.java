package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.model.task.bookingtasks.Booking;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.*;

import static duke.common.BookingMessages.*;

/**
 * Handles the add new booking command.
 */
public class AddBookingCommand extends Command<BookingList, Ui, BookingStorage> {

    private static String msg = "";
    private static final Logger logger = Logger.getLogger(AddBookingCommand.class.getName());

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
     * Constructor for class AddBookingCommand.
     *
     * @param userInput string containing the input from the user
     */
    public AddBookingCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the add command to add a new booking into booking list.
     *
     * @param bookingList    contains the booking list
     * @param ui             deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving bookings in the file
     * @return an array list consist of the results or prompts to be displayed to user
     * @throws ParseException if input booking date is not parsable.
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws ParseException {
        AddBookingCommand.setupLogger();
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_BOOKING)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_BOOKING_DETAILS);
        } else if (!userInput.contains("orders/")) {
            arrayList.add(ERROR_MESSAGE_INVALID_BOOKING_DETAILS);
        } else {
            String[] temp = userInput.split("orders/", 2);
            String[] details = temp[0].split("\\s+");
            if (userInput.trim().charAt(10) != ' ' || details.length != 5) {
                arrayList.add(ERROR_MESSAGE_INVALID_BOOKING_DETAILS);
            } else {
                String customerName = details[1].trim();
                String customerContact = details[2].trim();
                String numberOfPax = details[3].trim();
                String bookingDate = details[4].trim();
                String orderName = temp[1].trim();

                if (!isValidName(customerName)) {
                    arrayList.add(ERROR_MESSAGE_INVALID_NAME);
                } else if (!isValidContactNo((customerContact))) {
                    arrayList.add(ERROR_MESSAGE_INVALID_CONTACT_NO);
                } else if (!isParsable(numberOfPax)) {
                    arrayList.add(ERROR_MESSAGE_UNKNOWN_PAX);
                } else if (!isValidPax(numberOfPax)) {
                    arrayList.add(ERROR_MESSAGE_INVALID_PAX);
                } else if (!isDateParsable(bookingDate)) {
                    arrayList.add(ERROR_MESSAGE_INVALID_DATE);
                } else if (!isValidDate(bookingDate)) {
                    arrayList.add(ERROR_MESSAGE_OVERFLOW_DATE);
                } else if (!isAvailableDate(bookingDate, bookingList)) {
                    arrayList.add(ERROR_MESSAGE_UNAVAILABLE_DATE);
                } else if (orderName.isEmpty()) {
                    arrayList.add(ERROR_MESSAGE_EMPTY_ORDERS);
                } else {
                    bookingList.addBooking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                    bookingStorage.saveFile(bookingList);

                    int size = bookingList.getSize();

                    if (size == 1) {
                        msg = " booking in the list.";
                    } else {
                        msg = " bookings in the list.";
                    }
                    arrayList.add(MESSAGE_BOOKING_ADDED + "       " + bookingList.getBookingList().get(size - 1) + "\n" + "Now you have " + size + msg);
                }
            }
        }
        return arrayList;
    }

    /**
     * Validates the input to be alphabets or _.
     *
     * @param input String input from user
     * @return true if the string consist only alphabets or _ and false otherwise
     */
    private static boolean isValidName(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && !(c == '_')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the input to be numbers.
     *
     * @param input String input from user
     * @return true if the user inputs only numbers and false otherwise
     */
    private static boolean isValidContactNo(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the input to be integer.
     *
     * @param input String input from user
     * @return true if the user inputs an integer and false otherwise
     */
    private static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            logger.warning("Index input is not an integer.");
            return false;
        }
    }

    /**
     * Validates the input to be within a fixed range of integers.
     *
     * @param input String input from user
     * @return true if the user inputs an integer within the range and false otherwise
     */
    private static boolean isValidPax(String input) {
        int pax = Integer.parseInt(input);
        return pax > 0 && pax < 10;
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
                return day > 0 && day <= 29;
            } else if (year >= 2000 && (year - 2000) % 4 != 0) { //is a common year
                return day > 0 && day <= 28;
            }
        }
        return false;
    }

    /**
     * Validates the date to be available for new booking.
     *
     * @param bookingDate String of date input by user
     * @param bookingList List containing all current bookings
     * @return True if the date is not found in the list and false otherwise
     */
    private static boolean isAvailableDate(String bookingDate, BookingList bookingList) {
        for (Booking booking : bookingList.getBookingList()) {
            if (bookingDate.equals(booking.getBookingDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isExit() {
        return false;
    }

}