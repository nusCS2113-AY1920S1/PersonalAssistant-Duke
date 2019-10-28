package ducats.commands;

import ducats.DucatsException;

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
    public abstract T copy(T object) throws DucatsException, IOException,ClassNotFoundException;
}
