package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;

public class MetronomeCommand extends Command {

    private int duration;
    private int tempo;
    private int[] timeSig;


    //@@author rohan-av
    public MetronomeCommand(String message) {
        this.message = message;
    }

    /**
     * Executes the command, which consists of setting the command attributes (to be later passed into the Metronome
     * class as its parameters) and retrieving the appropriate output string from the UI class.
     *
     * @param songList the list of Songs in Ducats
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the formatted String to be displayed
     * @throws DucatsException in the case of input errors on the side of the user
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        try {
            String[] sections = message.substring(10).split(" ");
            duration = Integer.parseInt(sections[0]);
            tempo = Integer.parseInt(sections[1]);
            timeSig =
                new int[]{Integer.parseInt(sections[2].substring(0, 1)), Integer.parseInt(sections[2].substring(2))};
            return ui.formatMetronome(duration, tempo, timeSig);
        } catch (Exception e) {
            throw new DucatsException("", "create");
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
        return new int[]{duration, tempo, timeSig[0], timeSig[1]};
    }


}
