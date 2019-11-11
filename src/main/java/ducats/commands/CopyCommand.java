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
 * A class representing the command to copy bars or verses and paste them within the same track.
 */
public class CopyCommand extends Command {

    //@@author Samuel787
    private String message;
    private Song song;
    private Storage storage;
    private SongList songList;

    /**
     * Constructor for the command to copy and paste bars or verse.
     *
     * @param message the input message that resulted in the creation of the ducats.Commands.Command
     */
    public CopyCommand(String message) {
        this.message = message.trim();
    }


    /**
     * Copy bars between a certain range and paste it between a certain range
     * in song creator.
     * The possible combinations are:
     * copy start_index end_index paste_index
     * copy start_index end_index
     * copy group_name paste_index
     * copy group_name
     *
     * @param songList the ducats.TaskList or ducats.components.SongList object that contains the task list in use
     * @param ui       the Ui object responsible for the reading of user input and the display of
     *                 the responses
     * @param storage  the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in ducats.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        this.storage = storage;
        this.songList = songList;
        if (message.length() < 5
                || !message.substring(0, 4).equals("copy")
                || songList.getSize() == 0) {
            throw new DucatsException(message);
        }
        int activeSong = songList.getActiveIndex();
        song = songList.getSongIndex(activeSong);

        message = message.substring(5).trim();
        String[] sections = message.split(" ");
        if (sections.length < 1 || sections.length > 3) {
            throw new DucatsException(message, "copy");
        }
        //trimmer
        for (int i = 0; i < sections.length; i++) {
            sections[i] = sections[i].trim();
        }
        if (sections.length == 1) {
            //copy the verse to the end of the song list
            String verseName = sections[0];
            copyVerseToEnd(verseName);
            return ui.formatCopy(verseName, null, null, null, 0);
        } else if (sections.length == 2) {
            try {
                //copy the bars between the starting num and ending num to the end of the song
                int startNum = Integer.parseInt(sections[0]);
                int endNum = Integer.parseInt(sections[1]);
                copyBarsToEnd(startNum, endNum);
                return ui.formatCopy(null, startNum, endNum, null, 1);
            } catch (NumberFormatException e) {
                //copy the verse and insert it between the numbers stated
                String verseName = sections[0].trim();
                int startNum = Integer.parseInt(sections[1]);
                insertVerse(verseName, startNum);
                return ui.formatCopy(verseName, null, null, startNum, 2);
            }
        } else {
            //copy the bars between the a range into a new range
            int copyStartNum = Integer.parseInt(sections[0]);
            int copyEndNum = Integer.parseInt(sections[1]);
            int pasteStartNum = Integer.parseInt(sections[2]);
            insertCopiedBars(copyStartNum, copyEndNum, pasteStartNum);
            return ui.formatCopy(null, copyStartNum, copyEndNum, pasteStartNum, 3);
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
     * Inserts a verse to the end of the current song.
     * Usage: copy verse_name
     *
     * @param name name of the verse to be added to the end of the current song
     * @throws DucatsException if the verse doesn't exist
     */
    private void copyVerseToEnd(String name) throws DucatsException {
        ArrayList<Group> groupList = song.getGroups();

        Group copyGroup = null;
        for (Group group : groupList) {
            if (group.getName().equals(name)) {
                copyGroup = group;
                break;
            }
        }
        if (copyGroup == null) {
            throw new DucatsException("", "group_not_found");
        }

        for (int i = 0; i < copyGroup.size(); i++) {
            song.addBar(copyGroup.get(i));
        }
        //add the bar to the song in the songlist
        storage.updateFile(songList);
    }

