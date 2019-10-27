package entertainment.pro.model;

import javax.print.DocFlavor;
import java.util.ArrayList;

public class UserProfile {
    private ArrayList<Integer> genreIdPreference = new ArrayList<>();
    private ArrayList<Integer> genreIdRestriction = new ArrayList<>();
    private boolean adult = false;
    private boolean sortByAlphabetical = false;
    private boolean sortByHighestRating = false;
    private boolean sortByLatestRelease = false;

    public UserProfile(ArrayList<Integer> genreIdPreference, ArrayList<Integer> genreIdRestriction,
                       boolean adult, boolean sortByAlphabetical, boolean sortByHighestRating,
                       boolean sortByLatestRelease) {
        this.genreIdPreference = genreIdPreference;
        this.genreIdRestriction = genreIdRestriction;
        this.adult = adult;
        this.sortByAlphabetical = sortByAlphabetical;
        this.sortByHighestRating = sortByHighestRating;
        this.sortByLatestRelease = sortByLatestRelease;
    }

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
}

