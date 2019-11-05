package control;

import command.*;
import exception.DukeException;
import storage.Constants;
import java.io.IOException;

/**
 * Parse input and execute respective user command.
 */
public class Parser {

    /**
     * Converts user input into commands for control.Duke.
     * @param input from user
     * @return Command to be executed
     * @throws DukeException if user enters wrong input format
     */
    public static Command parse(String input) throws DukeException, IOException {
        String[] splitStr = input.split(" ");
        switch (splitStr[0]) {
        case "bye":
            return new ByeCommand();
        case "login":
            return new LoginCommand(input, splitStr);
        case "logout":
            return new LogoutCommand();
        case "create":
            return new CreateAccountCommand(input, splitStr);
        case "add":
            return new AddBookingCommand(input, splitStr);
        case "addroom":
            return new AddRoomCommand(input, splitStr);
        case "list":
            return new ListCommand();
        case "listroom":
            return new ListRoomCommand();
        case "edit":
            return new EditBookingCommand(input, splitStr);
        case "approve":
            return new ApproveCommand(input, splitStr);
        case "reject":
            return new RejectCommand(input, splitStr);
        case "delete":
            return new DeleteBookingCommand(input, splitStr);
        case "findindex" :
            return new FindBookingIndexCommand(input, splitStr);
        case "find" :
            return new FindBookingCommand(input, splitStr);
        case "listday":
            return new ListBookingDailyCommand(input, splitStr);
        case "listmonth":
            return new ListBookingMonthCommand(input, splitStr);
        case "listyear":
            return new ListBookingYearCommand(input, splitStr);
        case "deleteroom":
            return new DeleteRoomCommand(input, splitStr);
        case "addinventory":
            return new AddInventoryCommand(input, splitStr);
        case "adduser":
                return new AddUserCommand(input, splitStr);
        case "rmuser":
                return new RemoveUserCommand(input, splitStr);

        default:
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
