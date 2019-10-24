package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.Bar;
import duke.components.Song;
import duke.components.SongList;

/**
 * A class that represents the command to delete an bar from a song.
 */
public class DeleteBarCommand extends Command<SongList> {
    private int barIndex;

    /**
     * Constructor for the duke.Commands.Command created to delete a bar from a song
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     * @throws DukeException if an exception occurs in the parsing of the message
     */
    public DeleteBarCommand(String message) throws DukeException {
        this.message = message;
        try {
            barIndex = Integer.parseInt(message.substring(10));
        } catch (Exception e) {
            throw new DukeException("","other");
        }
    }

    /**
     * Modifies the song and returns the messages intended to be displayed.
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
        if (barIndex > songList.getSongIndex(0).getBars().size() || barIndex < 1) {
            throw new DukeException("", "index");
        } else {
            Song song = songList.getSongIndex(0);
            Bar deletedBar = song.getBars().get(barIndex - 1);
            song.getBars().remove(barIndex - 1);
            try {
                storage.updateFile(songList);
            } catch (Exception e) {
                throw new DukeException("","io");
            }
            return ui.formatDeleteBar(song, deletedBar);
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
