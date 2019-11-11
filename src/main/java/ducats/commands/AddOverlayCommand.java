package ducats.commands;


import java.util.ArrayList;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Chord;
import ducats.components.Note;
import ducats.components.Song;
import ducats.components.SongList;
import ducats.commands.AsciiCommand;
import ducats.commands.Command;
import ducats.DucatsLogger;
import ducats.components.Combiner;

import java.util.Iterator;

/**
 * A class representing the command to add a new bar of notes to the current song.
 */
public class AddOverlayCommand extends Command<SongList> {

    private int songIndex;
    public String message;

    /**
     * Constructor for the Overlay command to overlay 2 bars from the same song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AddOverlayCommand(String message) {
        this.message = message;
    }


    private void repeatLoop(ArrayList<Bar> barList, int barIndexToBeCopiedTo, int barIndexToAdd, Bar overlayingBar,
                             Combiner combine) {
        Iterator<Bar> iterator1 = barList.iterator();
        int i = 0;

        while (iterator1.hasNext()) {
            Bar temp = iterator1.next();
            if (i >= barIndexToBeCopiedTo && i != barIndexToAdd) {
                combine.combineBar(overlayingBar, temp);
            }
            i += 1;
        }
    }
    /**
     * This is the ovelay function for overlaying bars from the opened song.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in ducats.Ducats, if successful returns an ascii print of song.
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */

    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        Note note1;
        Note note2;
        Note note3;
        Note note4;
        int barNo;
        if (message.length() < 8 || !message.substring(0, 8).equals("overlay ")) { //exception if not fully spelt
            DucatsLogger.warning("the parser wrongly identified " + message + " as overlay");
            throw new DucatsException(message,"overlay_format");
        }
        try {

            String[] sections = message.substring(8).split(" ");
            //if the command is not inputted properly, i.e. the parameters are lesser than 2:
            if (sections.length < 2) {
                DucatsLogger.severe("overlay command was called without sufficient number of arguments");
                throw new DucatsException(message,"overlay_format");
            }

            int barIndexToAdd = Integer.parseInt(sections[0]) - 1;
            songIndex = songList.getActiveIndex();

            if (songList.getSize() > songIndex) {

                Song song = songList.getSongIndex(songIndex);

                ArrayList<Bar> barList = song.getBars();
                int barIndexToBeCopiedTo = Integer.parseInt(sections[1]) - 1;
                Bar overlayingBarToBeCopied;
                try {
                    overlayingBarToBeCopied = barList.get(barIndexToAdd);
                } catch (java.lang.IndexOutOfBoundsException e) {
                    DucatsLogger.severe("overlay command was called when the bar index did not exist");
                    throw new DucatsException(message, "no_index");
                }

                Bar overlayingBar = overlayingBarToBeCopied.copy(overlayingBarToBeCopied);

                Combiner combine = new Combiner();
                if (sections.length > 2 && sections[2].equals("repeat")) {
                    repeatLoop(barList,  barIndexToBeCopiedTo,  barIndexToAdd,  overlayingBar, combine);

                } else {

                    Bar temp = barList.get(barIndexToBeCopiedTo);
                    combine.combineBar(overlayingBar,temp);

                }
                //calling the storage update function.
                storage.updateFile(songList);
                Command ascii = new AsciiCommand("ascii song " + song.getName());
                DucatsLogger.fine("overlay command sucessfully overlayed the bar on the song");
                return ascii.execute(songList,ui,storage);
                //return ui.formatAddOverlay(songList.getSongList(), barIndexToAdd,song);
            } else {
                DucatsLogger.severe("the song did not exist");
                throw new DucatsException(message, "no_index");
            }

        } catch (DucatsException e) {
            DucatsLogger.severe("there was an DucatsException of " + e.getType()
                    + "when the user typed " + message);
            throw new DucatsException(message, e.getType());
        } catch (java.io.IOException e) {
            DucatsLogger.severe("there was an error in serializing the components");
            throw new DucatsException(message,"IO");
        } catch (java.lang.ClassNotFoundException e) {
            DucatsLogger.severe("there was an error in serializing the components");
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
