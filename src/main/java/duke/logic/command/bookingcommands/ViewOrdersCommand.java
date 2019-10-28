package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

public class ViewOrdersCommand extends Command<BookingList, Ui, BookingStorage> {

    public ViewOrdersCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals("vieworders")) {
            arrayList.add("Customer name cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       vieworders <customer_name>");
        } else if (userInput.trim().charAt(10) == ' ') {
            String customerName = userInput.split("\\s", 2)[1].trim().toLowerCase();
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
