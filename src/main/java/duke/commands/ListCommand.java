package duke.commands;

import duke.Storage;
import duke.Ui;
import duke.components.SongList;

public class ListCommand extends Command<SongList> {

    /**
     * Modifies the task list in use and returns the messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) {
        return ui.formatList(songList.getSongList());
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
