package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.BookingMessages.*;

/**
 * Handles the view orders command by searching with customer name.
 */
public class ViewOrdersCommand extends Command<BookingList, Ui, BookingStorage> {

    /**
     * Constructor for class ViewOrdersCommand.
     *
     * @param userInput string containing the input from the user
     */
    public ViewOrdersCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the view orders command to display the orders for a particular customer.
     *
     * @param bookingList    contains the booking list
     * @param ui             deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving bookings in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_VIEW_ORDERS)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_NAME_VIEW);
        } else if (userInput.trim().charAt(10) != ' ') {
            arrayList.add(ERROR_MESSAGE_INVALID_VIEWORDERS_COMMAND);
        } else {
            String customerName = userInput.split("\\s", 2)[1].trim().toLowerCase();
            if (!isValidName(customerName)) {
                arrayList.add(ERROR_MESSAGE_INVALID_NAME);
            } else {
                arrayList.add(MESSAGE_ORDERS_FOR + customerName);
                arrayList.addAll(bookingList.viewOrders(customerName));
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

    @Override
    public boolean isExit() {
        return false;
    }

}
