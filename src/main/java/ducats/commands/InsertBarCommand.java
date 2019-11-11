package ducats.commands;

import ducats.Ducats;
import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Song;
import ducats.components.SongList;

import java.io.IOException;
import java.util.ArrayList;

//@@author jwyf
/**
 * A class representing the command to insert a new bar of notes between existing bars in the current song.
 */
public class InsertBarCommand extends Command {
    private int songIndex;

    /**
     * Constructor for the command to insert a new bar to the current song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public InsertBarCommand(String message) {
        this.message = message;
        this.songIndex = 0;
    }

    /**
     * Modifies a song in the song list by inserting a new bar between existing bars and
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

            String[] sections = message.substring(10).split(" ");

            if (sections[0].isBlank() || sections[1].isBlank()) {
                throw new DucatsException(message, "insertbar");
            }
            barNo = Integer.parseInt(sections[0]);
            int notesIndex = message.indexOf(sections[1]);
            Bar newBar = new Bar(barNo, message.substring(notesIndex));

            activeSong.getBars().add(barNo - 1, newBar);

            storage.updateFile(songList);
            ArrayList<Song> temp = songList.getSongList();
            return ui.formatInsertBar(temp, newBar, activeSong);

        } catch (IndexOutOfBoundsException e) {
            throw new DucatsException("", "index");
        } catch (NumberFormatException e) {
            throw new DucatsException("", "number_index");
        } catch (Exception e) {
            if (e instanceof DucatsException && ((DucatsException) e).getType().equals("io")) {
                throw new DucatsException("", "io");
            } else {
                throw new DucatsException(message, "insertbar");
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
