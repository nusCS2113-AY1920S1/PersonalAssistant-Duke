package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongList;

import java.util.ArrayList;

/**
 * A class representing the command to list all the groups of the active song.
 * It can also list specific groups based on a starting substring of the group name.
 */
public class ListGroupCommand extends Command<SongList> {

    //@@author Samuel787

    private String message;

    /**
     * Constructor for the command to list the groups in the current song.
     * @param message the input message by the user requesting to display the groups.
     */
    public ListGroupCommand(String message) {
        this.message = message.trim();
    }

    /**
     * Lists either all or some of the groups in the opened song as requested by user.
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return returns the list of groups as requested by the user
     * @throws DucatsException if an exception occurs in the parsing of the message or in the IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        if (message.length() < 10 || !message.substring(0, 10).equals("list_group")) {
            throw new DucatsException(message, "list_group");
        } else if (songList.getSize() == 0) {
            throw new DucatsException(message, "no_song_in_songlist");
        } else if (message.length() == 10) {
            int songIndex = songList.getActiveIndex();
            Song activeSong = songList.getSongIndex(songIndex);
            ArrayList<Group> groups = activeSong.getGroups();
            return ui.formatListGroups(groups);
        } else {
            String keyword = message.substring(10).trim();
            int songIndex = songList.getActiveIndex();
            Song activeSong = songList.getSongIndex(songIndex);
            ArrayList<Group> resultGroups = getGroupsStartingWith(activeSong, keyword);
            return ui.formatListGroups(resultGroups);
        }
    }

    /**
     * Lists the groups in the opened song that starts with the substring as requested by user.
     * @param song the current song opened
     * @param keyword the starting substring that should appear in the groups that
     *                user wants to find
     * @return the list of groups with the same starting substring as requested by user
     */
    private ArrayList<Group> getGroupsStartingWith(Song song, String keyword) {
        ArrayList<Group> allGroups = song.getGroups();
        ArrayList<Group> resultGroups = new ArrayList<>();
        for (Group group : allGroups) {
            if (group.getName().startsWith(keyword)) {
                resultGroups.add(group);
            }
        }
        return resultGroups;
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
