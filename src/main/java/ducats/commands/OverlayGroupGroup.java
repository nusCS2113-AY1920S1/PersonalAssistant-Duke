package ducats.commands;

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
import java.util.Iterator;
import java.lang.Math;
import ducats.commands.AsciiCommand;
import ducats.commands.Command;
import ducats.components.Combiner;

/**
 * A class that splits an object to the bars and then returns an arraylist of the bars to the function.
 */
public class OverlayGroupGroup  extends Command<SongList>  {
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


    /**
     * Modifies the song in the song list and returns the messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    //@@author rishi12438
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        Note note1;
        Note note2;
        Note note3;
        Note note4;
        int barNo;

        if (message.length() < 20 || !message.substring(0, 19).equals("overlay_group_group")) {
            //exception if not fully spelt
            throw new DucatsException(message,"overlay_group_group_format");
        }

        try {
            //the command consists of overlay 10 repeat
            //the command consists of overlay_group_group twinkle 2 twinkle1 1 refers to overlay twinkle  group 2 onto
            // twinke1  group 1
            Combiner combine = new Combiner();
            String[] sections = message.substring(20).split(" ");
            if (sections.length < 4) {
                throw new DucatsException(message,"overlay_group_group_format");
            }
            ArrayList<Song> songs = songList.findSong(sections[0]);
            Song songAddFrom;
            Song songAddTo;
            if (songs.size() == 1) {
                songAddFrom = songs.get(0);
            } else {
                //song does not exist or query returned more than 1 result
                throw new DucatsException(message, "no_song");
            }
            ArrayList<Song> songs1 = songList.findSong(sections[2]);
            if (songs1.size() == 1) {
                songAddTo = songs1.get(0);
            } else {
                //song does not exist or query returned more than 1 result
                throw new DucatsException(message, "no_song");
            }
            //int songIndexToAddFrom = Integer.parseInt(sections[0]) - 1;
            int groupIndexToAddFrom = Integer.parseInt(sections[1]) - 1;
            //int songIndexToAddTo = Integer.parseInt(sections[2]) - 1;
            int groupIndexToAddTo = Integer.parseInt(sections[3]) - 1;



            ArrayList<Group> groupListAddFrom = songAddFrom.getGroups();
            ArrayList<Group> groupListAddTo = songAddTo.getGroups();
            Group overlayingGroupToBeCopied = groupListAddFrom.get(groupIndexToAddFrom);
            Group overlayingGroup = overlayingGroupToBeCopied.copy(overlayingGroupToBeCopied);
            //Bar overlayingBar = barList.get(barIndexToAdd);
            //System.out.println("adjjdsa");
            if (sections.length > 4 && sections[4].equals("repeat")) {
                Iterator<Group> iterator1 = groupListAddTo.iterator();
                int i = 0;
                while (iterator1.hasNext()) {
                    Group temp = iterator1.next();
                    if (i >= groupIndexToAddTo && i != groupIndexToAddFrom) {
                        combine.combineGroup(overlayingGroup,temp);
                    }
                    i += 1;
                }
            } else {

                Group groupToBeCopiedTo = groupListAddTo.get(groupIndexToAddTo);

                combine.combineGroup(overlayingGroup,groupToBeCopiedTo);
            }

            //add the bar to the song in the songlist
            storage.updateFile(songList);
            Command ascii = new AsciiCommand("ascii song " + songAddTo.getName());
            return ascii.execute(songList,ui,storage);
            //return ui.formatAddOverlay(songList.getSongList(), groupIndexToAddTo,songAddTo);


        } catch (DucatsException e) {
            //System.out.println(e.getType());
            throw new DucatsException(message, e.getType());
        } catch (java.io.IOException e) {
            throw new DucatsException(message,"IO");
        } catch (java.lang.ClassNotFoundException e) {
            throw new DucatsException(message,"IO");
        } catch (java.lang.NumberFormatException e) {
            throw new DucatsException(message,"number_index");
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
