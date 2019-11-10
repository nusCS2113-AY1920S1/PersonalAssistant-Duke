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

/**
 * A class that splits an object to the bars and then returns an arraylist of the bars to the function.
 */
public class OverlayBarSong  extends Command<SongList>  {
    public String message;
    private int songIndex;


    /**
     * Constructor for the command to add a new bar to the current song.
     * @param message the input message that resulted in the creation of the ducats.Commands.Command
     */
    public OverlayBarSong(String message) {
        this.message = message;
    }


    /**
     * Modifies the song in the song list and returns the messages intended to be displayed.
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
            throw new DucatsException(message,"overlay_bar_song_format");
        }

        try {

            //the command consists of overlay_bar_song Twinkle_1 2 Twinkle_2 1 refers to overlay song 1 bar 2 onto
            // song 1 bar 1
            //the command consists of overlay_bar_song 1 2 1 1 refers to overlay song 1 bar 2 onto song 1 bar 1
            //System.out.print(message.length());
            String[] sections = message.substring(17).split(" ");
            //System.out.print(Arrays.toString(sections));
            if (sections.length < 4) {
                throw new DucatsException(message,"overlay_bar_song_format");
            }
            //int songIndexToAddFrom = Integer.parseInt(sections[0]) - 1;
            int barIndexToAddFrom = Integer.parseInt(sections[1]) - 1;
            Combiner combine = new Combiner();
            //int songIndexToAddTo = Integer.parseInt(sections[2]) - 1;
            Song songAddFrom;
            Song songAddTo;
            ArrayList<Song> songs = songList.findSong(sections[0]);
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
            int barIndexToAddTo = Integer.parseInt(sections[3]) - 1;
            //System.out.println(barIndexToAdd);

            ArrayList<Bar> barListAddFrom = songAddFrom.getBars();
            ArrayList<Bar> barListAddTo = songAddTo.getBars();
            Bar overlayingBarToBeCopied = barListAddFrom.get(barIndexToAddFrom);
            Bar overlayingBar = overlayingBarToBeCopied.copy(overlayingBarToBeCopied);

            if (sections.length > 4 && sections[4].equals("repeat")) {
                Iterator<Bar> iterator1 = barListAddTo.iterator();
                int i = 0;
                while (iterator1.hasNext()) {
                    Bar temp = iterator1.next();
                    if (i >= barIndexToAddTo) {
                        combine.combineBar(overlayingBar, temp);
                    }
                    i += 1;
                }
            } else {
                //System.out.println("no repeat found");
                Bar barToBeCopiedTo = barListAddTo.get(barIndexToAddTo);
                combine.combineBar(overlayingBar, barToBeCopiedTo);
                //System.out.println("bar temp gotten");
            }
            //add the bar to the song in the songlist
            storage.updateFile(songList);
            Command ascii = new AsciiCommand("ascii song " + sections[2]);
            return ascii.execute(songList,ui,storage);
            //return ui.formatAddOverlay(songList.getSongList(), barIndexToAddFrom,songAddTo);

        } catch (DucatsException e) {
            //System.out.println(e.getType());
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
