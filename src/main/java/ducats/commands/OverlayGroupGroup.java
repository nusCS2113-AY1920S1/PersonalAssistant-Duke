package ducats.commands;

import java.util.ArrayList;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongList;
import java.util.Iterator;

import ducats.components.Combiner;
import ducats.DucatsLogger;

/**
 * A class that overlays  groups from 2 different songs.
 */
public class OverlayGroupGroup  extends Command {
    public String message;
    private int songIndex;

    //@@author rishi12438
    /**
     * Constructor for the command to add a new bar to the current song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */

    public OverlayGroupGroup(String message) {

        this.message = message;

    }

    private void repeatLoop(ArrayList<Group> groupListAddTo, int groupIndexToAddTo, int groupIndexToAddFrom,
                             Group overlayingGroup, Combiner combine) {
        Iterator<Group> iterator1 = groupListAddTo.iterator();
        int i = 0;
        while (iterator1.hasNext()) {
            Group temp = iterator1.next();
            if (i >= groupIndexToAddTo && i != groupIndexToAddFrom) {
                combine.combineGroup(overlayingGroup,temp);
            }
            i += 1;
        }
    }

    /**
     * Overlays 2 groups together and when the groups are of unequal length, when a
     * smaller group is overlayed onto a bigger one then it is repeated through out
     * and if a bigger group is overlayed on a smaller one then only the length till
     * the smaller one is overlayed.
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    //@@author rishi12438
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {


        if (message.length() < 20 || !message.substring(0, 19).equals("overlay_group_group")) {
            //exception if not fully spelt
            DucatsLogger.severe("the parser wrongly identified " + message + " as overlay_group_group");
            throw new DucatsException(message,"overlay_group_group_format");
        }

        try {

            //the command consists of overlay_group_group twinkle 2 twinkle1 1 refers to overlay twinkle  group 2 onto
            // twinke1  group 1
            Combiner combine = new Combiner();
            String[] sections = message.substring(20).split(" ");
            if (sections.length < 4) {
                DucatsLogger.severe("overlay_group_group command was called without sufficient number of arguments");
                throw new DucatsException(message,"overlay_group_group_format");
            }
            ArrayList<Song> songs = songList.findSong(sections[0]);
            Song songAddFrom;
            Song songAddTo;
            if (songs.size() == 1) {
                songAddFrom = songs.get(0);
            } else {
                DucatsLogger.severe("overlay_group_group command when no such song name existed");
                //song does not exist or query returned more than 1 result
                throw new DucatsException(message, "no_song");
            }
            ArrayList<Song> songs1 = songList.findSong(sections[2]);
            if (songs1.size() == 1) {
                songAddTo = songs1.get(0);
            } else {
                //song does not exist or query returned more than 1 result
                DucatsLogger.severe("overlay_group_group command when no such to be copied song name existed");
                throw new DucatsException(message, "no_song");
            }

            int groupIndexToAddFrom = Integer.parseInt(sections[1]) - 1;

            int groupIndexToAddTo = Integer.parseInt(sections[3]) - 1;

            ArrayList<Group> groupListAddFrom = songAddFrom.getGroups();
            ArrayList<Group> groupListAddTo = songAddTo.getGroups();
            Group overlayingGroupToBeCopied = groupListAddFrom.get(groupIndexToAddFrom);
            Group overlayingGroup = overlayingGroupToBeCopied.copy(overlayingGroupToBeCopied);

            if (sections.length > 4 && sections[4].equals("repeat")) {
                repeatLoop(groupListAddTo, groupIndexToAddTo, groupIndexToAddFrom, overlayingGroup,combine);
            } else {

                Group groupToBeCopiedTo = groupListAddTo.get(groupIndexToAddTo);
                combine.combineGroup(overlayingGroup,groupToBeCopiedTo);
            }

            //add the bar to the song in the songlist
            storage.updateFile(songList);
            DucatsLogger.fine("overlay_group_group sucessfully updated the song " + songAddTo.getName());
            Command ascii = new AsciiCommand("ascii song " + songAddTo.getName());
            return ascii.execute(songList,ui,storage);
        } catch (DucatsException e) {
            //System.out.println(e.getType());
            throw new DucatsException(message, e.getType());
        } catch (java.io.IOException e) {
            DucatsLogger.severe("there was an error in serializing the components - overlay_group_group ");
            throw new DucatsException(message,"IO");
        } catch (java.lang.ClassNotFoundException e) {
            DucatsLogger.severe("there was an error in serializing the components - overlay_group_group ");
            throw new DucatsException(message,"IO");
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
