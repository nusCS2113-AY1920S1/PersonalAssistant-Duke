package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.Song;
import duke.components.SongList;

//@@author jwyf
/**
 * A class that represents the command to delete an song from the song list.
 */
public class DeleteCommand extends Command<SongList> {

    private int songIndex;

    /**
     * Constructor for the duke.Commands.Command created to delete a song from the duke.SongList
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     * @throws DukeException if an exception occurs in the parsing of the message
     */
    public DeleteCommand(String message) throws DukeException {
        this.message = message;
        try {
            songIndex = Integer.parseInt(message.substring(7));
        } catch (Exception e) {
            throw new DukeException("","other");
        }
    }

    /**
     * Modifies the song list in use and returns the messages intended to be displayed.
     *
     * @param songList the duke.SongList object that contains the song list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DukeException {
        if (songList.getSize() == 0) {
            throw new DukeException("", "empty");
        }
        if (songIndex > songList.getSize() || songIndex < 1) {
            throw new DukeException("", "index");
        } else {
            Song deletedSong = songList.getSongIndex(songIndex - 1);
            songList.remove(songIndex - 1);
            try {
                storage.updateFile(songList);
            } catch (Exception e) {
                throw new DukeException("","io");
            }
            return ui.formatDelete(songList, deletedSong);
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
