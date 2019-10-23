package Contexts;

import javafx.util.Pair;
import object.MovieInfoObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultContext {


    static ArrayList<String> keywords = new ArrayList<>();

    static ArrayList<MovieInfoObject> mMovies = new ArrayList<>();

    static ArrayList<MovieInfoObject> mCurrentMovies = new ArrayList<>();

    public static void initialiseContext(String[] listOfKeys) {
        for (String a:listOfKeys) {

            keywords.add(a);
        }
    }

    public static ArrayList<String> getPossibilities(String key) {
        ArrayList<String> hints = new ArrayList<>();
        System.out.print("Getting possibilities");


        for (String a : keywords) {
            if (a.toLowerCase().startsWith(key.toLowerCase())) {
                hints.add(a);
            }
        }
        return hints;
    }

    public static Pair<String, String> getSpellingCheck(String key) {
        return null;
    }

    public static void AddKeyWord(String key) {
        keywords.add(key);
    }

    public static void clearContext() {
        keywords.clear();
    }

    public static void removeKeyWords(String key) {
        keywords.remove(key);

    }

    public static MovieInfoObject getIndex(int i) {
        return mCurrentMovies.get(i - 1);
    }

    public static void clearResults(){

    }

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




        HashMap<Long , Integer> movieDup = new HashMap<Long, Integer>();
        for (MovieInfoObject a: mMovies) {
            movieDup.put(a.getID() , new Integer(1));
        }
        for (MovieInfoObject e: moviesInfo) {
          //  System.out.println(e.getTitle());
            if (movieDup.get(e.getID()) == null) {
             //   System.out.println(e.getTitle());
                mMovies.add(e);
                keywords.add(e.getTitle());
            }
        }

    }

    public static ArrayList<MovieInfoObject> getMoviesToDisplay() {
        return mMovies;
    }
}