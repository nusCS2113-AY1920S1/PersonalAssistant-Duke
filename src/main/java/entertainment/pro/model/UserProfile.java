package entertainment.pro.model;

import java.util.ArrayList;

public class UserProfile {
    private final String userName;
    private final int userAge;
    private final ArrayList<Integer> genreIdPreference;
    private final ArrayList<Integer> genreIdRestriction;
    private final boolean adult;
    private final ArrayList<String> playlistNames;
    private final boolean sortByAlphabetical;
    private final boolean sortByHighestRating;
    private final boolean sortByLatestRelease;

    public UserProfile(String userName, int userAge, ArrayList<Integer> genreIdPreference, ArrayList<Integer> genreIdRestriction, boolean adult, ArrayList<String> playlistNames, boolean sortByAlphabetical, boolean sortByHighestRating, boolean sortByLatestRelease) {
        this.userName = userName;
        this.userAge = userAge;
        this.genreIdPreference = genreIdPreference;
        this.genreIdRestriction = genreIdRestriction;
        this.adult = adult;
        this.playlistNames = playlistNames;
        this.sortByAlphabetical = sortByAlphabetical;
        this.sortByHighestRating = sortByHighestRating;
        this.sortByLatestRelease = sortByLatestRelease;
    }

    public UserProfile() {
        this.userName = "";
        this.userAge = -1;
        this.genreIdPreference = new ArrayList<>();
        this.genreIdRestriction = new ArrayList<>();
        this.adult = true;
        this.playlistNames = new ArrayList<>();
        this.sortByAlphabetical = false;
        this.sortByHighestRating = false;
        this.sortByLatestRelease = false;
    }

    public ArrayList<Integer> getGenreIdPreference() {
        return genreIdPreference;
    }

    public UserProfile setGenreIdPreference(ArrayList<Integer> genreIdPreference) {
        return new UserProfile(this.getUserName(), this.getUserAge(), genreIdPreference, this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public ArrayList<Integer> getGenreIdRestriction() {
        return genreIdRestriction;
    }

    public UserProfile setGenreIdRestriction(ArrayList<Integer> genreIdRestriction) {
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), genreIdRestriction, this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public boolean isAdult() {
        return adult;
    }

    public UserProfile setAdult(boolean adult) {
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), adult, this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public boolean isSortByAlphabetical() {
        return sortByAlphabetical;
    }

    public UserProfile setSortByAlphabetical(boolean sortByAlphabetical) {
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), sortByAlphabetical, this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public boolean isSortByHighestRating() {
        return sortByHighestRating;
    }

    public UserProfile setSortByHighestRating(boolean sortByHighestRating) {
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), sortByHighestRating, this.isSortByLatestRelease());
    }

    public boolean isSortByLatestRelease() {
        return sortByLatestRelease;
    }

    public UserProfile setSortByLatestRelease(boolean sortByLatestRelease) {
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), sortByLatestRelease);
    }

    public UserProfile addGenreIdRestriction(ArrayList<Integer> inputGenre) {
        ArrayList<Integer> newRestriction = this.getGenreIdRestriction();
        newRestriction.addAll(inputGenre);
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), newRestriction, this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public UserProfile removeGenreIdRestriction(ArrayList<Integer> inputGenre) {
        ArrayList<Integer> newRestriction = this.getGenreIdRestriction();
        newRestriction.removeAll(inputGenre);
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), newRestriction, this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public UserProfile addGenreIdPreference(ArrayList<Integer> inputGenre) {
        ArrayList<Integer> newPreference = this.getGenreIdPreference();
        newPreference.addAll(inputGenre);
        return new UserProfile(this.getUserName(), this.getUserAge(), newPreference, this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public UserProfile removeGenreIdPreference(ArrayList<Integer> inputGenre) {
        ArrayList<Integer> newPreference = this.getGenreIdPreference();
        newPreference.removeAll(inputGenre);
        return new UserProfile(this.getUserName(), this.getUserAge(), newPreference, this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public ArrayList<String> getPlaylistNames() {
        return playlistNames;
    }

    public UserProfile setPlaylistNames(ArrayList<String> playlistNames) {
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), playlistNames, this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public UserProfile addPlaylist(String listName) {
        ArrayList<String> newPlaylistNames = this.getPlaylistNames();
        newPlaylistNames.add(listName);
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), newPlaylistNames, this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public UserProfile deletePlaylist(String listName) {
        ArrayList<String> newPlaylistNames = this.getPlaylistNames();
        newPlaylistNames.remove(listName);
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), newPlaylistNames, this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public UserProfile renamePlaylist(String oldName, String newName) {
        ArrayList<String> newPlaylistNames = this.getPlaylistNames();
        newPlaylistNames.remove((oldName));
        newPlaylistNames.add(newName);
        return new UserProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), newPlaylistNames, this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public String getUserName() {
        return userName;
    }

    public UserProfile setUserName(String userName) {
        return new UserProfile(userName, this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
    }

    public int getUserAge() {
        return userAge;
    }

    /**
     * setting adult value to a default false when user is below 21 yrs old.
     */
    public UserProfile setUserAge(int userAge) {
        if (userAge < 21) {
            return new UserProfile(this.getUserName(), userAge, this.getGenreIdPreference(), this.getGenreIdRestriction(), false, this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());
        } else {
            return new UserProfile(this.getUserName(), userAge, this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease());

        }
    }
}

