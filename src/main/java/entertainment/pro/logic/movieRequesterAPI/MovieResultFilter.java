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
