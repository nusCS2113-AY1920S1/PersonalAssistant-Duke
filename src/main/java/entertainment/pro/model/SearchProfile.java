package entertainment.pro.model;

import java.util.ArrayList;

public class SearchProfile extends UserProfile {

    private String name;
    private boolean isMovie;

    public SearchProfile(ArrayList<Integer> genreIdPreference, ArrayList<Integer> genreIdRestriction,
                         boolean adult, boolean sortByAlphabetical, boolean sortByHighestRating,
                         boolean sortByLatestRelease, String name, boolean isMovie) {
        super(genreIdPreference, genreIdRestriction, adult, sortByAlphabetical, sortByHighestRating,
                sortByLatestRelease);
        this.name = name;
        this.isMovie = isMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public void setFromUserPreference(SearchProfile searchProfile, String entryName, boolean isMovie, UserProfile userProfile) {
        searchProfile.setGenreIdPreference(userProfile.getGenreIdPreference());
        searchProfile.setGenreIdRestriction(userProfile.getGenreIdRestriction());
        searchProfile.setAdult(userProfile.isAdult());
        searchProfile.setSortByAlphabetical(userProfile.isSortByAlphabetical());
        searchProfile.setSortByLatestRelease(userProfile.isSortByLatestRelease());
        searchProfile.setSortByHighestRating(userProfile.isSortByHighestRating());
        searchProfile.setMovie(isMovie);
        searchProfile.setName(entryName);
    }

    public void iniitalizeBackSearchProfile(SearchProfile searchProfile) {
        ArrayList<Integer>newEmptyGenrePref = new ArrayList<>();
        ArrayList<Integer>newEmptyGenreRestrict = new ArrayList<>();
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
