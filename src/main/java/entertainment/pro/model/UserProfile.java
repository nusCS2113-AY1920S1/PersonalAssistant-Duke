package entertainment.pro.model;

import java.util.ArrayList;

/**
 * structure for UserProfile object
 */
public class UserProfile {
    private String userName;
    private int userAge;
    private ArrayList<Integer> genreIdPreference = new ArrayList<>();
    private ArrayList<Integer> genreIdRestriction = new ArrayList<>();
    private boolean adult = false;
    private boolean sortByAlphabetical = false;
    private boolean sortByLatestRelease = false;

    public boolean isSortByAlphabetical() {
        return sortByAlphabetical;
    }

    public void setSortByAlphabetical(boolean sortByAlphabetical) {
        this.sortByAlphabetical = sortByAlphabetical;
    }

    public boolean isSortByLatestRelease() {
        return sortByLatestRelease;
    }

    public void setSortByLatestRelease(boolean sortByLatestRelease) {
        this.sortByLatestRelease = sortByLatestRelease;
    }

    public boolean isSortByHighestRAting() {
        return sortByHighestRAting;
    }

    public void setSortByHighestRAting(boolean sortByHighestRAting) {
        this.sortByHighestRAting = sortByHighestRAting;
    }

    private boolean sortByHighestRAting = false;


    public String getUserName(){
        return userName;
    }

    public int getUserAge(){
        return userAge;
    }

    public ArrayList<Integer> getGenreIdPreference() {
        return genreIdPreference;
    }

    public void setUserName(String inputName) {
        userName = inputName;
    }

    public void setUserAge(int inputAge) {
        userAge = inputAge;
    }

    public void setGenreIdPreference(ArrayList<Integer> inputGenre) {
        genreIdPreference = inputGenre;
    }

    public void addGenreIdPreference(ArrayList<Integer> inputGenre) {
        genreIdPreference.addAll(inputGenre);
    }

    public void removeGenreIdPreference(ArrayList<Integer> inputGenre) {
        genreIdPreference.removeAll(inputGenre);
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }


    public ArrayList<Integer> getGenreIdRestriction() {
        return genreIdRestriction;
    }

    public void setGenreIdRestriction(ArrayList<Integer> genreIdRestriction) {
        this.genreIdRestriction = genreIdRestriction;
    }

    public void addGenreIdRestriction(ArrayList<Integer> inputGenre) {
        genreIdRestriction.addAll(inputGenre);
    }

    public void removeGenreIdRestriction(ArrayList<Integer> inputGenre) {
        genreIdRestriction.removeAll(inputGenre);
    }
}