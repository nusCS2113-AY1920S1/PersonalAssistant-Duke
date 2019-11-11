package ducats.commands;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Group;
import ducats.components.Note;
import ducats.components.Song;
import ducats.components.Chord;
import ducats.components.SongList;

import java.util.Arrays;
import java.util.Iterator;
import ducats.commands.AsciiCommand;
import ducats.commands.Command;
import ducats.components.Combiner;
import ducats.DucatsLogger;

/**
 * A class that overlays a bar onto a group.
 */
public class OverlayBarGroup  extends Command<SongList>  {
    public String message;
    private int songIndex;

    /**
     * Constructor for the command overlay a bar onto a group.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public OverlayBarGroup(String message) {
        this.message = message;
    }

    private void repeatLoop(ArrayList<Group> groupList, int groupIndexToBeCopiedTo, Bar overlayingBar,
                            Combiner combine) {
        Iterator<Group> iterator1 = groupList.iterator();
        int i = 0;
        while (iterator1.hasNext()) {
            Group temp = iterator1.next();

            if (i >= groupIndexToBeCopiedTo) {

                Splitter splitItem = new Splitter("group split");
                ArrayList<Bar> barListCopiedTo = splitItem.splitObject(temp);
                Iterator<Bar> iteratorGroup = barListCopiedTo.iterator();
                while (iteratorGroup.hasNext()) {
                    Bar barCopiedTo = iteratorGroup.next();
                    combine.combineBar(overlayingBar, barCopiedTo);
                }
            }
            i += 1;
        }
    }

    /**
     * Overlays a bar onto a group from the same song.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */

    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        Note note1;
        Note note2;
        Note note3;
        Note note4;
        int barNo;

        if (message.length() < 18 || !message.substring(0, 17).equals("overlay_bar_group")) {
            //exception if not fully spelt
            DucatsLogger.severe("the parser wrongly identified " + message + " as overlay_bar_group");
            throw new DucatsException(message,"overlay_bar_group_format");
        }

        try {
            //overlay_bar_group 1 2 will overlay  bar 1 into group 2
            if (message.equals("overlay_bar_group")) {
                DucatsLogger.severe("insufficient amount of parameters given - overlay_bar_group");
                throw new DucatsException(message,"overlay_bar_group_format");
            }
            String[] sections1 = message.split("overlay_bar_group ");

            if (sections1.length < 2) {
                DucatsLogger.severe("insufficient amount of parameters given - overlay_bar_group");
                throw new DucatsException(message,"overlay_bar_group_format");
            }

            message = sections1[1];
            String[] sections = message.split(" ");

            if (sections.length < 2) {
                DucatsLogger.severe("insufficient amount of parameters given - overlay_bar_group");
                throw new DucatsException(message,"overlay_bar_group_format");
            }

            int barIndexToAdd = Integer.parseInt(sections[0]) - 1;
            Combiner combine = new Combiner();
            songIndex = songList.getActiveIndex();

            if (songList.getSize() > songIndex) {
                Song song = songList.getSongIndex(songIndex);
                ArrayList<Bar> barList = song.getBars();
                ArrayList<Group> groupList = song.getGroups();

                int groupIndexToBeCopiedTo = Integer.parseInt(sections[1]) - 1;
                Bar overlayingBarToBeCopied;
                try {
                    overlayingBarToBeCopied = barList.get(barIndexToAdd);
                } catch (java.lang.IndexOutOfBoundsException e) {
                    throw new DucatsException(message,"no_index");
                }
                Bar overlayingBar = overlayingBarToBeCopied.copy(overlayingBarToBeCopied);
                if (sections.length > 2 && sections[2].equals("repeat")) {
                    repeatLoop(groupList, groupIndexToBeCopiedTo, overlayingBar, combine);
                } else {

                    Group groupToBeCopied = groupList.get(groupIndexToBeCopiedTo);
                    Splitter splitItem = new Splitter("group split");
                    ArrayList<Bar> barListCopiedTo = splitItem.splitObject(groupToBeCopied);
                    Iterator<Bar> iteratorGroup = barListCopiedTo.iterator();
                    while (iteratorGroup.hasNext()) {
                        Bar temp = iteratorGroup.next();
                        combine.combineBar(overlayingBar, temp);
                    }

                }
                storage.updateFile(songList);
                DucatsLogger.fine("overlay_bar_group sucessfully overlayed a bar onto a group");
                Command ascii = new AsciiCommand("ascii song " + song.getName());
                return ascii.execute(songList,ui,storage);
            } else {

                throw new DucatsException(message, "no_index");
            }

        } catch (DucatsException e) {
            DucatsLogger.severe("there was an DucatsException of " + e.getType()
                    + "when the user typed " + message);
            throw new DucatsException(message, e.getType());
        } catch (java.io.IOException e) {
            throw new DucatsException(message,"IO");
        } catch (java.lang.ClassNotFoundException e) {
            throw new DucatsException(message, "IO");
        } catch (java.lang.NumberFormatException e) {
            throw new DucatsException(message,"number_index");
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new DucatsException(message,"no_index");
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
