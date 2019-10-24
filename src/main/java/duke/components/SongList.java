package duke.components;

import java.util.ArrayList;

//@@author jwyf
/**
 * Class used to store the song list and perform necessary manipulations to the song list such as
 * adding songs, removing songs and finding songs based on keywords, as well as obtaining the size
 * of the song list.
 */
public class SongList {

    private ArrayList<Song> list = new ArrayList<>();

    /**
     * Removes an element from the song list.
     *
     * @param index the index of the duke.components.Song in the song list that is to be removed
     */
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * Adds an element to the song list.
     *
     * @param newSong the duke.components.Song object to be added to the song list.
     */
    public void add(Song newSong) {
        list.add(newSong);
    }

    /**
     * Returns the duke.components.Song object at the specified index
     *
     * @param index the index of the duke.components.Song object
     * @return the duke.components.Song object
     */
    public Song getSongIndex(int index) {
        return list.get(index);
    }

    /**
     * Returns the current size of the song list.
     *
     * @return the current size of the task list.
     */
    public int getSize() {
        return list.size();
    }

    /**
     * Returns the task list for duke.Duke, which is implemented as an ArrayList of duke.components.Song objects.
     *
     * @return the song list
     */
    public ArrayList<Song> getSongList() {
        return list;
    }

    /**
     * Returns a subset of the song list (implemented as an ArrayList of duke.components.Song objects) that contains
     * the query specified in the argument.
     *
     * @param query the search query to be obtained from the input command
     * @return the ArrayList of duke.components.Song objects whose description contained the query
     */
    public ArrayList<Song> findSong(String query) {
        ArrayList<Song> result = new ArrayList<>();
        for (Song song: list) {
            if (song.getName().contains(query)) {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * Removes from this list all of its elements that are contained in the ArrayList.
     *
     * @param removeList the specified collection; its elements are removed from the list.
     */
    public void removeAll(ArrayList<Song> removeList) {
        list.removeAll(removeList);
    }
}
