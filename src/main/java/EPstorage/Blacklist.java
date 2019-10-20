package EPstorage;

import object.MovieInfoObject;

import java.util.ArrayList;

public class Blacklist {

    private static ArrayList<String>  blackListKeyWords = new ArrayList<>();
    private static ArrayList<MovieModel>  blackListMovies = new ArrayList<>();
    private static ArrayList<String>  blackListMoviesTitle = new ArrayList<>();


    public static void initialiseAll(ArrayList<String> keywords , ArrayList<String> movieTitles , ArrayList<MovieModel> movies) {
        initialiseBlackListKey(keywords);
        initialiseBlackListMovieID(movies);
        initialiseBlackListMovieTitles(movieTitles);
    }

    public static void initialiseBlackListKey(ArrayList<String> keywords) {
        blackListKeyWords = (ArrayList<String>) keywords.clone();
    }


    public static void initialiseBlackListMovieTitles(ArrayList<String> movieTitles) {
        blackListMoviesTitle = (ArrayList<String>) movieTitles.clone();
    }


    public static void initialiseBlackListMovieID(ArrayList<MovieModel> movies) {
        for (MovieModel m : movies) {


//            System.out.print(m.getSummary());
            blackListMovies.add(m);
        }
    }

    /**
     * Adding keywords to blacklist.
     *
     * @param movie command that was entered by the user in split array form
     */
    public static void addToBlacklistKeyWord(String movie) {
        if (movie.trim() == "") {
            return;
        }
        blackListKeyWords.add(movie.toLowerCase());
        saveBlackList();
    }

    public static void addToBlacklistMoviesID(MovieInfoObject mo) {
        if (mo == null) {
            return;
        }
        blackListMovies.add(new MovieModel(mo.getID() , mo.getTitle()));
        saveBlackList();
    }

    public static void addToBlacklistMovie(String movie) {
        if (movie.trim() == "") {
            return;
        }
        blackListMoviesTitle.add(movie.toLowerCase());
        saveBlackList();
    }

    private static void saveBlackList() {
        try {
            BlacklistStorage allbl = new BlacklistStorage();
            allbl.updateBlacklistFile(blackListKeyWords , blackListMovies , blackListMoviesTitle);
        } catch (Exception e) {
            //TODO ADD exception handling
        }


    }

    public static boolean removeFromBlacklistKeyWord(String movie)  {
        if (movie.trim() == "") {
            return false;
        }
        ArrayList<String> newKeywords = (ArrayList<String>) blackListKeyWords.clone();
        for (String mo : newKeywords) {
            if (mo.toLowerCase().contains(movie.toLowerCase()) && blackListKeyWords.contains(mo)) {
                blackListKeyWords.remove(mo);
                return true;
            }
        }
        return false;


    }

    public static boolean removeFromBlacklistMovieTitle(String movie)  {
        if (movie.trim() == "") {
            return false;
        }

        boolean statTitle = removeMovieTitle(movie);
        boolean statObj = removeMovieObj(movie);

        return statObj || statTitle;

    }

    public static boolean removeFromBlacklistMovies(MovieInfoObject movie)  {
        if (movie != null) {
            return false;
        }

        boolean statTitle = removeMovieTitle(movie.getTitle());
        boolean statObj = removeMovieObjById(movie);

        return statObj || statTitle;

    }

