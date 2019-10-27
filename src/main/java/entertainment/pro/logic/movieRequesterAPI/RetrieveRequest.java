package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.movieRequesterAPI.InfoFetcher;
import entertainment.pro.logic.movieRequesterAPI.RequestListener;
import entertainment.pro.logic.parsers.TimeParser;
import entertainment.pro.model.SearchProfile;
import org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import entertainment.pro.model.MovieInfoObject;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for fetching results from The MovieDB API and parsing them into objects.
 */
public class RetrieveRequest implements InfoFetcher {
    private RequestListener mListener;
    private ArrayList<MovieInfoObject> p_Movies;


    SearchProfile searchProfile;

    public void setSearchProfile(SearchProfile searchProfile) {
        this.searchProfile = searchProfile;
    }


    static boolean isCast = false;

    // API Usage constants
    private static final String MAIN_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "5d467eb39777ed643edb8312fb71c157";

    // Data Request URL's for movies
    private static final String CURRENT_MOVIE_URL = "movie/now_playing?api_key=";
    private static final String POPULAR_MOVIE_URL = "movie/popular?api_key=";
    private static final String UPCOMING_MOVIE_URL = "movie/upcoming?api_key=";
    private static final String MOVIE_SEARCH_URL = "search/movie?api_key=";
    private static final String TRENDING_MOVIE_URL = "trending/movie/day?api_key=";
    private static final String GENRE_LIST_MOVIE_URL = "genre/movie/list?api_key=";

    // Data Request URL's for TV shows
    private static final String TV_SEARCH_URL = "";
    private static final String CURRENT_TV_URL = "tv/on_the_air?api_key=";
    private static final String POPULAR_TV_URL = "tv/popular?api_key=";
    private static final String NEW_TV_URL = "tv/latest?api_key=";
    private static final String TRENDING_TV_URL = "trending/tv/day?api_key=";
    private static final String GENRE_LIST_TV_URL = "genre/tv/list?api_key=";


    // Data Keys for both movie and TV shows
    private static final String ADD_ADULT_OPTION = "&include_adult=";
    private static final String kMOVIE_TITLE = "title";
    private static final String kTV_TITLE = "original_name";
    private static final String kMOVIE_RELEASE_DATE = "release_date";
    private static final String kMOVIE_ID = "id";
    private static final String kMOVIE_GENRES = "genre_ids";
    private static final String kMOVIE_SUMMARY = "overview";
    private static final String kMOVIE_RATING = "vote_average";
    private static final String kMOVIE_BACKDROP_PATH = "backdrop_path";
    private static final String kMOVIE_POSTER_PATH = "poster_path";

    private String string;

    private static final String kMOVIE_CAST = "cast_id";
    private static final String kADULT = "adult";

