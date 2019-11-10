package entertainment.pro.model;

import entertainment.pro.logic.parsers.commands.SearchCommand;

import java.util.ArrayList;

public class SearchProfile extends UserProfile {

    private final String name;
    private final boolean isMovie;

    /**
     * Constructor for SearchProfile Object.
     * @param userName Name of the user stored according to the user.
     * @param userAge Age of the user stored according to the user.
     * @param genreIdPreference List of genres set by user for a particular search request.
     * @param genreIdRestriction List of genres restricted by user for a particular search request.
     * @param adult To indicate whether user prefers adult content for a paricular search request.
     * @param playlistNames List of playlist stored by user.
     * @param sortByAlphabetical To indicate whether user want to sort results in alphabetical order.
     * @param sortByHighestRating To indicate whether user want to sort results based on highest rating.
     * @param sortByLatestRelease To indicate whether user want to sort results in alphabetical order.
     * @param name String that user want the search results to be based on.
     * @param isMovie To indicate whether the search request is for movies.
     */
    public SearchProfile(String userName, int userAge, ArrayList<Integer> genreIdPreference,
                         ArrayList<Integer> genreIdRestriction, boolean adult, ArrayList<String> playlistNames,
                         boolean sortByAlphabetical, boolean sortByHighestRating, boolean sortByLatestRelease,
                         String name, boolean isMovie) {
//        super.setUserName(userName);
//        super.setUserAge(userAge);
//        super.setGenreIdPreference(genreIdPreference);
//        super.setGenreIdRestriction(genreIdRestriction);
//        super.setAdult(adult);
//        super.setPlaylistNames(playlistNames);
//        super.setSortByAlphabetical(sortByAlphabetical);
//        super.setSortByHighestRating(sortByHighestRating);
//        super.setSortByLatestRelease(sortByLatestRelease);
        super(userName, userAge, genreIdPreference, genreIdRestriction, adult, playlistNames, sortByAlphabetical, sortByHighestRating, sortByLatestRelease);
        this.name = name;
        this.isMovie = isMovie;
    }

    public SearchProfile() {
        this.name = "";
        this.isMovie = true;
    }


    public SearchProfile(ArrayList<Integer> genreIdPreference,
                         ArrayList<Integer> genreIdRestriction, boolean adult,
                         boolean sortByAlphabetical, boolean sortByHighestRating, boolean sortByLatestRelease,
                         String name, boolean isMovie) {
        super.setGenreIdPreference(genreIdPreference);
        super.setGenreIdRestriction(genreIdRestriction);
        super.setAdult(adult);
        super.setSortByAlphabetical(sortByAlphabetical);
        super.setSortByHighestRating(sortByHighestRating);
        super.setSortByLatestRelease(sortByLatestRelease);
        this.name = name;
        this.isMovie = isMovie;
    }


    /**
     * Responsible for returning the name of movie/TV show that user want search results to be based on.
     * @return The name of movie/TV show that user want search results to be based on.
     */
    public String getName() {
        return name;
    }

    /**
     * Responsible for setting the name of movie/TV show that user want search results to be based on.
     * @param name The name of movie/TV show that user want search results to be based on.
     */
    public SearchProfile setName(String name) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease(), name, this.isMovie);
    }

    /**
     * Responsible for returning whether the search request is for movies or TV shows.
     * @return true if search request is for movies and false otherwise.
     */
    public boolean isMovie() {
        return isMovie;
    }

    /**
     * Responsible for setting whether the search request is for movies or TV shows.
     * @param movie true if search request is for movies and false otherwise.
     */
    public SearchProfile setMovie(boolean movie) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease(), this.getName(), movie);
    }

    public SearchProfile setGenreIdPreference(ArrayList<Integer> genreIdPreference) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), genreIdPreference, this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease(), this.getName(), this.isMovie);
    }

    public SearchProfile setGenreIdRestriction(ArrayList<Integer> genreIdRestriction) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), genreIdRestriction, this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease(), this.getName(), this.isMovie);
    }

    public SearchProfile setAdult(boolean adult) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), adult, this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), this.isSortByLatestRelease(), this.getName(), this.isMovie);
    }

    public SearchProfile setSortByAlphabetical(boolean sortByAlphabetical) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), sortByAlphabetical, this.isSortByHighestRating(), this.isSortByLatestRelease(), this.getName(), this.isMovie);
    }

    public SearchProfile setSortByLatestRelease(boolean sortByLatestRelease) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), this.isSortByHighestRating(), sortByLatestRelease, this.getName(), this.isMovie);
    }

    public SearchProfile setSortByHighestRating(boolean sortByHighestRating) {
        return new SearchProfile(this.getUserName(), this.getUserAge(), this.getGenreIdPreference(), this.getGenreIdRestriction(), this.isAdult(), this.getPlaylistNames(), this.isSortByAlphabetical(), sortByHighestRating, this.isSortByLatestRelease(), this.getName(), this.isMovie);
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public SearchProfile setFromUserPreference(String entryName, boolean isMovie, UserProfile userProfile) {
        SearchProfile searchProfile = new SearchProfile();
        searchProfile = searchProfile.setGenreIdPreference(userProfile.getGenreIdPreference());
        searchProfile = searchProfile.setGenreIdRestriction(userProfile.getGenreIdRestriction());
        searchProfile = searchProfile.setAdult(userProfile.isAdult());
        searchProfile = searchProfile.setSortByAlphabetical(userProfile.isSortByAlphabetical());
        searchProfile = searchProfile.setSortByLatestRelease(userProfile.isSortByLatestRelease());
        searchProfile = searchProfile.setSortByHighestRating(userProfile.isSortByHighestRating());
        searchProfile = searchProfile.setMovie(isMovie);
        searchProfile = searchProfile.setName(entryName);
        return searchProfile;
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public SearchProfile iniitalizeBackSearchProfile() {
        SearchProfile searchProfile = new SearchProfile();
        ArrayList<Integer> newEmptyGenrePref = new ArrayList<>();
        ArrayList<Integer> newEmptyGenreRestrict = new ArrayList<>();
        String newEmptyEntry = "";
        searchProfile = searchProfile.setGenreIdPreference(newEmptyGenrePref);
        searchProfile = searchProfile.setGenreIdRestriction(newEmptyGenreRestrict);
        searchProfile = searchProfile.setAdult(false);
        searchProfile = searchProfile.setSortByAlphabetical(false);
        searchProfile = searchProfile.setSortByHighestRating(false);
        searchProfile = searchProfile.setSortByLatestRelease(false);
        searchProfile = searchProfile.setName(newEmptyEntry);
        searchProfile = searchProfile.setMovie(false);
        return searchProfile;
    }
}
