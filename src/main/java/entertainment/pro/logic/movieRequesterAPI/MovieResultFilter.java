package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;

public class MovieResultFilter {
    ArrayList<Integer> genrePreference;
    ArrayList<Integer> genreRestriction;

    public MovieResultFilter(ArrayList<Integer> genrePreference, ArrayList<Integer> genreRestriction) {
        this.genrePreference = genrePreference;
        this.genreRestriction = genreRestriction;
    }

    public boolean isFitGenrePreference(MovieInfoObject movie) {
        long[] movieGenre = movie.getGenreIDs();
        for (long log : movieGenre) {
            for (Integer preferenceLog : genrePreference) {
                if (log == preferenceLog) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFitGenreRestriction(MovieInfoObject movie) {
        long[] movieGenre = movie.getGenreIDs();
        for (long log : movieGenre) {
            for (Integer preferenceLog : genreRestriction) {
                if (log == preferenceLog) {
                    return false;
                }
            }
        }
        return true;
    }
}
