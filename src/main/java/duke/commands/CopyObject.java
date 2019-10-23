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

public abstract class CopyObject<T> {

    /**
     * Modifies the song list and returns the messages intended to be displayed.
     *
     * @param object the object that must be copied with type T.
     */
    public abstract T copy(T object) throws DukeException, IOException,ClassNotFoundException;
}