    public static String getCastStrings(MovieInfoObject mMovie) throws Exceptions {

        try {
            isCast = true;
            String jsonResult = "";
            boolean isMovie = mMovie.isMovie();
            if (isMovie) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "movie/" + mMovie.getID() + "/credits?api_key=" +
                    RetrieveRequest.API_KEY));
            } else {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "tv/" + mMovie.getID() + "/credits?api_key=" +
                    RetrieveRequest.API_KEY));

            }
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray casts = (JSONArray) jsonData.get("cast");
            String castStrings = "";
            for (int i = 0; i < casts.size(); i += 1) {
                //if (i == 10) {
                //  break;
                //}
                JSONObject castPair = (JSONObject) casts.get(i);
                castStrings += castPair.get("name");
                // if (i != casts.size() - 1) {
                if (i != casts.size() - 1) {
                    castStrings += ", ";
                }
            }

            return castStrings;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (org.json.simple.parser.ParseException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String getCertStrings(MovieInfoObject mMovie) throws Exceptions {
        try {
            String jsonResult = "";
            boolean isMovie = mMovie.isMovie();
            if (isMovie) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "movie/" + mMovie.getID() + "/release_dates?api_key=" +
                    RetrieveRequest.API_KEY));
            } else {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "tv/" + mMovie.getID() + "/content_ratings?api_key=" +
                    RetrieveRequest.API_KEY + "&language=en-US"));
            }
           // System.out.println(MAIN_URL + "movie/" + mMovie.getID() + "/release_dates?api_key=" +
             //   RetrieveRequest.API_KEY);
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray casts = (JSONArray) jsonData.get("results");
            //System.out.println("yes1");
            String certStrings = "";
            String ret = "N/A";
            if (isMovie) {
                for (int i = 0; i < casts.size(); i += 1) {
                    JSONObject castPair = (JSONObject) casts.get(i);
                    if (castPair.get("iso_3166_1").equals("GB")) {
                        Map cert = (Map) casts.get(i);
                        Iterator<Map.Entry> itr1 = cert.entrySet().iterator();
                        while (itr1.hasNext()) {
                            Map.Entry pair = itr1.next();
                            if (pair.getKey().equals("release_dates")) {
                                certStrings = pair.getValue().toString();
                            }
                        }
                       // System.out.println("this is:" + certStrings);
                        String[] getCert = certStrings.strip().split("certification");
                        if (getCert.length == 2) {
                            ret = getCert[1].substring(2, getCert[1].length() - 2);
                        } else {
                            ret = getCert[getCert.length - 1].substring(2, getCert[getCert.length - 1].length() - 2);
                        }
                    }
                }
            } else {
                for (int i = 0; i < casts.size(); i += 1) {
                    JSONObject castPair = (JSONObject) casts.get(i);
                    if (castPair.get("iso_3166_1").equals("GB")) {
                        certStrings = castPair.get("rating").toString();
                        ret = "Suitable for " +
                            certStrings + " years & above";
                    }
                }
            }
          //  System.out.println("this is cert" + ret);
            return ret;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (org.json.simple.parser.ParseException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;

    }

    public enum MoviesRequestType {
        CURRENT_MOVIES,
        POPULAR_MOVIES,
        UPCOMING_MOVIES,
        TRENDING_MOVIES,
        SEARCH_MOVIES,
        CURRENT_TV,
        POPULAR_TV,
        TRENDING_TV,
        NEW_TV
    }

    public RetrieveRequest(RequestListener listener) throws Exceptions {
        mListener = listener;
        // Check if config is needed
        checkIfConfigNeeded();
    }

    public void beginMovieRequest(RetrieveRequest.MoviesRequestType type) throws Exceptions {
        boolean isAdult;
        try {
            isAdult = searchProfile.isAdult();
        } catch (NullPointerException e) {
            isAdult = false;
        }
        String requestURL = RetrieveRequest.MAIN_URL;
        switch (type) {
            case CURRENT_MOVIES:
                requestURL += RetrieveRequest.CURRENT_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&region=SG";
                break;
            case POPULAR_MOVIES:
                requestURL += RetrieveRequest.POPULAR_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&region=SG";
                break;
            case UPCOMING_MOVIES:
                requestURL += RetrieveRequest.UPCOMING_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&region=SG";
                break;
            case TRENDING_MOVIES:
                requestURL += RetrieveRequest.TRENDING_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&region=SG";
                    break;
            case CURRENT_TV:
                requestURL += RetrieveRequest.CURRENT_TV_URL + RetrieveRequest.API_KEY;
                break;
            case POPULAR_TV:
                requestURL += RetrieveRequest.POPULAR_TV_URL + RetrieveRequest.API_KEY;
                break;
            case TRENDING_TV:
                requestURL += RetrieveRequest.TRENDING_TV_URL + RetrieveRequest.API_KEY;
                break;
            case NEW_TV:
                requestURL += RetrieveRequest.NEW_TV_URL + RetrieveRequest.API_KEY;
                break;
            case SEARCH_MOVIES:
                try {
                    requestURL += RetrieveRequest.MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(searchProfile.getName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;

            default:
                requestURL = null;
        }
        requestURL += ADD_ADULT_OPTION + isAdult;
        System.out.println(requestURL);
        fetchJSONData(requestURL);
    }


    public String beginAddRequest(String movieTitle) throws Exceptions{
        try {
            String url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8");
            URLRetriever retrieve = new URLRetriever();
            String json = retrieve.readURLAsString(new URL(url));
            fetchedMoviesJSON(json);
            return p_Movies.get(0).getTitle();
        } catch (UnsupportedEncodingException | MalformedURLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public ArrayList<MovieInfoObject> beginSearchGenre (String genre, boolean adult) throws Exceptions {
        try {
            String url = MAIN_URL + "discover/movie?with_genres=" + URLEncoder.encode(genre, "UTF-8") + "&api_key="
                    + API_KEY + "&language=en-US&page=1" + "&include_adult=";
            url += adult;
            URLRetriever retrieve = new URLRetriever();
            String json = retrieve.readURLAsString(new URL(url));
            fetchedMoviesJSON(json);
            //fetchJSONData(url);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return p_Movies;
    }


    // JSON data was fetched by the fetcher
    @Override
    public void fetchedMoviesJSON(String json) {
        // If null string returned then there was a lack of internet connection
        //System.out.println("so far ok0");
        if (json == null) {
            mListener.requestFailed();
            //System.out.println("so far not ok");
            return;
        }
        JSONParser parser = new JSONParser();
        JSONObject movieData;
        try {
            movieData = (JSONObject) parser.parse(json);
            JSONArray movies = new JSONArray();

                movies = (JSONArray) movieData.get("results");
                if (movies == null)
                    System.out.println("mannns");



        ArrayList<MovieInfoObject> parsedMovies = new ArrayList(20);


            for (int i = 0; i < movies.size(); i++) {
                if (checkCondition((JSONObject) movies.get(i))) {
                    parsedMovies.add(parseMovieJSON((JSONObject) movies.get(i)));
                    //System.out.println("yeesss");
                }
            }
            if (searchProfile.isSortByAlphabetical()) {
                //System.out.println("nooo");
                //sortByAlphaOrder(parsedMovies);
                Collections.sort(parsedMovies, new Comparator<MovieInfoObject>() {
                    public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                        return v2.getReleaseDate().compareTo(v1.getReleaseDate());
                    }
                });
            } else if (searchProfile.isSortByLatestRelease()) {
                //System.out.println("boooo");
                Collections.sort(parsedMovies, new Comparator<MovieInfoObject>() {
                    public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                        return v2.getReleaseDate().compareTo(v1.getReleaseDate());
                    }
                });
            } else if (searchProfile.isSortByHighestRating()) {
                Collections.sort(parsedMovies, new Comparator<MovieInfoObject>() {
                    public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                        return Double.compare(v2.getRating(), v1.getRating());
                    }
                });
            }


            mListener.requestCompleted(parsedMovies);
            p_Movies = parsedMovies;
            //} else {

            //}
           // System.out.println("so far ok3");
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // The fetcher reported a connection time out. Notify the request listener.
    /**
     * The function is called when the fetcher reported a connection time out.
     * Notify the request listener.
     */
    @Override
    public void connectionTimedOut() {
        mListener.requestTimedOut();
    }

    private void fetchJSONData(String URLString) throws Exceptions {
        Thread fetchThread = null;
        try {
            fetchThread = new Thread(new MovieInfoFetcher(new URL(URLString), this));
            fetchThread.start();
            //System.out.println("bef MovieInfoFetcher");
        } catch (MalformedURLException ex) {
            Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Parses the given JSON string for a movie into a MovieInfo object.
     */
    private MovieInfoObject parseMovieJSON(JSONObject movieData) {
        //long ID = (long) movieData.get(kMOVIE_ID);
        //boolean adult = (boolean) movieData.get(kADULT);
        String title = "";
       boolean isMovie = false;
       if (searchProfile.isMovie()) {
                title = (String) movieData.get(kMOVIE_TITLE);
                isMovie = true;
            } else {
                title = (String) movieData.get(kTV_TITLE);
            }
        long ID = (long) movieData.get(kMOVIE_ID);
        double rating = 0.0;
        try {
            rating = (double) movieData.get(kMOVIE_RATING);
        } catch (ClassCastException ex) {
            // the rating was parsed with a long value, cast to double (issue in simple json library)
            Long longRating = (Long) movieData.get(kMOVIE_RATING);
            rating = longRating.doubleValue();
        }

        String summary = (String) movieData.get(kMOVIE_SUMMARY);

        // Parse genre id array
        JSONArray genreIDsJsonArray = (JSONArray) movieData.get(kMOVIE_GENRES);
        long[] genreIDs = new long[genreIDsJsonArray.size()];
        for (int i = 0; i < genreIDs.length; i++) {
            genreIDs[i] = (long) genreIDsJsonArray.get(i);
        }

        // Parse date string from json
        //SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        Date releaseDate = null;

        //try {
        String releaseDateString = (String) movieData.get(kMOVIE_RELEASE_DATE);
        //System.out.println("date is" + releaseDateString);

        if (releaseDateString != null) {
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                releaseDate = formatter1.parse(releaseDateString);
                //System.out.println(releaseDate);
            } catch (ParseException e) {
                releaseDate = null;
            }
        }

        // Get poster and backdrop paths
        String posterPath = (String) movieData.get(kMOVIE_POSTER_PATH);
        String backdropPath = (String) movieData.get(kMOVIE_BACKDROP_PATH);

        MovieInfoObject movieInfo = new MovieInfoObject(isMovie, ID, title, releaseDate, summary, rating,
                genreIDs,posterPath, backdropPath, searchProfile.isAdult());

        // If the base url was fetched and loaded, set the root path and poster size
        if (mImageBaseURL != null) {
            movieInfo.setPosterRootPath(mImageBaseURL, mPosterSizes[mPosterSizes.length - 3], mBackdropSizes[mBackdropSizes.length - 1]);
        }

        return movieInfo;
    }

    // Config URL
    private final static String CONFIG_URL = MAIN_URL + "configuration?api_key=" + API_KEY;

    // Config constants
    private static final int DAYS_TILL_RECACHE = 5;
    private static final String CONFIG_FILE_NAME = "config.dat";

    // Config Keys
    private static final String kCONFIG_BASE_URL = "base_url";
    private static final String kCONFIG_SECURE_BASE_URL = "secure_base_url";
    private static final String kCONFIG_BACKDROP_SIZES = "backdrop_sizes";
    private static final String kCONFIG_POSTER_SIZES = "poster_sizes";

    // Config values
    private static Date mLastConfigCacheDate;
    private static String mImageBaseURL;
    private static String mImageSecureBaseURL;
    private static String[] mPosterSizes;
    private static String[] mBackdropSizes;
    private boolean mConfigWasRead;

    // Checks if API config data needs to be recached
    private void checkIfConfigNeeded() throws Exceptions {
        boolean configNeeded = true;

        // Get last cache date and reconfig if more than 5 days passed
        File configFile = new File(CONFIG_FILE_NAME);

        if (configFile.exists() && !configFile.isDirectory()) {
            readConfigData();

            // Parse date and if more than 5 days passed, a recache is required
            Date now = new Date();
            int diffInDays = (int) (now.getTime() - mLastConfigCacheDate.getTime()) / (1000 * 3600 * 24);

            if (diffInDays < DAYS_TILL_RECACHE) {
                configNeeded = false;
            }
        }

        if (configNeeded || !mConfigWasRead) {
            //System.out.println("Config recache needed");
            reCacheConfigData();
        } else {
            // No config needed - read cached data from config file
            //System.out.println("Found a cache and config was not required");
            readConfigData();
        }
    }

    // Reads in the config data from disk
    private void readConfigData() {
        try {
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(CONFIG_FILE_NAME));
            mLastConfigCacheDate = (Date) file.readObject();
            mImageBaseURL = file.readUTF();
            mImageSecureBaseURL = file.readUTF();
            mPosterSizes = (String[]) file.readObject();
            mBackdropSizes = (String[]) file.readObject();

            mConfigWasRead = true;
            file.close();
        } catch (FileNotFoundException ex) {
            // No file found, config will be recached
            mConfigWasRead = false;
        } catch (IOException ex) {
            // Error reading - config will be recached
            mConfigWasRead = false;
        } catch (ClassNotFoundException ex) {
            mConfigWasRead = false;
        }
    }

    // Writes out the config data to file
    // NOTE: Only call after all config data was recached
    private void writeConfigData() {
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE_NAME));
            file.writeObject(new Date());
            file.writeUTF(mImageBaseURL);
            file.writeUTF(mImageSecureBaseURL);
            file.writeObject(mPosterSizes);
            file.writeObject(mBackdropSizes);
            file.close();
        } catch (IOException ex) {
            // Failed to write, data will be not be cached and will be recached on next run
            System.err.println("Error: Unable to cache config data: \n" + ex.getMessage());
        }
    }

    // Re-caches the config data to the binary config file
    private void reCacheConfigData() throws Exceptions {
        try {
            // Download the config data and parse
            //System.out.println("Config URL is: " + CONFIG_URL);
            String configJSON = URLRetriever.readURLAsString(new URL(CONFIG_URL));
            JSONObject configRootData = null;

            if (configJSON != null) {
                JSONParser parser = new JSONParser();
                try {
                    configRootData = (JSONObject) parser.parse(configJSON);
                    JSONObject imageConfigData = (JSONObject) configRootData.get("images");

                    // Get the base url data
                    mImageBaseURL = (String) imageConfigData.get(kCONFIG_BASE_URL);
                    mImageSecureBaseURL = (String) imageConfigData.get(kCONFIG_SECURE_BASE_URL);

                    // Get the string arrays for the poster and backdrop size strings
                    JSONArray posterSizesData = (JSONArray) imageConfigData.get(kCONFIG_POSTER_SIZES);
                    JSONArray backdropSizesData = (JSONArray) imageConfigData.get(kCONFIG_BACKDROP_SIZES);
                    mPosterSizes = Arrays.copyOf(posterSizesData.toArray(), posterSizesData.toArray().length, String[].class);
                    mBackdropSizes = Arrays.copyOf(backdropSizesData.toArray(), backdropSizesData.toArray().length, String[].class);

                    writeConfigData();
                } catch (org.json.simple.parser.ParseException ex) {
                    // Failed to parse... TODO: Call listener and notify error
                }
            }
        } catch (IOException ex) {
            // Failed to download config data...
            // TODO: Call listener and notify error
        }
    }

    /**
     * Fetches the strings for the genres for the given move. Note: This is operation is NOT asynchronous.
     *
     * @param movie The movie for which the genre strings need to be fetched.
     * @return A string array for the movie genre strings.
     */
    public static String[] getGenreStrings(MovieInfoObject movie) throws Exceptions {
        try {
            String jsonResult = "";
            if (movie.isMovie()) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + GENRE_LIST_MOVIE_URL + API_KEY));
            } else {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + GENRE_LIST_TV_URL + API_KEY));


            }
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray genres = (JSONArray) jsonData.get("genres");

            Set<Integer>genrePref = new HashSet<>();
            Set<Integer>genreRestric = new HashSet<>();
            String[] genreStrings = new String[movie.getGenreIDs().length];
            for (int i = 0; i < movie.getGenreIDs().length; i++) {
                genreStrings[i] = getGenreStringForID(movie.getGenreIDs()[i], genres);
            }

            return genreStrings;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (org.json.simple.parser.ParseException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Returns the genre string for the given genre ID using the given JSONArray of dictionaries of {ID: Genre String}
    private static String getGenreStringForID(long genreID, JSONArray genreList) {
        String genre = null;

        for (int i = 0; i < genreList.size(); i++) {
            JSONObject genrePair = (JSONObject) genreList.get(i);
            if ((long) genrePair.get("id") == genreID) {
                genre = (String) genrePair.get("name");
            }
        }

        return genre;
    }


    private boolean checkCondition(JSONObject entryInfo) {

            Set<Long> genrePref = new HashSet<>();
            Set<Long> genreRestric = new HashSet<>();
            boolean haveGenrePref = true;
            boolean haveGenreRestrict = true;
            try {
                for (int i = 0; i < searchProfile.getGenreIdPreference().size(); i += 1) {
                    genrePref.add(Long.valueOf(searchProfile.getGenreIdPreference().get(i)));
                }
            } catch (NullPointerException e) {
                haveGenrePref = false;
            }
            if (genrePref.size() == 0) {
                haveGenrePref = false;
            }
            try {
                for (int i = 0; i < searchProfile.getGenreIdRestriction().size(); i += 1) {
                    genreRestric.add(Long.valueOf(searchProfile.getGenreIdRestriction().get(i)));
                }
            } catch (NullPointerException e) {
                haveGenreRestrict = false;
            }
            JSONArray jsonArray = (JSONArray) entryInfo.get("genre_ids");
            boolean containPrefGenre = false;

             //   System.out.println("this is set " + genrePref);

            for (int i = 0; i < jsonArray.size(); i += 1) {
               // System.out.println(jsonArray.get(i));
                if (genreRestric.contains((long) jsonArray.get(i))) {
                   // System.out.println("this2");
                    return false;
                } else if (genrePref.contains((long) jsonArray.get(i))) {
                    containPrefGenre = true;
                 //   System.out.println("mannns");
                }
            }
            if ((containPrefGenre) || !(haveGenrePref)) {
               // System.out.println("ahh");
                return true;

            } else {
               // System.out.println("afff");
                return false;

            }
    }
}
