package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongList;

import java.util.ArrayList;

/**
 * A class representing the command to
 * group bars together as a verse.
 */
public class GroupCommand extends Command<SongList> {

    //@@author Samuel787
    private String message;

    /**
     * Constructor for the command to group bars together as a verse.
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public GroupCommand(String message) {
        this.message = message.trim();
    }

    /**
     * Saves the range of bars as a verse with the specified name and returns the messages intended to be displayed.
     *
     * @param songList the duke.TaskList or duke.components.SongList object that contains the task list in use
     * @param ui       the Ui object responsible for the reading of user input and the display of
     *                 the responses
     * @param storage  the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        if (message.length() < 6 || !message.substring(0, 6).equals("group ")) {
            //exception if not fully spelt
            throw new DucatsException(message, "group_format");
        }
        try {
            message = message.substring(6).trim();
            int startNo;
            int endNo;
            String name;
            String[] sections = message.split(" ");
            startNo = Integer.parseInt(sections[0]);
            endNo = Integer.parseInt(sections[1]);
            name = sections[2];
            createGroup(songList, startNo, endNo, name);
            updateSong(storage, songList);
            return ui.formatGroupBar(startNo, endNo, name);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DucatsException(message, "group");
        }
    }

    private void createGroup(SongList songList, int startNo, int endNo, String name) throws DucatsException {
        boolean nameAlreadyExists = groupNameExists(songList, name);
        if(songList.getSize() < 1){
            throw new DucatsException(message, "no_song_in_songlist");
        } else if (nameAlreadyExists){
            throw new DucatsException(message, "name_exists");
        } else {
            int activeSongIndex = songList.getActiveIndex();
            Song song = songList.getSongIndex(activeSongIndex);
            Group group = createGroup(song, name, startNo, endNo);
            songList.getSongIndex(songList.getActiveIndex()).getGroups().add(group);
        }
//        if (songList.getSize() > 0 && !nameAlreadyExists) {
//
//        } else {
//            throw new DucatsException(message, "name_exists");
//        }
    }

    private void updateSong(Storage storage, SongList songList) throws DucatsException{
        try {
            storage.updateFile(songList);
        } catch (Exception e) {
            throw new DucatsException(message, "group");
        }
    }

    private boolean groupNameExists(SongList songList, String newGroupName) {
        ArrayList<Group> groups = songList.getSongIndex(songList.getActiveIndex()).getGroups();
        for (Group group : groups) {
            if (group.getName().equals(newGroupName)) {
                return true;
            }
        }
        return false;
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

    private Group createGroup(Song song, String name, int start, int end) throws DucatsException {
        //maybe can begin off by seeing if the said group already exists

        //check that the bounds are valid
        if (start < 1 || end > song.getNumBars()) {
            throw new DucatsException("", "no_index");
        }

        ArrayList<Bar> myBars = new ArrayList<>();
        ArrayList<Bar> songBars = song.getBars();
        for (int i = start - 1; i < end; i++) {
            myBars.add(songBars.get(i));
        }
        return new Group(name, myBars);
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
