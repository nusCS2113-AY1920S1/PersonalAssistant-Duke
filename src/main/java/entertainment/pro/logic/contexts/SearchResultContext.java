package entertainment.pro.logic.contexts;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the context for all Root and Subroot commands.
 * Auto complete makes use of helper functions from this class to perform autocompletion for commands
 */
public class SearchResultContext {

    static ArrayList<String> keywords = new ArrayList<>();

    static ArrayList<MovieInfoObject> mMovies = new ArrayList<>();

    static ArrayList<MovieInfoObject> mCurrentMovies = new ArrayList<>();

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public static void initialiseContext(String[] listOfKeys) {
        for (String a : listOfKeys) {
            keywords.add(a);
        }
    }

    /**
     * Given the a certain Root command, the possible subRoot commands are returned.
     *
     * @param key index of the movie to mark as done
     * @return Arraylist of possible string values
     */
    public static ArrayList<String> getPossibilities(String key) {
        ArrayList<String> hints = new ArrayList<>();

        for (String a : keywords) {
            if (a.toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a);
            }
        }
        return hints;
    }

    /**
     * Gets the search result at a certain index.
     *
     * @param i index of the movie to return
     * @return Return MovieInfoObject
     */
    public static MovieInfoObject getIndex(int i) {
        return mCurrentMovies.get(i - 1);
    }


    /**
     * Add results from the search query into search result context.
     *
     * @param moviesInfo Arraylist of Search results
     */
    public static void addResults(ArrayList<MovieInfoObject> moviesInfo) {
        mCurrentMovies.clear();
        for (MovieInfoObject mi : moviesInfo) {
            mCurrentMovies.add(mi);
        }
        if (mMovies.size() == 0) {
            for (MovieInfoObject mi : moviesInfo) {
                mMovies.add(mi);
                keywords.add(mi.getTitle());
            }
            return;
        }
        HashMap<Long, Integer> movieDup = new HashMap<Long, Integer>();
        for (MovieInfoObject a : mMovies) {
            movieDup.put(a.getId(), new Integer(1));
        }
        for (MovieInfoObject e : moviesInfo) {
            if (movieDup.get(e.getId()) == null) {
                mMovies.add(e);
                keywords.add(e.getTitle());
            }
        }

    }

    /**
     * Gets the movies to display from the Search Result Context.
     *
     * @return Arraylist of MovieInfoObjects
     */
    public static ArrayList<MovieInfoObject> getMoviesToDisplay() {
        return mMovies;
    }
}
