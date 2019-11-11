package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;

//@@author SalonetheGreat
/**
 * A class representing the command to display help: the command list.
 */
public class HelpCommand extends Command<SongList> {

    //@@author SalonetheGreat
    /**
     * Constructor for the command to display help.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public HelpCommand(String message) {
        this.message = message;
    }

    //@@author SalonetheGreat
    /**
     * Displays the command list in use; returns the help messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        if (message.length() == 4) {
            return ui.formatHelp();
        }
        String helpMessage = message.substring(4).trim();
        return ui.formatHelp(helpMessage);
    }

    //@@author SalonetheGreat
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
