package control;

import command.Command;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
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
    private RoomList roomList;
    private Ui ui;
    private User user;
    private boolean isExit;
    private Storage roomStorage;
    private Inventory inventory;
    private Storage inventoryStorage;


    /**
     * Constructor for control.Duke
     * @param bookingListFile path of text file containing bookings list
     */
    public Duke(String bookingListFile, String roomListFile, String inventoryFile) {
        ui = new Ui();
        ui.showWelcome();
        user = new Guest("guest");
        bookingStorage = new Storage(bookingListFile);
        roomStorage = new Storage(roomListFile);
        inventoryStorage = new Storage(inventoryFile);

        try {
            bookingList = new BookingList(bookingStorage.load());
        } catch (FileNotFoundException | DukeException e) {
            ui.showLoadingError();
            bookingList = new BookingList();
        }
        try {
            roomList = new RoomList(roomStorage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            roomList = new RoomList();
        }
        try {
            inventory = new Inventory(inventoryStorage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            inventory = new Inventory();
        }
    }

    /**
     * gets response.
     * @param input from user
     * @return respons from chatbot
     */
    public String getResponse(String input) {
        try {
            ui.setOutput("");
            Command c = Parser.parse(input, user.getLoginStatus());
            c.execute(inventory, roomList, bookingList, ui, inventoryStorage, bookingStorage, roomStorage, user);
            System.out.println(ui.getOutput());
            return ui.getOutput();
        } catch (DukeException | IOException | ParseException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public BookingList getBookingList() {
        return bookingList;
    }

    public RoomList getRoomList() {
        return roomList;
    }
}