package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.model.task.bookingtasks.Booking;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static duke.common.BookingMessages.*;

public class AddBookingCommand extends Command<BookingList, Ui, BookingStorage> {

    private static String msg = "";

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

    private static boolean isValidPax(String input) {
        int pax = Integer.parseInt(input);
        if (pax > 0 && pax < 9) {
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
            return false;
        }
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
                        if (isValidPax(numberOfPax)) {
                            if (isDateParsable(bookingDate)) {
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
                                        arrayList.add(ERROR_MESSAGE_NO_ORDERS);
                                    }
                                } else {
                                    arrayList.add(ERROR_MESSAGE_UNAVAILABLE_DATE);
                                }

                            } else {
                                arrayList.add(ERROR_MESSAGE_INVALID_DATE);
                            }
                        } else {
                            arrayList.add(ERROR_MESSAGE_INVALID_PAX);
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