    public static boolean removeMovieTitle(String movie)  {

        if (blackListMoviesTitle.contains(movie.toLowerCase())) {
            blackListMoviesTitle.remove(movie.toLowerCase());
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeMovieObj(String movie)  {

        for (MovieModel mo : blackListMovies) {
            if (mo.getTitle().toLowerCase() == movie.toLowerCase()) {
                blackListMovies.remove(mo);
                return true;
            }
        }
        return false;
    }

    public static boolean removeMovieObjById(MovieInfoObject movie)  {

        for (MovieModel mo : blackListMovies) {
            if (mo.getId() == movie.getID()) {
                blackListMovies.remove(mo);
                return true;
            }
        }
        return false;
    }

    public static String getIndexMovie(int index) {
        if (index >= blackListKeyWords.size()) {
            return "";
        }
        return blackListKeyWords.get(index - 1);
    }

    public static String getIndexKeyWord(int index) {
        if (index >= blackListKeyWords.size()) {
            return "";
        }
        return blackListKeyWords.get(index - 1);
    }

    public static void clearBlacklist() {
        blackListMoviesTitle.clear();
        blackListMovies.clear();
        blackListKeyWords.clear();
    }

    public static String printList() {
        String feedback = "Blacklisted Keywords: \n";
        int i  = 1;
        for (String e : blackListKeyWords) {
            feedback += String.valueOf(i);
            feedback += ") ";
            feedback += e;
            feedback += "\t\t";
            i++;
            if (i % 4 == 0) {
                feedback += "\n";
            }
        }

        feedback += "\nBlacklisted Movies: \n";
        i  = 1;
        for (String e : blackListMoviesTitle) {
            feedback += String.valueOf(i);
            feedback += ") ";
            feedback += e;
            feedback += "\t\t";
            i++;
            if (i % 4 == 0) {
                feedback += "\n";
            }
        }

        for (MovieModel e : blackListMovies) {
            feedback += String.valueOf(i);
            feedback += ") ";
            feedback += e.getTitle();
            feedback += "\t\t";
            i++;
            if (i % 4 == 0) {
                feedback += "\n";
            }
        }

        return feedback;
    }

    public static ArrayList<String> getBlackListMovies() {
        return (ArrayList<String>) blackListKeyWords.clone();
    }

    public static String printHint() {
        String feedback = "";
        int i  = 1;
        for (String e : blackListKeyWords) {

            feedback += e;

            feedback += "\n";

        }

        return feedback;
    }

    public static ArrayList<MovieInfoObject> filter(ArrayList<MovieInfoObject> mMovies) {
        mMovies = filterByKeyword(mMovies);
        mMovies = filterById(mMovies);
        mMovies = filterByTitle(mMovies);

        return mMovies;
    }

    private static ArrayList<MovieInfoObject> filterByKeyword(ArrayList<MovieInfoObject> mMovies) {
        ArrayList<MovieInfoObject> filteredMovies = new ArrayList<>();
        for (MovieInfoObject o : mMovies) {
            boolean isBlacklisted = false;
            for (String e : blackListKeyWords) {
                if (o.getTitle().toLowerCase().contains(e.toLowerCase())) {
                    isBlacklisted = true;
                    break;
                }
            }
            if (!isBlacklisted) {
                filteredMovies.add(o);
            }
        }
        return filteredMovies;
    }

    private static ArrayList<MovieInfoObject> filterById(ArrayList<MovieInfoObject> mMovies) {
        ArrayList<MovieInfoObject> filteredMovies = new ArrayList<>();
        for (MovieInfoObject o : mMovies) {
            boolean isBlacklisted = false;
            for (MovieModel e : blackListMovies) {
                if (o.getID() == e.getId()) {
                    isBlacklisted = true;
                }
            }
            if (!isBlacklisted) {
                filteredMovies.add(o);
            }
        }


        return filteredMovies;
    }

    private static ArrayList<MovieInfoObject> filterByTitle(ArrayList<MovieInfoObject> mMovies) {
        ArrayList<MovieInfoObject> filteredMovies = new ArrayList<>();
        for (MovieInfoObject o : mMovies) {
            boolean isBlacklisted = false;
            for (String e : blackListMoviesTitle) {
                if (o.getTitle().toLowerCase().equals(e.toLowerCase())) {
                    isBlacklisted = true;
                }
            }
            if (!isBlacklisted) {
                filteredMovies.add(o);
            }
        }


        return filteredMovies;
    }


}

