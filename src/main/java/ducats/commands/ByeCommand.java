package ducats.commands;

import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;

/**
 * A class representing the command to terminate and exit ducats.Duke.
 */



public class ByeCommand extends Command {

    private boolean exit = false;

    /**
     * Executes the command and terminates ducats.Duke.
     *
     * @param songList the ducats.components.SongList object that contains the task list
     * @param ui the Ui object that determines the displayed output of ducats.Duke
     * @param storage the storage
     * @return the string to be displayed in ducats.Duke
     */
    public String execute(SongList songList, Ui ui, Storage storage) {
        exit = true;
        return Ui.showByeMessage();
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * ducats.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    public boolean isExit() {
        return exit;
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
