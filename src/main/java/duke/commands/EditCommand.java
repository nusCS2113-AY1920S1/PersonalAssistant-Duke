package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.Bar;
import duke.components.Song;
import duke.components.SongList;

import java.util.ArrayList;

//@@author jwyf
/**
 * A class representing the command to edit a bar of notes in the current song.
 */
public class EditCommand extends Command<SongList> {

    private int songIndex;

    /**
     * Constructor for the command to edit a bar in the current song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public EditCommand(String message) {
        this.message = message;
        this.songIndex = 0;
    }

    /**
     * Modifies the song in the song list and returns the messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DukeException {
        int barNo;
        try {
            String[] sections = message.substring(5).split(" ");
            barNo = Integer.parseInt(sections[0].substring(4));

            int notesIndex = message.indexOf(sections[1]);

            Bar newBar = new Bar(barNo, message.substring(notesIndex));

            Song song = songList.getSongIndex(songIndex);

            song.getBars().add(barNo - 1, newBar);
            Bar oldBar = song.getBars().get(barNo);
            song.getBars().remove(barNo);

            storage.updateFile(songList);
            ArrayList<Song> temp = songList.getSongList();
            return ui.formatEdit(oldBar, newBar, song);
        } catch (Exception e) {
            throw new DukeException(message, "edit");
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
