package duke.command.bookingcommands;

import duke.list.bookinglist.BookingList;
import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddBookingCommand extends CommandBooking {

    private static String msg = "";

    public AddBookingCommand(String userInputCommand){
            this.userInputCommand = userInputCommand;
    }

    private static boolean isDateParsable(String bookingDate) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] temp = userInputCommand.split("\\s", 6);
        if (userInputCommand.trim().equals("addbooking")) {
            arrayList.add("Booking details cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       addbooking <customer_name> <customer_contact> <number_of_pax> <booking_date_dd/MM/yyyy> <order_name>");
        } else if (userInputCommand.trim().charAt(10) == ' ' && temp.length == 6) {
            String customerName = temp[1].trim();
            String customerContact = temp[2].trim();
            String numberOfPax = temp[3].trim();
            String bookingDate = temp[4].trim();
            String orderName = temp[5].trim();

            if(isDateParsable(bookingDate)) {
                bookingList.addBooking(customerName, customerContact, numberOfPax, bookingDate, orderName);
                bookingStorage.saveFile(bookingList);

                int size = bookingList.getSize();
                if (size == 1) {
                    msg = " booking in the list.";
                } else {
                    msg = " bookings in the list.";
                }
                arrayList.add("New booking added:\n" + "       " + bookingList.getBookingList().get(size - 1) + "\n" + "Now you have " + size + msg);
            }else {
                arrayList.add("Invalid booking date entered.\n Please enter again in the format: dd/MM/yyyy");
            }
        } else {
            arrayList.add("Incorrect Booking details.\n" +
                    "       Please enter in the following format:\n" +
                    "       addbooking <customer_name> <customer_contact> <number_of_pax> <booking_date_dd/MM/yyyy> <order_name>");
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

}