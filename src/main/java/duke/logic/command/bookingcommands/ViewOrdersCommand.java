package duke.logic.command.bookingcommands;

import duke.logic.command.CommandBooking;
import duke.exception.DukeException;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

public class ViewOrdersCommand extends CommandBooking {

    public ViewOrdersCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInputCommand.trim().equals("vieworders")) {
            arrayList.add("Customer name cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       vieworders <customer_name>");
        } else if (userInputCommand.trim().charAt(10) == ' ') {
            String customerName = userInputCommand.split("\\s", 2)[1].trim().toLowerCase();
            arrayList.add("     Here are your orders for: " + customerName);
            arrayList.addAll(bookingList.viewOrders(customerName));
        } else {
            arrayList.add("Incorrect view orders command.\n " +
                    "       Please enter in the following format:\n" +
                    "       vieworders <customer_name>");
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
