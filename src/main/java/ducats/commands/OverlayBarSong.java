package ducats.commands;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Note;
import ducats.components.Song;
import ducats.components.Chord;
import ducats.components.SongList;
import ducats.commands.AsciiCommand;
import ducats.commands.Command;

import java.util.Arrays;
import java.util.Iterator;
import ducats.components.Combiner;
import ducats.DucatsLogger;

/**
 * A class that overlays a bar from one song to another.
 */
public class OverlayBarSong  extends Command<SongList>  {

    public String message;
    private int songIndex;




    /**
     * Constructor for the command to overlay a bar from one song to another.
     * @param message the input message that resulted in the creation of the ducats.Commands.Command
     */
    public OverlayBarSong(String message) {
        this.message = message;
    }


    private void repeatLoop(ArrayList<Bar> barListAddTo, int barIndexToAddTo, Bar overlayingBar, Combiner combine) {
        Iterator<Bar> iterator1 = barListAddTo.iterator();
        int i = 0;
        while (iterator1.hasNext()) {
            Bar temp = iterator1.next();
            if (i >= barIndexToAddTo) {
                combine.combineBar(overlayingBar, temp);
            }
            i += 1;
        }
    }

    /**
     * Overlays a bar from one song to another song.
     *
     * @param songList the ducats.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in ducats.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */

    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        Note note1;
        Note note2;
        Note note3;
        Note note4;
        int barNo;

        if (message.length() < 17 || !message.substring(0, 16).equals("overlay_bar_song")) {
            //exception if not fully spelt
            DucatsLogger.severe("the parser wrongly identified " + message + " as overlay_bar_song");
            throw new DucatsException(message,"overlay_bar_song_format");
        }

        try {

            //the command consists of overlay_bar_song Twinkle_1 2 Twinkle_2 1 refers
            // to overlay song Twinkle_1 bar 2 onto song Twinkle_2 bar 1
            String[] sections = message.substring(17).split(" ");

            if (sections.length < 4) {
                DucatsLogger.severe("overlay_bar_song command was called without sufficient number of arguments");
                throw new DucatsException(message,"overlay_bar_song_format");
            }

            int barIndexToAddFrom = Integer.parseInt(sections[1]) - 1;
            Combiner combine = new Combiner();
            Song songAddFrom;
            Song songAddTo;
            ArrayList<Song> songs = songList.findSong(sections[0]);
            if (songs.size() == 1) {
                songAddFrom = songs.get(0);
            } else {
                //song does not exist or query returned more than 1 result
                DucatsLogger.severe("overlay_bar_song command was called when the song did not exist");
                throw new DucatsException(message, "no_song");
            }
            ArrayList<Song> songs1 = songList.findSong(sections[2]);
            if (songs1.size() == 1) {
                songAddTo = songs1.get(0);
            } else {
                //song does not exist or query returned more than 1 result
                DucatsLogger.severe("overlay_bar_song command was called when the song did not exist");
                throw new DucatsException(message, "no_song");
            }
            int barIndexToAddTo = Integer.parseInt(sections[3]) - 1;

            ArrayList<Bar> barListAddFrom = songAddFrom.getBars();
            ArrayList<Bar> barListAddTo = songAddTo.getBars();
            Bar overlayingBarToBeCopied = barListAddFrom.get(barIndexToAddFrom);
            Bar overlayingBar = overlayingBarToBeCopied.copy(overlayingBarToBeCopied);

            if (sections.length > 4 && sections[4].equals("repeat")) {
                repeatLoop(barListAddTo, barIndexToAddTo, overlayingBar, combine);
            } else {

                Bar barToBeCopiedTo = barListAddTo.get(barIndexToAddTo);
                combine.combineBar(overlayingBar, barToBeCopiedTo);

            }
            //add the bar to the song in the songlist
            storage.updateFile(songList);
            DucatsLogger.fine("overlay_bar_song command sucessfully overlayed a bar from one song to another");
            Command ascii = new AsciiCommand("ascii song " + sections[2]);
            return ascii.execute(songList,ui,storage);

        } catch (DucatsException e) {
            DucatsLogger.severe("there was an DucatsException of " + e.getType()
                    + "when the user typed " + message);
            throw new DucatsException(message, e.getType());
        } catch (java.io.IOException e) {
            throw new DucatsException(message,"IO");
        } catch (java.lang.ClassNotFoundException e) {
            throw new DucatsException(message,"IO");
        } catch (java.lang.NumberFormatException e) {
            throw new DucatsException(message,"number_index");
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new DucatsException(message,"no_index");
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
