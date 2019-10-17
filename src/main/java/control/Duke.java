package control;

import command.Command;
import exception.DukeException;
import room.RoomList;
import storage.BookingConstants;
import storage.Constants;
import storage.Storage;
import ui.Ui;
import booking.BookingList;
import user.Guest;
import user.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Main control.Duke class
 * control.Duke is a chatbot that manage tasks for the user
 */
public class Duke {
    private Storage bookingStorage;
    private BookingList bookingList;
    private Ui ui;
    private User user;
    private boolean isExit;
    private Storage roomStorage;
    private RoomList roomList;

    /**
     * Constructor for control.Duke
     * @param bookingListFile path of text file containing bookings list
     */
    public Duke(String bookingListFile, String roomListFile) {
        ui = new Ui();
        ui.showWelcome();
        user = new Guest("guest");
        bookingStorage = new Storage(bookingListFile);
        roomStorage = new Storage(roomListFile);
        try {
            bookingList = new BookingList(bookingStorage.load());
            roomList = new RoomList(roomStorage.load());

        } catch (FileNotFoundException | DukeException e) {
            ui.showLoadingError();
            bookingList = new BookingList();
        }
    }

    /**
     *  Main control.Duke logic run here
     */
    public void run() {
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showUserInput(fullCommand);
                Command c = Parser.parse(fullCommand, user.getLoginStatus());
                c.execute(bookingList, ui, bookingStorage, user);
                c.execute(roomList, ui, roomStorage);
                isExit = c.isExit();
            } catch (DukeException | IOException | ParseException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Main function of control.Duke
     * @param args input from command line
     */
    public static void main(String[] args) {
        new Duke(BookingConstants.FILENAME, Constants.ROOMFILENAME).run();
    }

    public String getResponse(String input) {
        try {
            ui.setOutput("");
            Command c = Parser.parse(input, user.getLoginStatus());
            c.execute(bookingList, ui, bookingStorage, user);
            c.execute(roomList, ui, roomStorage);
            System.out.println(ui.getOutput());
            return ui.getOutput();
        } catch (DukeException | IOException | ParseException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}