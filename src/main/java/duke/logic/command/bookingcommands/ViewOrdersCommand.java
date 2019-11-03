package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.BookingMessages.*;

public class ViewOrdersCommand extends Command<BookingList, Ui, BookingStorage> {

    public ViewOrdersCommand(String userInput) {
        this.userInput = userInput;
    }

    private static boolean isAlphabet(String input) {
        char firstChar = input.charAt(0);
        if (Character.isLetter(firstChar)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_VIEW_ORDERS)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_NAME_VIEW);
        } else if (userInput.trim().charAt(10) == ' ') {
            String customerName = userInput.split("\\s", 2)[1].trim().toLowerCase();
            if (isAlphabet(customerName)) {
                arrayList.add(MESSAGE_ORDERS_FOR + customerName);
                arrayList.addAll(bookingList.viewOrders(customerName));
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_NAME);
            }

        } else {
            arrayList.add(ERROR_MESSAGE_INVALID_VIEWORDERS_COMMAND);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
