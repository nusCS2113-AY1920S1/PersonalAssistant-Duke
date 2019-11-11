package ducats.commands;

import ducats.DucatsException;
import ducats.Parser;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Song;
import ducats.components.SongList;

import java.util.ArrayList;

//@@author jwyf
/**
 * A class representing the command to add a new bar of notes at the end of the current song.
 */
public class AddBarCommand extends Command {

    private int songIndex;

    /**
     * Constructor for the command to add a new bar to the current song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AddBarCommand(String message) {
        this.message = message;
        this.songIndex = 0;
    }

    /**
     * Modifies a song in the song list by adding a new bar at the end of the song and
     * returns the messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        int barNo;
        try {
            songIndex = songList.getActiveIndex();
            Song activeSong = songList.getSongIndex(songIndex);

            barNo = activeSong.getNumBars() + 1;
            Bar newBar = new Bar(barNo, message.substring(7));
            activeSong.addBar(newBar);

            storage.updateFile(songList);
            ArrayList<Song> temp = songList.getSongList();
            return ui.formatAddBar(temp, newBar, activeSong);

        } catch (Exception e) {
            if (e instanceof DucatsException && ((DucatsException) e).getType().equals("io")) {
                throw new DucatsException("", "io");
            } else {
                throw new DucatsException(message, "addbar");
            }
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

    //@@author rohan-av
    /**
     * Returns an integer corresponding to the duration, tempo and time signature if the command starts a metronome.
     * Else, returns an array containing -1.
     *
     * @return the integer array corresponding to the parameters of the Metronome class
     */
    @Override
    public int[] startMetronome() {
        return new int[]{-1, -1, -1, -1};
    }
}
