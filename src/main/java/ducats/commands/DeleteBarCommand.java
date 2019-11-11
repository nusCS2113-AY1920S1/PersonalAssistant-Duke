package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;

//@@author jwyf
/**
 * A class that represents the command to delete an bar from a song.
 */
public class DeleteBarCommand extends Command {

    /**
     * Constructor for the duke.Commands.Command created to delete a bar from a song
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     * @throws DucatsException if an exception occurs in the parsing of the message
     */
    public DeleteBarCommand(String message) throws DucatsException {
        this.message = message;
    }

    /**
     * Modifies a song in the song list by deleting an existing bar and
     * returns the messages intended to be displayed.
     *
     * @param songList the duke.SongList object that contains the song list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        int barIndex = -1;
        try {
            if (songList.getSize() == 0) {
                throw new DucatsException("", "empty");
            }

            int songIndex = songList.getActiveIndex();
            Song activeSong = songList.getSongIndex(songIndex);

            if (message.substring(10).isBlank()) {
                throw new DucatsException(message, "deletebar");
            }

            barIndex = Integer.parseInt(message.substring(10));

            activeSong.getBars().remove(barIndex - 1);
            storage.updateFile(songList);
            return ui.formatDeleteBar(activeSong, barIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new DucatsException("", "index");
        } catch (NumberFormatException e) {
            throw new DucatsException("", "number_index");
        } catch (Exception e) {
            if (e instanceof DucatsException && ((DucatsException) e).getType().equals("io")) {
                throw new DucatsException("", "io");
            } else {
                throw new DucatsException(message, "deletebar");
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
