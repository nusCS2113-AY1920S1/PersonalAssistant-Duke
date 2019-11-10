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

import java.util.Iterator;

/**
 * A class representing the command to add a new bar of notes to the current song.
 */
public class AddOverlayCommand extends Command<SongList> {

    private int songIndex;
    public String message;

    /**
     * Constructor for the command to add a new bar to the current song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AddOverlayCommand(String message) {
        this.message = message;
    }

    /**
     * Combines two chords.
     *
     * @param chordBeCopiedFrom the chord that is being copied from
     * @param chordCopiedTo the chord that is being copied to
     */

    public void combineChord(Chord chordBeCopiedFrom, Chord chordCopiedTo) {

        //ArrayList<Note>noteArrayCopyFrom  = chordBeCopiedFrom.getNotes();
        //Iterator<Note> iterator1 = noteArrayCopyFrom.iterator();
        //while()
        chordCopiedTo.getNotes().addAll(chordBeCopiedFrom.getNotes());
    }

    /**
     * Combines two bars.
     *
     * @param barToBeCopied the bar that is being copied from
     * @param barToCopyTo the bar that is being copied to
     */
    public void combineBar(Bar barToBeCopied, Bar barToCopyTo) {
        //we need copy the chords from bar1 into bar 2
        ArrayList<Chord> chordBeCopiedFrom = barToBeCopied.getChords();
        ArrayList<Chord> chordCopiedTo = barToCopyTo.getChords();
        //System.out.println("here i after the chord from bar");
        Iterator<Chord> iterator1 = chordBeCopiedFrom.iterator();
        int i = 0;
        while (iterator1.hasNext()) {
            Chord chordAdd = iterator1.next();
            combineChord(chordAdd,chordCopiedTo.get(i));
            i += 1;
        }
    }

    /**
     * Modifies the song in the song list and returns the messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in ducats.Ducats
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */

    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        Note note1;
        Note note2;
        Note note3;
        Note note4;
        int barNo;
        if (message.length() < 8 || !message.substring(0, 8).equals("overlay ")) { //exception if not fully spelt
            throw new DucatsException(message,"overlay_format");
        }
        try {
            //the command consists of overlay 10 repeat
            String[] sections = message.substring(8).split(" ");
            //this refers to the bar that needs to be added:
            if (sections.length < 2) {
                throw new DucatsException(message,"overlay_format");
            }
            int barIndexToAdd = Integer.parseInt(sections[0]) - 1;
            songIndex = songList.getActiveIndex();
            //System.out.println(barIndexToAdd);
            if (songList.getSize() > songIndex) {

                Song song = songList.getSongIndex(songIndex);

                ArrayList<Bar> barList = song.getBars();
                int barIndexToBeCopiedTo = Integer.parseInt(sections[1]) - 1;
                Bar overlayingBarToBeCopied;
                try {
                    overlayingBarToBeCopied = barList.get(barIndexToAdd);
                } catch (java.lang.IndexOutOfBoundsException e) {
                    throw new DucatsException(message, "no_index");
                }
                Bar overlayingBar = overlayingBarToBeCopied.copy(overlayingBarToBeCopied);

                ArrayList<Chord> chordsToAdd = overlayingBar.getChords();

                if (sections.length > 2 && sections[2].equals("repeat")) {
                    Iterator<Bar> iterator1 = barList.iterator();
                    int i = 0;
                    while (iterator1.hasNext()) {
                        Bar temp = iterator1.next();
                        if (i >= barIndexToBeCopiedTo && i != barIndexToAdd) {
                            combineBar(overlayingBar, temp);
                        }
                        i += 1;
                    }

                } else {
                    //System.out.println("no repeat found");
                    Bar temp = barList.get(barIndexToBeCopiedTo);
                    //ArrayList<Chord> tempChordList = temp.getChords();

                    combineBar(overlayingBar,temp);

                }
                //add the bar to the song in the songlist
                storage.updateFile(songList);
                Command ascii = new AsciiCommand("ascii song " + song.getName());
                return ascii.execute(songList,ui,storage);
                //return ui.formatAddOverlay(songList.getSongList(), barIndexToAdd,song);
            } else {
                //System.out.println("no such index");
                //System.out.println(songList.getSize());
                throw new DucatsException(message, "no_index");
            }

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
