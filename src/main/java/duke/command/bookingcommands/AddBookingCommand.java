package duke.command.bookingcommands;

import duke.list.bookinglist.BookingList;
import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public class AddBookingCommand extends CommandBooking {

    public AddBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] temp = userInputCommand.split("\\s",6);
        if(userInputCommand.trim().equals("addbooking")) {
            arrayList.add("Booking details cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       addbooking <customer_name> <customer_contact> <number_of_pax> <booking_date_dd/MM/yyyy> <order_name>");
        } else if(userInputCommand.trim().charAt(10) == ' ' && temp.length == 6) {
            String customerName = temp[1].trim();
            String customerContact = temp[2].trim();
            String numberOfPax = temp[3].trim();
            String bookingDate = temp[4].trim();
            String orderName = temp[5].trim();

            bookingList.addBooking(customerName, customerContact, numberOfPax, bookingDate, orderName);
            bookingStorage.saveFile(bookingList);

            arrayList.add("New booking added:\n" + "       " + bookingList.getBookingList().get(bookingList.getSize() - 1) + "\n" + "     Now you have " + bookingList.getSize() + " bookings in the list.");

        }else {
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