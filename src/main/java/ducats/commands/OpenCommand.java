package ducats.commands;

import ducats.DucatsException;
import ducats.DucatsLogger;
import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;

//@@author rohan-av

public class OpenCommand extends Command {

    public OpenCommand(String message) {
        this.message = message;
    }

    /**
     * Opens a specified song for editing based on the input message.
     *
     * @param songList the the ducats.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in the GUI of Ducats
     * @throws DucatsException exception in the case of a wrong index provided by the user
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        try {
            String songName = message.substring(5);
            int songIndex = songList.findSongIndex(songName);
            if (songIndex != -1) {
                songList.setActiveIndex(songList.findSongIndex(songName));
                DucatsLogger.fine("The active index of the songList has been set to "
                        + songIndex
                        + " ("
                        + songName
                        + ")");
            } else {
                DucatsLogger.severe("Unable to set active index of songList to" + songIndex);
                throw new DucatsException("");
            }
            return Ui.formatOpen(songList, songIndex);
        } catch (Exception e) {
            DucatsLogger.severe("A song with the name " + message.substring(5) + "is not found");
            throw new DucatsException("","index");
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
