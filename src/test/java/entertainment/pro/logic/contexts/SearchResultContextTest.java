package entertainment.pro.logic.contexts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SearchResultContextTest {

    @Test public void testaddResults(){

        ArrayList<MovieInfoObject> searchresults = new ArrayList<MovieInfoObject>(Arrays.asList(
                new MovieInfoObject(1,12,"title 1" ) ,
                new MovieInfoObject(1,15,"title 2" ) ,
                new MovieInfoObject(1,18,"title 3" )));

        ArrayList<String> possibilities = new ArrayList<String>(Arrays.asList("title 1" , "title 2" , "title 3"));

        SearchResultContext.addResults(searchresults);
        ArrayList<String> possible = SearchResultContext.getPossibilities("tit");
        possible.sort(null);
        possibilities.sort(null);

        assertEquals(true ,  possibilities.equals(possible));


    }


}





///**
// * Represents the context for all Root and Subroot commands.
// * Auto complete makes use of helper functions from this class to perform autocompletion for commands
// */
//public class SearchResultContext {
//
//
//    static ArrayList<String> keywords = new ArrayList<>();
//
//    static ArrayList<MovieInfoObject> mMovies = new ArrayList<>();
//
//    static ArrayList<MovieInfoObject> mCurrentMovies = new ArrayList<>();
//
//    public static void initialiseContext(String[] listOfKeys) {
//        for (String a:listOfKeys) {
//            keywords.add(a);
//        }
//    }
//
//    /**
//     * Given the a certain Root command, the possible subRoot commands are returned.
//     * @param key: index of the movie to mark as done
//     * @return Arraylist of possible string values
//     */
//    public static ArrayList<String> getPossibilities(String key) {
//        ArrayList<String> hints = new ArrayList<>();
//
//        for (String a : keywords) {
//            if (a.toLowerCase().startsWith(key.toLowerCase())) {
//                hints.add(a);
//            }
//        }
//        return hints;
//    }
//
//    /**
//     * Gets the search result at a certain index.
//     * @param i: index of the movie to return
//     * @return  Return MovieInfoObject
//     */
//    public static MovieInfoObject getIndex(int i) {
//        return mCurrentMovies.get(i - 1);
//    }
//
//
//    /**
//     * Add results from the search query into search result context.
//     * @param moviesInfo: Arraylist of Search results
//     */
//    public static void addResults(ArrayList<MovieInfoObject> moviesInfo) {
//        mCurrentMovies.clear();
//        for (MovieInfoObject mi : moviesInfo) {
//            mCurrentMovies.add(mi);
//        }
//        if (mMovies.size() == 0) {
//            for (MovieInfoObject mi : moviesInfo) {
//                mMovies.add(mi);
//                keywords.add(mi.getTitle());
//            }
//            return;
//        }
//        HashMap<Long , Integer> movieDup = new HashMap<Long, Integer>();
//        for (MovieInfoObject a: mMovies) {
//            movieDup.put(a.getID() , new Integer(1));
//        }
//        for (MovieInfoObject e: moviesInfo) {
//            if (movieDup.get(e.getID()) == null) {
//                mMovies.add(e);
//                keywords.add(e.getTitle());
//            }
//        }
//
//    }
//
//    /**
//     * Gets the movies to display from the Search Result Context.
//     *
//     * @return Arraylist of MovieInfoObjects
//     */
//    public static ArrayList<MovieInfoObject> getMoviesToDisplay() {
//        return mMovies;
//    }
//}
//
