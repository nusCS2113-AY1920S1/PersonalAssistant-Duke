package movieRequesterAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import object.MovieInfoObject;

import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RetrieveRequest implements InfoFetcher, InfoFetcherWithGenre {
    private RequestListener mListener;
    static int index = 0;

    // API Usage constants
    private static final String MAIN_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "2a888e02edd08043185889ba862cb073";

    // Movie Data Request URL's for movies
    private static final String CURRENT_MOVIE_URL = "movie/now_playing?api_key=";
    private static final String POPULAR_MOVIE_URL = "movie/popular?api_key=";
    private static final String UPCOMING_MOVIE_URL = "movie/upcoming?api_key=";
    private static final String MOVIE_SEARCH_URL = "search/movie?api_key=";
    private static final String TRENDING_MOVIE_URL = "trending/movie/day?api_key=";
    private static final String GENRE_LIST_MOVIE_URL = "genre/movie/list?api_key=";
    private static final String MOVIE_CAST = "/credits?api_key=";
    //=====================================================
    private static final String TV_SEARCH_URL = "";
    private static final String CURRENT_TV_URL = "tv/on_the_air?api_key=";
    private static final String POPULAR_TV_URL = "tv/popular?api_key=";
    private static final String NEW_TV_URL = "tv/latest?api_key=";
    private static final String TRENDING_TV_URL = "trending/tv/day?api_key=";
    private static final String GENRE_LIST_TV_URL = "genre/tv/list?api_key=";
    //======================================================
    private static final String POP_CAST_URL = "person/popular?api_key=";

    private static final String LIST = "/lists?api_key=";


    // Movie Data Keys
    private static final String kMOVIE_TITLE = "title";
    private static final String kTV_TITLE = "original_name";
    private static final String kMOVIE_RELEASE_DATE = "release_date";
    private static final String kMOVIE_ID = "id";
    private static final String kMOVIE_GENRES = "genre_ids";
    private static final String kMOVIE_SUMMARY = "overview";
    private static final String kMOVIE_RATING = "vote_average";
    private static final String kMOVIE_BACKDROP_PATH = "backdrop_path";
    private static final String kMOVIE_POSTER_PATH = "poster_path";
    private static final String kMOVIE_CAST = "cast_id";


    public enum MoviesRequestType {
        CURRENT_MOVIES,
        POPULAR_MOVIES,
        UPCOMING_MOVIES,
        CURRENT_TV,
        POPULAR_TV,
        UPCOMING_TV,
        TRENDING_MOVIES,
        TRENDING_TV,
        POP_CAST,
        NEW_TV
    }

    public RetrieveRequest(RequestListener listener) {
        mListener = listener;

        // Check if config is needed
        checkIfConfigNeeded();
    }

    public void beginMovieRequest(RetrieveRequest.MoviesRequestType type) {
        String requestURL = RetrieveRequest.MAIN_URL;
        switch (type) {
            case CURRENT_MOVIES:
                requestURL += RetrieveRequest.CURRENT_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG";
                break;
            case POPULAR_MOVIES:
                requestURL += RetrieveRequest.POPULAR_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG";
                break;
            case UPCOMING_MOVIES:
                requestURL += RetrieveRequest.UPCOMING_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG";
                break;
            case TRENDING_MOVIES:
                requestURL += RetrieveRequest.TRENDING_MOVIE_URL + RetrieveRequest.API_KEY;
                break;
            case CURRENT_TV:
                requestURL += RetrieveRequest.CURRENT_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1";
                index = 1;
                break;
            case POPULAR_TV:
                requestURL += RetrieveRequest.POPULAR_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1";
                index = 1;
                break;
            case POP_CAST:
                requestURL += RetrieveRequest.POP_CAST_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1";
                break;
            case TRENDING_TV:
                requestURL += RetrieveRequest.TRENDING_TV_URL + RetrieveRequest.API_KEY;
                index = 1;
                break;
            case NEW_TV:
                requestURL += RetrieveRequest.NEW_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1";
                break;
            default:
                requestURL = null;
        }

        fetchJSONData(requestURL);
    }


    public void beginMovieSearchRequest(String movieTitle) {
        try {
            String url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8");
            fetchJSONData(url);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public void beginMovieSearchRequestWithGenre(String movieTitle, ArrayList<Integer> genreID) {
        try {
            String url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8");
            fetchJSONDataWithGenre(url, genreID);

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public void beginSearchGenre (String genre) {
        try {
            String url = MAIN_URL + "movie/" + URLEncoder.encode(genre, "UTF-8") + LIST
                    + API_KEY + "&language=en-US&page=1";
            fetchJSONData(url);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * public String getCastMovie (long id) {
     * String url = MAIN_URL + "movie/" + id + API_KEY;
     * ArrayList<String> castInfo = fetchCastData(url);
     * String cast = castInfo.get(0);
     * for (int i = 1; i < castInfo.size(); i += 1) {
     * cast += "," + castInfo.get(i);
     * }
     * return cast;
     * }
     * <p>
     * public ArrayList<String> fetchCastData(String url) {
     * if (url == null) {
     * mListener.requestFailed();
     * //System.out.println("so far not ok");
     * return null;
     * }
     * //System.out.println("so far ok1");
     * <p>
     * // Parse received movies
     * JSONParser parser = new JSONParser();
     * JSONObject movieData;
     * try {
     * String json = URLRetriever.readURLAsString(new URL(url));
     * movieData = (JSONObject) parser.parse(json);
     * JSONArray movies = (JSONArray) movieData.get("cast");
     * ArrayList<String> parsedMovies = new ArrayList(10);
     * for (int i = 0; i < movies.size(); i++) {
     * parsedMovies.add(parseCastJSON((JSONObject) movies.get(i)));
     * }
     * return parsedMovies;
     * //System.out.println("so far ok3");
     * } catch (org.json.simple.parser.ParseException ex) {
     * Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
     * } catch (SocketTimeoutException e) {
     * e.printStackTrace();
     * } catch (MalformedURLException e) {
     * e.printStackTrace();
     * }
     * return null;
     * }
     **/

    private String parseCastJSON(JSONObject jsonObject) {
        String name = jsonObject.get("name").toString();
        return name;
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
        //System.out.println("so far ok1");

        // Parse received movies
        JSONParser parser = new JSONParser();
        JSONObject movieData;
        try {
            movieData = (JSONObject) parser.parse(json);
            //if (index == 0) {
            JSONArray movies = (JSONArray) movieData.get("results");
            ArrayList<MovieInfoObject> parsedMovies = new ArrayList(10);

            for (int i = 0; i < movies.size(); i++) {
                parsedMovies.add(parseMovieJSON((JSONObject) movies.get(i)));
            }
            //System.out.println("so far ok2");

            // Notify Listener
            mListener.requestCompleted(parsedMovies);
            //} else {

            //}
            System.out.println("so far ok3");
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fetchedMoviesJSONWithGenre(String json, ArrayList<Integer> genreID) {
        // If null string returned then there was a lack of internet connection
        if (json == null) {
            mListener.requestFailed();
            return;
        }

        // Parse received movies
        JSONParser parser = new JSONParser();
        JSONObject movieData;
        try {
            movieData = (JSONObject) parser.parse(json);

            JSONArray movies = (JSONArray) movieData.get("results");
            ArrayList<MovieInfoObject> parsedMovies = new ArrayList(20);

            for (int i = 0; i < movies.size(); i++) {
                MovieInfoObject newMovie = parseMovieJSON((JSONObject) movies.get(i));
                long[] movieGenre = newMovie.getGenreIDs();
                for (Integer id : genreID){
                    for (long movieGenreID : movieGenre){
                        if (movieGenreID == id){
                            parsedMovies.add(newMovie);
                            break;
                        }
                    }
                }
            }

            // Notify Listener
            mListener.requestCompleted(parsedMovies);
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

    private void fetchJSONData(String URLString) {
        Thread fetchThread = null;
        try {
            fetchThread = new Thread(new MovieInfoFetcher(new URL(URLString), this));
            fetchThread.start();
            //System.out.println("bef MovieInfoFetcher");
        } catch (MalformedURLException ex) {
            Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fetchJSONDataWithGenre(String URLString, ArrayList<Integer> genreID) {
        Thread fetchThread = null;
        try {
            fetchThread = new Thread(new MovieInfoFetcherWithGenre(new URL(URLString), this, genreID));
            fetchThread.start();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Parses the given JSON string for a movie into a MovieInfo object
    /**
     * Parses the given JSON string for a movie into a MovieInfo object.
     */
    private MovieInfoObject parseMovieJSON(JSONObject movieData) {
        long ID = (long) movieData.get(kMOVIE_ID);
        String title = "";
        if (index == 0) {
            title = (String) movieData.get(kMOVIE_TITLE);
        } else {
            title = (String) movieData.get(kTV_TITLE);
        }
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

        MovieInfoObject movieInfo = new MovieInfoObject(ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);

        // If the base url was fetched and loaded, set the root path and poster size
        if (mImageBaseURL != null) {
            movieInfo.setPosterRootPath(mImageBaseURL, mPosterSizes[mPosterSizes.length - 3], mBackdropSizes[mBackdropSizes.length - 1]);
        }

        return movieInfo;
    }

    // Parses the given string into a date. Returns null if the parse failed
    private Date parseDateString(String dateString) {
        DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        Date date = null;

        try {
            date = formatter.parse(dateString);
        } catch (ParseException ex) {
            date = null;
        }

        return date;
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
    private void checkIfConfigNeeded() {
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
    private void reCacheConfigData() {
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
    public static String[] getGenreStrings(MovieInfoObject movie) {
        try {
            String jsonResult = "";
            if (index == 0) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + GENRE_LIST_MOVIE_URL + API_KEY));
            } else {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + GENRE_LIST_TV_URL + API_KEY));


            }
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray genres = (JSONArray) jsonData.get("genres");

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
}
