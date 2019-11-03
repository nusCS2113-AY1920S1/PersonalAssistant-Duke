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

public class AddBookingCommand extends Command<BookingList, Ui, BookingStorage> {

    private static String msg = "";
    private static final Logger logger = Logger.getLogger(AddBookingCommand.class.getName());

    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);

        try {
            FileHandler fh = new FileHandler("logFile.log",true);
            fh.setLevel(Level.INFO);
            logger.addHandler(fh);
        } catch (java.io.IOException e){
            logger.log(Level.SEVERE, "File logger is not working.", e);
        }
    }

    public AddBookingCommand(String userInput) {
        this.userInput = userInput;
    }

    private static boolean isValidName(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && !(c == '_')) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidContactNo(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates that user inputs an integer value for the index.
     *
     * @param input String containing integer input from user for the index
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

    private static boolean isValidPax(String input) {
        int pax = Integer.parseInt(input);
        if (pax > 0 && pax < 10) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isDateParsable(String bookingDate) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
            return true;
        } catch (ParseException e) {
            logger.warning("Invalid date format entered.");
            return false;
        }
    }

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

    private static boolean isAvailableDate(String bookingDate, BookingList bookingList) {
        for (Booking booking : bookingList.getBookingList()) {
            if (bookingDate.equals(booking.getBookingDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws ParseException {
        AddBookingCommand.setupLogger();
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_BOOKING)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_BOOKING_DETAILS);
        } else if (userInput.contains("orders/")) {
            String[] temp = userInput.split("orders/", 2);
            String[] details = temp[0].split("\\s+");
            if (userInput.trim().charAt(10) == ' ' && details.length == 5) {
                String customerName = details[1].trim();
                String customerContact = details[2].trim();
                String numberOfPax = details[3].trim();
                String bookingDate = details[4].trim();
                String orderName = temp[1].trim();

                if (isValidName(customerName)) {
                    if (isValidContactNo((customerContact))) {
                        if (isParsable(numberOfPax)) {
                            if (isValidPax(numberOfPax)) {
                                if (isDateParsable(bookingDate)) {
                                    if (isValidDate(bookingDate)) {
                                        if (isAvailableDate(bookingDate, bookingList)) {
                                            if (!orderName.isEmpty()) {
                                                bookingList.addBooking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                                                bookingStorage.saveFile(bookingList);

                                                int size = bookingList.getSize();
                                                if (size == 1) {
                                                    msg = " booking in the list.";
                                                } else {
                                                    msg = " bookings in the list.";
                                                }
                                                arrayList.add(MESSAGE_BOOKING_ADDED + "       " + bookingList.getBookingList().get(size - 1) + "\n" + "Now you have " + size + msg);
                                            } else {
                                                arrayList.add(ERROR_MESSAGE_EMPTY_ORDERS);
                                            }
                                        } else {
                                            arrayList.add(ERROR_MESSAGE_UNAVAILABLE_DATE);
                                        }
                                    } else {
                                        arrayList.add(ERROR_MESSAGE_OVERFLOW_DATE);
                                    }
                                } else {
                                    arrayList.add(ERROR_MESSAGE_INVALID_DATE);
                                }
                            } else {
                                arrayList.add(ERROR_MESSAGE_INVALID_PAX);
                            }
                        } else {
                            arrayList.add(ERROR_MESSAGE_UNKNOWN_PAX);
                        }
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_CONTACT_NO);
                    }
                } else {
                    arrayList.add(ERROR_MESSAGE_INVALID_NAME);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_BOOKING_DETAILS);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_INVALID_BOOKING_DETAILS);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

}