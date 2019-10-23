package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.Song;
import duke.components.SongList;

import java.io.IOException;
/**
 * An abstract class used to copy any object. Extended by
 * <ul>
 *     <li>Bar</li>
 * </ul>
 */
/**
 * A class representing the command to copy an object
 */
public abstract class CopyObject<T> {

    /**
     * Modifies the song list and returns the messages intended to be displayed.
     *
     * @param songList the duke.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public abstract T copy(T object) throws DukeException, IOException,ClassNotFoundException;
}
