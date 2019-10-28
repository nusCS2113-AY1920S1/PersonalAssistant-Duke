package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;

public class MovieResultFilter {
    private ArrayList<Integer> genrePreference;
    private ArrayList<Integer> genreRestriction;

    public MovieResultFilter(ArrayList<Integer> genrePreference, ArrayList<Integer> genreRestriction) {
        this.genrePreference = genrePreference;
        this.genreRestriction = genreRestriction;
    }

    /**
     * to check if movie fits within genre preferences.
     */
    public boolean isFitGenrePreference(MovieInfoObject movie) {
        ArrayList<Long> movieGenre = movie.getGenreIDInfo();
        for (long log : movieGenre) {
            for (Integer preferenceLog : genrePreference) {
                if (log == preferenceLog) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * to find out if movie fits within genre restrictions.
     */
    public boolean isFitGenreRestriction(MovieInfoObject movie) {
        ArrayList<Long> movieGenre = movie.getGenreIDInfo();
        for (long log : movieGenre) {
            for (Integer preferenceLog : genreRestriction) {
                if (log == preferenceLog) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * to filter movies according to preferences and restrictions.
     */
    public ArrayList<MovieInfoObject> filter(ArrayList<MovieInfoObject> movies) {
        ArrayList<MovieInfoObject> filteredMovies = new ArrayList<>();
        if (!genrePreference.isEmpty() && !genreRestriction.isEmpty()) {
            for (MovieInfoObject log : movies) {
                if (isFitGenrePreference(log) && isFitGenreRestriction(log)) {
                    filteredMovies.add(log);
                }
            }
        } else if (!genrePreference.isEmpty()) {
            for (MovieInfoObject log : movies) {
                if (isFitGenrePreference(log)) {
                    filteredMovies.add(log);
                }
            }
        } else if (!genreRestriction.isEmpty()) {
            for (MovieInfoObject log : movies) {
                if (isFitGenreRestriction(log)) {
                    filteredMovies.add(log);
                }
            }
        }
        if (genrePreference.isEmpty() && genreRestriction.isEmpty()) {
            filteredMovies = movies;
        }
        return filteredMovies;
    }
}
