package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;

import java.util.ArrayList;

//@@author jwyf
/**
 * A class representing the command to view the last bar of a song from the song list.
 */
public class ViewCommand extends Command {

    /**
     * Constructor for the command to view a song from the song list.
     * @param message the input message that resulted in the creation of the ducats.Commands.Command
     */
    public ViewCommand(String message) {
        this.message = message;
    }

    /**
     * Access the song list and returns the song intended to be displayed.
     *
     * @param songList the ducats.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in ducats.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        String songName;
        songName = message.substring(5).trim();
        ArrayList<Song> findList = songList.findSong(songName);

        if (findList.size() != 1) {
            throw new DucatsException(message, "view");
        } else {
            Song selectedSong = findList.get(0);
            int barSize = selectedSong.getBars().size();

            OpenCommand openCommand = new OpenCommand("open " + selectedSong.getName());
            openCommand.execute(songList, ui, storage);

            AsciiCommand asciiCommand = new AsciiCommand("ascii bar " + Integer.toString(barSize));
            String result = asciiCommand.execute(songList, ui, storage);
            return ui.formatView(result);
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * ducats.Duke to reassign a boolean variable checked at each iteration of a while loop.
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
