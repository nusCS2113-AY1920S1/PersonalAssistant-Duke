package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.components.Song;
import duke.tasks.Task;

import java.util.ArrayList;

/**
 * A class representing the command to view a song from the song list.
 */
public class ViewCommand extends Command {

    /**
     * Constructor for the command to view a song from the song list.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public ViewCommand(String message) {
        this.message = message;
    }

    /**
     * Access the song list and returns the song intended to be displayed.
     *
     * @param taskList the duke.TaskList object that contains the task list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        String songName;
        if (message.length() < 5 || !message.substring(0, 5).equals("view ")) { //exception if not fully spelt
            throw new DukeException(message);
        }
        Song song;
        songName = message.substring(5).trim();
        ArrayList<Task> songList = new ArrayList<>();
        songList = taskList.findTask(songName);

        if (songList.size() != 1) {
            throw new DukeException(message, "view");
        } else {
            return ui.formatView(songList.get(0));
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
