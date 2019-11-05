package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;

public class ListGroupCommand extends Command<SongList> {

    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        return ui.formatListGroups(songList);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public int[] startMetronome() {
        return new int[]{-1, -1, -1, -1};
    }
}
