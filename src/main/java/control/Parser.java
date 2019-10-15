package control;

import command.*;
import exception.DukeException;
import room.AddRoom;

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
    public static Command parse(String input, boolean loginStatus) throws DukeException, IOException {
        String[] splitStr = input.split(" ");
        switch (splitStr[0]) {
        case "bye":
            return new ByeCommand();
        case "login":
            return new LoginCommand(input, splitStr);
        case "create":
            return new CreateAccountCommand(input, splitStr);
        case "add":
            return new AddBookingCommand(input, splitStr);
        case "addroom":
            return new AddRoomCommand(input, splitStr);
        case "list":
            return new ListCommand();
        default:
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
