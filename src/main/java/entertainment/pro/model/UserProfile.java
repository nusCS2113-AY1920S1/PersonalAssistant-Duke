package entertainment.pro.model;

import javax.print.DocFlavor;
import java.util.ArrayList;

public class UserProfile {
    private String userName = "";
    private int userAge = -1;
    private ArrayList<Integer> genreIdPreference = new ArrayList<>();
    private ArrayList<Integer> genreIdRestriction = new ArrayList<>();
    private boolean adult = true;
    private ArrayList<String> playlistNames = new ArrayList<>();
    private boolean sortByAlphabetical = false;
    private boolean sortByHighestRating = false;
    private boolean sortByLatestRelease = false;

    public ArrayList<Integer> getGenreIdPreference() {
        return genreIdPreference;
    }

    public void setGenreIdPreference(ArrayList<Integer> genreIdPreference) {
        this.genreIdPreference = genreIdPreference;
    }

    public ArrayList<Integer> getGenreIdRestriction() {
        return genreIdRestriction;
    }

    public void setGenreIdRestriction(ArrayList<Integer> genreIdRestriction) {
        this.genreIdRestriction = genreIdRestriction;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isSortByAlphabetical() {
        return sortByAlphabetical;
    }

    public void setSortByAlphabetical(boolean sortByAlphabetical) {
        this.sortByAlphabetical = sortByAlphabetical;
    }

    public boolean isSortByHighestRating() {
        return sortByHighestRating;
    }

    public void setSortByHighestRating(boolean sortByHighestRating) {
        this.sortByHighestRating = sortByHighestRating;
    }

    public boolean isSortByLatestRelease() {
        return sortByLatestRelease;
    }

    public void setSortByLatestRelease(boolean sortByLatestRelease) {
        this.sortByLatestRelease = sortByLatestRelease;
    }

    public void addGenreIdRestriction(ArrayList<Integer> inputGenre) {
        genreIdRestriction.addAll(inputGenre);
    }

    public void removeGenreIdRestriction(ArrayList<Integer> inputGenre) {
        genreIdRestriction.removeAll(inputGenre);
    }

    public void addGenreIdPreference(ArrayList<Integer> inputGenre) {
        genreIdPreference.addAll(inputGenre);
    }

    public void removeGenreIdPreference(ArrayList<Integer> inputGenre) {
        genreIdPreference.removeAll(inputGenre);
    }

    public ArrayList<String> getPlaylistNames() {
        return playlistNames;
    }

    public void setPlaylistNames(ArrayList<String> playlistNames) {
        this.playlistNames = playlistNames;
    }

    public void addPlaylist(String listName) {
        playlistNames.add(listName);
    }

    public void deletePlaylist(String listName) {
        playlistNames.remove(listName);
    }

    public void renamePlaylist(String oldName, String newName) {
        playlistNames.remove((oldName));
        playlistNames.add(newName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    /**
     * setting adult value to a default false when user is below 21 yrs old.
     */
    public void setUserAge(int userAge) {
        this.userAge = userAge;
        if (userAge < 21) {
            adult = false;
        }
    }
}

