package ducats.commands;

import java.util.ArrayList;

import ducats.components.Bar;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.Chord;
import ducats.DucatsLogger;

/**
 * A class that splits an object to the bars and then returns an arraylist of the bars to the function.
 */
public class Splitter  {
    public String message;

    /**
     * Constructor for the command to split a song into its bars.
     * @param message the input message that resulted in the creation of the duke.Commands.Command.
     */

    public Splitter(String message) {
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
     * Splits a song object into an array list of bars.
     *
     * @param object the duke.components.Song is a Song object that needs to be split.
     * @return the the arraylist of bars
     */

    public ArrayList<Bar> splitObject(Song object) {
        return object.getBars();
    }

    /**
     * Splits a Group object into an array list of bars.
     *
     * @param object the duke.components.Grouo is a Group object that needs to be split.
     * @return the the arraylist of bars
     */

    public ArrayList<Bar> splitObject(Group object) {
        DucatsLogger.fine("Group object was split into bars - splitter");
        return object.getBars();
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    
    public boolean isExit() {
        return false;
    }
}
