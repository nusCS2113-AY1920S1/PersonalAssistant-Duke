package entertainment.pro.model;

import java.util.ArrayList;

public class SearchProfile extends UserProfile {

    private String name;
    private boolean isMovie;

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
        super.setUserName(userName);
        super.setUserAge(userAge);
        super.setGenreIdPreference(genreIdPreference);
        super.setGenreIdRestriction(genreIdRestriction);
        super.setAdult(adult);
        super.setPlaylistNames(playlistNames);
        super.setSortByAlphabetical(sortByAlphabetical);
        super.setSortByHighestRating(sortByHighestRating);
        super.setSortByLatestRelease(sortByLatestRelease);
        this.name = name;
        this.isMovie = isMovie;
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
    public void setName(String name) {
        this.name = name;
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
    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public void setFromUserPreference(SearchProfile searchProfile, String entryName,
                                      boolean isMovie, UserProfile userProfile) {
        searchProfile.setGenreIdPreference(userProfile.getGenreIdPreference());
        searchProfile.setGenreIdRestriction(userProfile.getGenreIdRestriction());
        searchProfile.setAdult(userProfile.isAdult());
        searchProfile.setSortByAlphabetical(userProfile.isSortByAlphabetical());
        searchProfile.setSortByLatestRelease(userProfile.isSortByLatestRelease());
        searchProfile.setSortByHighestRating(userProfile.isSortByHighestRating());
        searchProfile.setMovie(isMovie);
        searchProfile.setName(entryName);
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public void iniitalizeBackSearchProfile(SearchProfile searchProfile) {
        ArrayList<Integer> newEmptyGenrePref = new ArrayList<>();
        ArrayList<Integer> newEmptyGenreRestrict = new ArrayList<>();
        String newEmptyEntry = "";
        searchProfile.setGenreIdPreference(newEmptyGenrePref);
        searchProfile.setGenreIdRestriction(newEmptyGenreRestrict);
        searchProfile.setAdult(false);
        searchProfile.setSortByAlphabetical(false);
        searchProfile.setSortByHighestRating(false);
        searchProfile.setSortByLatestRelease(false);
        searchProfile.setName(newEmptyEntry);
        searchProfile.setMovie(false);
    }
}
