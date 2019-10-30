package control;

import command.Command;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import booking.BookingList;
import user.User;
import user.UserList;

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
    private UserList userList;
    private Ui ui;
    private boolean isExit;
    private Storage roomStorage;
    private Inventory inventory;
    private Storage inventoryStorage;
    private Storage userStorage;


    /**
     * Constructor for control.Duke
     * @param bookingListFile path of text file containing bookings list
     */
    public Duke(String bookingListFile, String roomListFile, String inventoryFile, String userFile) {
        ui = new Ui();
        ui.showWelcome();
        bookingStorage = new Storage(bookingListFile);
        roomStorage = new Storage(roomListFile);
        inventoryStorage = new Storage(inventoryFile);
        userStorage = new Storage(userFile);

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
        try {
            userList = new UserList(userStorage.load());
        }catch (FileNotFoundException e) {
            ui.showLoadingError();
            userList = new UserList();
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
            Command c = Parser.parse(input);
            c.execute(userList, inventory, roomList, bookingList, ui, userStorage, inventoryStorage, bookingStorage, roomStorage);
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