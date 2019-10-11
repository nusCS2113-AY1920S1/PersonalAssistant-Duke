package duke.command.bookingcommands;

import duke.bookinglist.BookingList;
import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;

public class AddBookingCommand extends Command {

    public AddBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException {
        String[] temp = userInputCommand.split("\\s",6);
        if(userInputCommand.trim().equals("addbooking")) {
            throw new DukeException("Booking details cannot be empty!\n" +
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

        }else {
            throw new DukeException("Incorrect Booking details.\n" +
                    "       Please enter in the following format:\n" +
                    "       addbooking <customer_name> <customer_contact> <number_of_pax> <booking_date_dd/MM/yyyy> <order_name>");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}