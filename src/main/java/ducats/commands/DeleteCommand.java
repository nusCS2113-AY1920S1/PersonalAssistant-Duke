package ducats.commands;

import ducats.Ducats;
import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;

//@@author jwyf
/**
 * A class that represents the command to delete a song from the song list.
 */
public class DeleteCommand extends Command {

    /**
     * Constructor for the ducats.Commands.Command created to delete a song from the ducats.SongList
     *
     * @param message the input message that resulted in the creation of the ducats.Commands.Command
     * @throws DucatsException if an exception occurs in the parsing of the message
     */
    public DeleteCommand(String message) throws DucatsException {
        this.message = message;
    }

    /**
     * Deletes an existing song in the song list and
     * returns the messages intended to be displayed.
     *
     * @param songList the ducats.SongList object that contains the song list
     * @param ui the Ui object that determines the displayed output of ducats.Duke
     * @param storage the storage
     * @return the string to be displayed in ducats.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        int songIndex = -1;
        try {
            if (songList.getSize() == 0) {
                throw new DucatsException("", "empty");
            }
            if (message.substring(7).isBlank()) {
                throw new DucatsException(message, "delete");
            }
            try {
                songIndex = Integer.parseInt(message.substring(7));
            } catch (NumberFormatException e) {
                songIndex = songList.findSongIndex(message.substring(7)) + 1;
            } catch (Exception e) {
                throw new DucatsException(message, "delete");
            }

            Song deletedSong = songList.getSongIndex(songIndex - 1);
            songList.remove(songIndex - 1);
            storage.updateFile(songList);
            return ui.formatDelete(songList, deletedSong);

        } catch (IndexOutOfBoundsException e) {
            throw new DucatsException("", "index");
        } catch (NumberFormatException e) {
            throw new DucatsException("", "number_index");
        } catch (Exception e) {
            if (e instanceof DucatsException && ((DucatsException) e).getType().equals("io")) {
                throw new DucatsException("", "io");
            } else {
                throw new DucatsException(message, "delete");
            }
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
