package control;

import command.Command;
import exception.DukeException;
import room.RoomList;
import storage.BookingConstants;
import storage.Constants;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import user.BookingList;

import java.io.*;
import java.text.ParseException;

/**
 * Main control.Duke class
 * control.Duke is a chatbot that manage tasks for the user
 */
public class Duke {
    private Storage taskStorage, bookingStorage;
    private TaskList tasks;
    private BookingList bookingList;
    private Ui ui;
    private boolean isExit;
    private Storage roomStorage;
    private RoomList rooms;

    /**
     * Constructor for control.Duke
     * @param taskListFile path of text file containing task list
     */
    public Duke(String taskListFile, String bookingListFile, String roomListFile) {
        ui = new Ui();
        ui.showWelcome();
        taskStorage = new Storage(taskListFile);
        bookingStorage = new Storage(bookingListFile);
        roomStorage = new Storage(roomListFile);
        try {
            tasks = new TaskList(taskStorage.load());
            bookingList = new BookingList(bookingStorage.load());
            rooms = new RoomList(roomStorage.load());

        } catch (FileNotFoundException | DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
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
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, taskStorage);
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
        new Duke(Constants.FILENAME, BookingConstants.FILENAME, Constants.ROOMFILENAME).run();
    }

    public String getResponse(String input) {
        try {
            ui.setOutput("");
            Command c = Parser.parse(input);
            c.execute(tasks, ui, taskStorage);
            return ui.getOutput();
        } catch (DukeException | IOException | ParseException e) {
            return e.getMessage();
        }
    }
}