    /**
     * Inserts a verse into a particular index of the song.
     * For the user, the index of the song starts from 1.
     *
     * @param name name of the verse to be inserted into the song
     * @param i    index of the song into which the verse is to be inserted
     * @throws DucatsException if verse doesn't exist or if the index to insert is out of range
     */
    private void insertVerse(String name, int i) throws DucatsException {
        if (i < 1 || i > song.getNumBars()) {
            throw new DucatsException("", "no_index");
        }

        ArrayList<Group> groupList = song.getGroups();

        Group copyGroup = null;
        for (Group group : groupList) {
            if (group.getName().equals(name)) {
                copyGroup = group;
                break;
            }
        }
        if (copyGroup == null) {
            throw new DucatsException("", "group_not_found");
        }

        ArrayList<Bar> songBars = song.getBars();
        ArrayList<Bar> newSongBars = new ArrayList<>();
        for (int j = 0; j < i - 1; j++) {
            newSongBars.add(songBars.get(j));
        }
        ArrayList<Bar> tempBars = new ArrayList<>();
        for (int j = i - 1; j < songBars.size(); j++) {
            tempBars.add(songBars.get(j));
        }
        for (int j = 0; j < copyGroup.size(); j++) {
            newSongBars.add(copyGroup.get(j));
        }
        for (int j = 0; j < tempBars.size(); j++) {
            newSongBars.add(tempBars.get(j));
        }
        song.updateBars(newSongBars);
        //add the bar to the song in the songlist
        storage.updateFile(songList);
    }

    /**
     * Copies some bars in the user defined range to the end of the current song.
     *
     * @param copyStart index of the song to start copying from. Index for the user starts from 1
     * @param copyEnd   index of the song to end copying. Index for the user starts at 1.
     * @throws DucatsException if the index to start copying from is more than the index to end
     *                       copying from or if the index specified is out of range of the song.
     */
    private void copyBarsToEnd(int copyStart, int copyEnd) throws DucatsException {
        int songNumBars = song.getNumBars();
        if (copyStart < 1 || copyEnd < 1
                || copyStart > songNumBars
                || copyEnd < copyStart) {
            throw new DucatsException("", "no_index");
        }
        ArrayList<Bar> copyBars = new ArrayList<>();
        ArrayList<Bar> songBars = song.getBars();
        for (int i = copyStart - 1; i < copyEnd; i++) {
            copyBars.add(songBars.get(i));
        }
        for (int i = 0; i < copyBars.size(); i++) {
            song.addBar(copyBars.get(i));
        }
        storage.updateFile(songList);
    }

    /**
     * Copies some bars in the user defined range and inserts the copied bars into a user defined index.
     * The bars following that index will be pushed forward to make space for the copied bars.
     *
     * @param copyStart  index of the song to start copying from. Index for the user starts from 1
     * @param copyEnd    index of the song to end copying. Index for the user starts at 1
     * @param pasteStart index of the song at which to paste the copied bars. Bars folowing this point will be
     *                   pushed forward to make space for the copied bars.
     * @throws DucatsException if the index to start copying from is more than the index to end copying from or
     *                       if the index specified is out of range of the song or if starting index to paste is out of
     *                       range of the current song
     */
    private void insertCopiedBars(int copyStart, int copyEnd, int pasteStart) throws DucatsException {
        int songNumBars = song.getNumBars();
        if (copyStart < 1 || copyEnd < 1 || pasteStart < 1
                || copyStart > songNumBars || pasteStart > songNumBars
                || copyEnd < copyStart) {
            throw new DucatsException("", "no_index");
        }
        ArrayList<Bar> copyBars = new ArrayList<>();
        ArrayList<Bar> songBars = song.getBars();
        ArrayList<Bar> tempBars = new ArrayList<>();
        ArrayList<Bar> newSongBars = new ArrayList<>();

        for (int i = copyStart - 1; i < copyEnd; i++) {
            copyBars.add(songBars.get(i));
        }

        for (int i = pasteStart - 1; i < songBars.size(); i++) {
            tempBars.add(songBars.get(i));
        }

        for (int i = 0; i < pasteStart - 1; i++) {
            newSongBars.add(songBars.get(i));
        }

        for (int i = 0; i < copyBars.size(); i++) {
            newSongBars.add(copyBars.get(i));
        }

        for (int i = 0; i < tempBars.size(); i++) {
            newSongBars.add(tempBars.get(i));
        }

        song.updateBars(newSongBars);
        storage.updateFile(songList);
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
