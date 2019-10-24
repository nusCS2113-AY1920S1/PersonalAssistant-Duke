package entertainment.pro.model;

import java.util.ArrayList;

/**
 * structure for Playlist object
 */
public class Playlist {
    private String listName;
    private String description;
    private ArrayList<Long> movieId;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<Long> getMovieId() {
        return movieId;
    }

    public void setMoveId(ArrayList<Long> inputGenre) {
        movieId = inputGenre;
    }

    public void addMovieId(ArrayList<Long> inputGenre) {
        movieId.addAll(inputGenre);
    }

    public void removeMovieId(ArrayList<Long> inputGenre) {
        movieId.removeAll(inputGenre);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
