package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.storage.utils.OfflineSearchStorage;
import entertainment.pro.ui.MovieHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import entertainment.pro.model.MovieInfoObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for fetching results from The MovieDB API and parsing them into objects.
 */
public class RetrieveRequest implements InfoFetcher {
    private static final String DEFAULT_IMAGE_FILENAME = "/images/cross.png";
    private static final String PRINT_SUITABLE_FOR = "Suitable for";
    private static final String UNAVAILABLE_INFO = "N/A";
    private RequestListener requestListener;
    private ArrayList<MovieInfoObject> finalSearchResults = new ArrayList<>();
    private SearchProfile searchProfile;
    private static RetrieveRequest.MoviesRequestType getType;
    private boolean isOffline = false;
    private String messageToBePrinted = "";

    // API Usage constants
    private static final String MAIN_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "5d467eb39777ed643edb8312fb71c157";

    // General API keywords used for both movies and TV shows
    private static final String REGION_SPECIFIED_IN_API = "&region=SG";
    private static final String TO_SPECIFY_QUERY = "&query=";
    private static final String ADD_ADULT_OPTION = "&include_adult=";
    private static final String TO_SPECIFY_MOVIES = "movie/";
    private static final String TO_SPECIFY_TV_SHOWS = "tv/";
    private static final String TO_SPECIFY_ISO = "iso_3166_1";
    private static final String TO_SPECIFY_RATING = "rating";
    private static final String TO_SPECIFY_UK = "GB";
    private static final String TO_SPECIFY_US = "US";
    private static final String TO_SPECIFY_CERTIFICATION = "certification";
    private static final String TO_SPECIFY_RELEASE_DATES = "release_dates";


    // General Data Request URL's
    private static final String RELEASE_DATES_URL = "/release_dates?api_key=";
    private static final String CREDITS_URL = "/credits?api_key=";
    private static final String RATINGS_URL = "/content_ratings?api_key=";

    // Data Request URL's for movies
    private static final String CURRENT_MOVIE_URL = "movie/now_playing?api_key=";
    private static final String POPULAR_MOVIE_URL = "movie/popular?api_key=";
    private static final String UPCOMING_MOVIE_URL = "movie/upcoming?api_key=";
    private static final String MOVIE_SEARCH_URL = "search/movie?api_key=";
    private static final String TRENDING_MOVIE_URL = "trending/movie/day?api_key=";
    private static final String TOP_RATED_MOVIE_URL = "movie/top_rated?api_key=";
    private static final String GENRE_LIST_MOVIE_URL = "genre/movie/list?api_key=";

    // Data Request URL's for TV shows
    private static final String CURRENT_TV_URL = "tv/on_the_air?api_key=";
    private static final String POPULAR_TV_URL = "tv/popular?api_key=";
    //private static final String NEW_TV_URL = "tv/latest?api_key=";
    private static final String TV_SEARCH_URL = "search/tv?api_key=";
    private static final String TRENDING_TV_URL = "trending/tv/day?api_key=";
    private static final String TOP_RATED_TV_URL = "tv/top_rated?api_key=";
    private static final String GENRE_LIST_TV_URL = "genre/tv/list?api_key=";

    // Data Keys for both movie and TV shows
    private static final String MOVIE_TITLE = "title";
    private static final String TV_TITLE = "original_name";
    private static final String RELEASE_DATE = "release_date";
    private static final String DATA_ID = "id";
    private static final String GENRES = "genre_ids";
    private static final String SUMMARY = "overview";
    private static final String RATING = "vote_average";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String POSTER_PATH = "poster_path";
    private static final String KEYWORD_FOR_SEARCH_REQUESTS = "results";
    private static final String KEYWORD_FOR_CAST_REQUESTS = "cast";
    private static final String KEYWORD_FOR_NAME = "name";

    // Config URL
    private final static String CONFIG_URL = MAIN_URL + "configuration?api_key=" + API_KEY;

    // Config constants
    private static final int DAYS_TILL_RECACHE = 10;
    private static final String CONFIG_FILENAME = "config.dat";

    // Config Keys
    private static final String CONFIG_BASE_URL = "base_url";
    private static final String CONFIG_SECURE_BASE_URL = "secure_base_url";
    private static final String CONFIG_BACKDROP_SIZES = "backdrop_sizes";
    private static final String CONFIG_POSTER_SIZES = "poster_sizes";

    // Config values
    private static Date lastConfigCachedDate;
    private static String imageBaseURL;
    private static String imageSecureBaseURL;
    private static String[] resultsPosterSizes;
    private static String[] resultsBackdropSizes;
    private boolean configWasRead;


    /**
     * Enumerates different types of requests that can be done with MovieDB API.
     */
    public enum MoviesRequestType {
        CURRENT_MOVIES,
        POPULAR_MOVIES,
        UPCOMING_MOVIES,
        TRENDING_MOVIES,
        TOP_RATED_MOVIES,
        SEARCH_MOVIES,
        CURRENT_TV,
        POPULAR_TV,
        TRENDING_TV,
        TOP_RATED_TV,
        SEARCH_TV,
    }

    /**
     * Responsible for setting SearchProfile.
     * Which will be in turn used to filter search results according to user's prefererences.
     *
     * @param searchProfile Object that contains all the users' preferences for a particular search.
     */
    public void setSearchProfile(SearchProfile searchProfile) {
        this.searchProfile = searchProfile;
    }

    /**
     * Responsible for setting up interface that listens to completed fetch requests from the MovieDB API.
     * Also, checks if config is needed.
     *
     * @param listener Interface that listens to completed fetch requests from the MovieDB API.
     * @throws Exceptions
     */
    public RetrieveRequest(RequestListener listener) throws Exceptions {
        requestListener = listener;
        // Check if configuration is needed
        checkIfConfigNeeded();
    }

    /**
     * Responsible for begining appropriate data request fetch based on type on another thread.
     * Listener will be called once the data has been fetched and parsed into a object.
     *
     * @param type The request type to fetch the appropriate data from the API.
     * @throws Exceptions to detect UnsupportedEncodingException
     */
    public void beginSearchRequest(RetrieveRequest.MoviesRequestType type) throws Exceptions {
        getType = type;
        boolean isAdult;
        try {
            // check if search is for movies or TV shows
            isAdult = searchProfile.isAdult();
        } catch (NullPointerException e) {
            isAdult = false;
        }
        String requestURL = RetrieveRequest.MAIN_URL;
        switch (type) {
            // to fetch data for currently showing movies
            case CURRENT_MOVIES:
                requestURL += RetrieveRequest.CURRENT_MOVIE_URL + RetrieveRequest.API_KEY +
                        REGION_SPECIFIED_IN_API;
                messageToBePrinted = PromptMessages.VIEW_CURRENT_MOVIES_SUCCESS;
                break;
            // to fetch data for popular movies
            case POPULAR_MOVIES:
                requestURL += RetrieveRequest.POPULAR_MOVIE_URL + API_KEY +
                        REGION_SPECIFIED_IN_API;
                messageToBePrinted = PromptMessages.VIEW_POPULAR_MOVIES_SUCCESS;
                break;
            // to fetch data for upcoming movies
            case UPCOMING_MOVIES:
                requestURL += RetrieveRequest.UPCOMING_MOVIE_URL + API_KEY +
                        REGION_SPECIFIED_IN_API;
                messageToBePrinted = PromptMessages.VIEW_UPCOMING_MOVIES_SUCCESS;
                break;
            // to fetch data for trending movies
            case TRENDING_MOVIES:
                requestURL += RetrieveRequest.TRENDING_MOVIE_URL + API_KEY +
                        REGION_SPECIFIED_IN_API;
                messageToBePrinted = PromptMessages.VIEW_TRENDING_MOVIES_SUCCESS;
                break;
            // to fetch data for top-rated movies
            case TOP_RATED_MOVIES:
                requestURL += RetrieveRequest.TOP_RATED_MOVIE_URL + API_KEY;
                messageToBePrinted = PromptMessages.VIEW_TOP_RATED_MOVIES_SUCCESS;
                break;
            // to fetch data for currently playing TV shows on the air
            case CURRENT_TV:
                requestURL += RetrieveRequest.CURRENT_TV_URL + API_KEY;
                messageToBePrinted = PromptMessages.VIEW_CURRENT_TV_SUCCESS;
                break;
            // to fetch data for popular TV shows
            case POPULAR_TV:
                requestURL += RetrieveRequest.POPULAR_TV_URL + API_KEY;
                messageToBePrinted = PromptMessages.VIEW_POPULAR_TV_SUCCESS;
                break;
            // to fetch data for trending TV shows
            case TRENDING_TV:
                requestURL += RetrieveRequest.TRENDING_TV_URL + API_KEY;
                messageToBePrinted = PromptMessages.VIEW_TRENDING_TV_SUCCESS;
                break;
            // to fetch data for top-rated TV shows
            case TOP_RATED_TV:
                requestURL += RetrieveRequest.TOP_RATED_TV_URL + API_KEY;
                messageToBePrinted = PromptMessages.VIEW_TOP_RATED_TV_SUCCESS;
                break;
            // to fetch data for movies that match the keyword entered by user
            case SEARCH_MOVIES:
                try {
                    requestURL += RetrieveRequest.MOVIE_SEARCH_URL + API_KEY + TO_SPECIFY_QUERY +
                            URLEncoder.encode(searchProfile.getName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new Exceptions(PromptMessages.API_FAIL_GENERAL);
                }
                messageToBePrinted = PromptMessages.VIEW_SEARCH_MOVIES_SUCCESS;
                break;
            case SEARCH_TV:
                try {
                    requestURL += RetrieveRequest.TV_SEARCH_URL + API_KEY + TO_SPECIFY_QUERY +
                            URLEncoder.encode(searchProfile.getName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new Exceptions(PromptMessages.API_FAIL_GENERAL);
                }
            default:
                messageToBePrinted = PromptMessages.VIEW_SEARCH_TV_SUCCESS;
                requestURL = null;
        }
        // add adult preference to the url to fetch data approrpariately
        requestURL += ADD_ADULT_OPTION + isAdult;
        System.out.println(requestURL);
        fetchJSONData(requestURL);
    }

    /**
     * Responible and called when information about cast and certication is needed about a movie or TV show.
     * This data have to be fetched by MovieDB API.
     *
     * @param object Object that contains all the details about a movie or TV show.
     * @throws Exceptions
     */
    public void beginMoreInfoRequest(MovieInfoObject object) throws Exceptions {
        if (!isOffline) {
            String certDetail = getCertStrings(object);
            object.setCertInfo(certDetail);
            ArrayList<String> castDetail = getCastStrings(object);
            object.setCastInfo(castDetail);
        }
    }

    /**
     * Responsible for extracting and returning the cert details about a movie/TV show.
     * This function unlike beginSearchRequest does not call any listener.
     *
     * @param infoObject Object that contains details about the movie/TV show.
     * @return The cert details pertaining to the movie/TV show.
     * @throws Exceptions when detect MalformedURLException or ParserException.
     */
    public static String getCertStrings(MovieInfoObject infoObject) throws Exceptions {
        String cert = UNAVAILABLE_INFO;
        try {
            String jsonResult = "";
            String url = MAIN_URL;
            boolean isMovie = infoObject.isMovie();
            // if object is a movie
            if (isMovie) {
                url += TO_SPECIFY_MOVIES + infoObject.getId() + RELEASE_DATES_URL;
            } else {
                url += TO_SPECIFY_TV_SHOWS + infoObject.getId() + RATINGS_URL;
            }
            url += API_KEY;
            System.out.println(url);
            jsonResult = URLRetriever.readURLAsString(new URL(url));
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray casts = (JSONArray) jsonData.get(KEYWORD_FOR_SEARCH_REQUESTS);
            if (isMovie) {
                cert = getMovieCertFromJSON(casts);
            } else {
                cert = getTVCertFromJSON(casts);
            }
        } catch (MalformedURLException | org.json.simple.parser.ParseException ex) {
            ex.printStackTrace();
        }
        return cert;
    }

    /**
     * Responsible for extracting and returning the certification for a TV show from a JSONArray.
     *
     * @param certInfo JSONArray from which the certification for the TV show is extacted.
     * @return Certification for the TV show from a JSONArray.
     */
    private static String getTVCertFromJSON(JSONArray certInfo) {
        String cert = UNAVAILABLE_INFO;
        String certStrings = "";
        for (int i = 0; i < certInfo.size(); i += 1) {
            JSONObject castPair = (JSONObject) certInfo.get(i);
            if (castPair.get(TO_SPECIFY_ISO).equals(TO_SPECIFY_UK)) {
                certStrings = castPair.get(TO_SPECIFY_RATING).toString();
                cert = PRINT_SUITABLE_FOR + certStrings + " years & above";
            }
        }
        return cert;
    }

    /**
     * Responsible for extracting and returning the certification for a movie from a JSONArray.
     *
     * @param certInfo JSONArray from which the certification for the movie is extacted.
     * @return Certification for the movie from a JSONArray.
     */
    private static String getMovieCertFromJSON(JSONArray certInfo) {
        String cert = UNAVAILABLE_INFO;
        String certStrings = "";
        for (int i = 0; i < certInfo.size(); i += 1) {
            JSONObject castPair = (JSONObject) certInfo.get(i);
            if (castPair.get(TO_SPECIFY_ISO).equals(TO_SPECIFY_US)) {
                //SONArray jsonArray = castPair.get(RELEASE_DATE)
                Map certMap = (Map) certInfo.get(i);
                Iterator<Map.Entry> itr1 = certMap.entrySet().iterator();
                while (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    if (pair.getKey().equals(TO_SPECIFY_RELEASE_DATES)) {
                        certStrings = pair.getValue().toString();
                    }
                }
                System.out.println("this is:" + certStrings);
                String[] getCert = certStrings.strip().split(TO_SPECIFY_CERTIFICATION);
                if (getCert.length == 2) {
                    cert = getCert[1].substring(2, getCert[1].length() - 2);
                } else {
                    cert = getCert[getCert.length - 1].substring(2, getCert[getCert.length - 1].length() - 2);
                }
            }
        }
        return cert;
    }

    /**
     * Responsible for extracting and returning the list of cast details about a movie/TV show.
     * This function unlike beginSearchRequest does not call any listener.
     *
     * @param infoObject Object that contains details about the movie/TV show.
     * @return the list of cast pertaining to the movie/TV show.
     * @throws Exceptions when detect MalformedURLExcpetion and/or ParserException.
     */
    public static ArrayList<String> getCastStrings(MovieInfoObject infoObject) throws Exceptions {
        ArrayList<String> castInfoStrings = new ArrayList<>();
        try {
            String jsonResult = "";
            String url = MAIN_URL;
            boolean isMovie = infoObject.isMovie();
            // object is a movie
            if (isMovie) {
                url += TO_SPECIFY_MOVIES;
            } else {
                url += TO_SPECIFY_TV_SHOWS;
            }
            url += infoObject.getId() + CREDITS_URL + API_KEY;
            System.out.println(url);
            jsonResult = URLRetriever.readURLAsString(new URL(url));
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray casts = (JSONArray) jsonData.get(KEYWORD_FOR_CAST_REQUESTS);
            for (int i = 0; i < casts.size(); i += 1) {
                JSONObject castPair = (JSONObject) casts.get(i);
                castInfoStrings.add((String) castPair.get(KEYWORD_FOR_NAME));
            }
        } catch (MalformedURLException | org.json.simple.parser.ParseException ex) {
            throw new Exceptions(PromptMessages.API_FAIL_GENERAL);
        }
        return castInfoStrings;
    }


    /**
     * api request to search movies to add to the watchlist
     *
     * @param movieTitle: movie name to be added to watchlist
     * @return first movie title in the search result
     * @throws Exceptions: API request errors such as bad encoding or incorrect URL
     */
    public String beginAddRequest(String movieTitle) throws Exceptions {
        try {
            String url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8");
            URLRetriever retrieve = new URLRetriever();
            String json = retrieve.readURLAsString(new URL(url));
            fetchedJSON(json);
            return finalSearchResults.get(0).getTitle();
        } catch (UnsupportedEncodingException | MalformedURLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * api request to search movies by genre to add to the watchlist
     *
     * @param genre: genreid of the api to search
     * @param adult: parameter to determine if adult feature needs to be enabled
     * @return Array_list of movies results based on the genre
     * @throws Exceptions: API request errors such as bad encoding or incorrect URL
     */
    public ArrayList<MovieInfoObject> beginSearchGenre(String genre, boolean adult) throws Exceptions {
        try {
            String url = MAIN_URL + "discover/movie?with_genres=" + URLEncoder.encode(genre, "UTF-8") + "&api_key="
                    + API_KEY + "&language=en-US&page=1" + "&include_adult=";
            url += adult;
            URLRetriever retrieve = new URLRetriever();
            String json = retrieve.readURLAsString(new URL(url));
            fetchedJSON(json);
            //fetchJSONData(url);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return finalSearchResults;
    }


    /**
     * Responsible for retrieving data from offline storage files when there is no/weak internet connection.
     * Once data has been retrieved, calls another function to further parse the data.
     */
    @Override
    public void fetchOfflineData() {
        isOffline = true;
        JSONArray resultsJSON = new JSONArray();
        OfflineSearchStorage offlineSearchStorage = new OfflineSearchStorage();
        try {
            resultsJSON = offlineSearchStorage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseJSON(resultsJSON);
    }

    /**
     * Called after JSON data has been fetched by the fetcher.
     * Parse the data into a JSONArray.
     *
     * @param json String that contains all the data extracted by fetcher.
     */
    @Override
    public void fetchedJSON(String json) {
        isOffline = false;
        JSONObject data = new JSONObject();
        JSONParser parser = new JSONParser();
        JSONArray searchResults = new JSONArray();
        try {
            data = (JSONObject) parser.parse(json);
            searchResults = (JSONArray) data.get(KEYWORD_FOR_SEARCH_REQUESTS);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        parseJSON(searchResults);
    }

    /**
     * Responsible for filtering results according to user's preferences.
     * Also responsible for sorting results according to user's preferences.
     * @param searchResults JSONArray that contains data about all the search results.
     */
    private void parseJSON(JSONArray searchResults) {
        ArrayList<MovieInfoObject> parsedMovies = new ArrayList(20);
        int size = 0;
        //max size of results that are to be displayed set at 20
        for (int i = 0; i < searchResults.size(); i++) {
            if (size > 20) {
                break;
            }
            // add results that meet user stated preferences
            if (checkCondition((JSONObject) searchResults.get(i))) {
                parsedMovies.add(parseMovieJSON((JSONObject) searchResults.get(i)));
                size += 1;
                //System.out.println("yeesss");
            }
        }
        if (parsedMovies.isEmpty()) {
            requestListener.emptyResults();
            return;
        }
        finalSearchResults = parsedMovies;
        if (searchProfile.isSortByAlphabetical()) {
            sortByAlphaOrder();
        } else if (searchProfile.isSortByLatestRelease()) {
            sortByLatestRelease();
        } else if (searchProfile.isSortByHighestRating()) {
            sortByHighestRating();
        }
        if (isOffline) {
            // to print a message that offline data is being used
            messageToBePrinted += "\n" + PromptMessages.DATA_OBTAINED_FROM_LOCAL_FILES;
            requestListener.requestTimedOut(messageToBePrinted);
        } else {
            // to print message that data was extracted from API successfully
            messageToBePrinted += "\n" + PromptMessages.DATA_OBTAINED_FROM_API;
            requestListener.requestCompleted(messageToBePrinted);
        }
        requestListener.obtainedResultsData(finalSearchResults);
    }

    /**
     * Responsible for sorting data in alphabetical order from lowest to highest letter
     */
    private void sortByHighestRating() {
        finalSearchResults.sort(new Comparator<MovieInfoObject>() {
            public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                return Double.compare(v2.getRatingInfo(), v1.getRatingInfo());
            }
        });
    }

    private void sortByLatestRelease() {
        finalSearchResults.sort(new Comparator<MovieInfoObject>() {
            public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                return v2.getReleaseDateInfo().compareTo(v1.getReleaseDateInfo());
            }
        });
    }


    private void sortByAlphaOrder() {
        finalSearchResults.sort(new Comparator<MovieInfoObject>() {
            public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                return v1.getTitle().compareTo(v2.getTitle());
            }
        });
    }


    public static MoviesRequestType getGetType() {
        return getType;
    }


    /**
     * Called to fetch data from API for search requests.
     *
     * @param URLString The URL pertaining to the type of search request to be carried off.
     * @throws Exceptions when detect a MalformedURLException.
     */
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
     * Called to parses the given JSON string for a movie/TV into a MovieInfo object.
     *
     * @param data JSONObject containing the information about a movie/TV show.
     * @return MovieInfo object containing information about a movie/TV show.
     */
    private MovieInfoObject parseMovieJSON(JSONObject data) {
        String title = "";
        boolean isMovie = false;

        // Parse title
        //if the search request was for movies
        if (searchProfile.isMovie()) {
            title = (String) data.get(MOVIE_TITLE);
            isMovie = true;
        } else {
            title = (String) data.get(TV_TITLE);
        }

        // Parse id
        long ID = (long) data.get(DATA_ID);

        // Parse rating
        double rating = 0.0;
        try {
            rating = (double) data.get(RATING);
        } catch (ClassCastException ex) {
            // the rating was parsed with a long value, cast to double (issue in simple json library)
            Long longRating = (Long) data.get(RATING);
            rating = longRating.doubleValue();
        }
        String summary = (String) data.get(SUMMARY);

        // Parse genre id array
        JSONArray genreIDsJsonArray = (JSONArray) data.get(GENRES);
        ArrayList<Long> genreID = new ArrayList<>();
        for (int i = 0; i < genreIDsJsonArray.size(); i++) {
            genreID.add((long) genreIDsJsonArray.get(i));
        }

        // Parse date string from json
        Date releaseDate = new Date();
        String releaseDateString = "";
        if (searchProfile.isMovie()) {
            releaseDateString = (String) data.get(RELEASE_DATE);
        } else {
            releaseDateString = (String) data.get("first_air_date");
        }
        if (releaseDateString != null) {
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                releaseDate = formatter1.parse(releaseDateString);
                //System.out.println("this is " + releaseDate);
                //System.out.println(releaseDate);
            } catch (ParseException e) {
                releaseDate = null;
            }
        }

        // Get poster and backdrop paths
        String posterPath = "";
        String backdropPath = "";
        if (isOffline) {
            posterPath = DEFAULT_IMAGE_FILENAME;
            backdropPath = DEFAULT_IMAGE_FILENAME;
        } else {
            posterPath = (String) data.get(POSTER_PATH);
            backdropPath = (String) data.get(BACKDROP_PATH);
        }

        MovieInfoObject movieInfo;
        if (isOffline) {
            String cert = (String) data.get("cert");
            JSONArray jsonArray = (JSONArray) data.get("cast");
            ArrayList<String> getCast = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i += 1) {
                getCast.add((String) jsonArray.get(i));
            }
            movieInfo = new MovieInfoObject(ID, title, isMovie, releaseDate, summary,
                    posterPath, backdropPath, rating, genreID, searchProfile.isAdult(), cert, getCast);
        } else {
            movieInfo = new MovieInfoObject(ID, title, isMovie, releaseDate, summary,
                    posterPath, backdropPath, rating, genreID, searchProfile.isAdult());
        }
        // If the base url was fetched and loaded, set the root path and poster size
        if (imageBaseURL != null) {
            movieInfo.setPosterRootPath(imageBaseURL, resultsPosterSizes[resultsPosterSizes.length - 3], isOffline);
        }

        return movieInfo;
    }

    // Checks if API config data needs to be recached
    private void checkIfConfigNeeded() throws Exceptions {
        boolean configNeeded = true;

        // Get last cache date and reconfig if more than 5 days passed
        File configFile = new File(CONFIG_FILENAME);

        if (configFile.exists() && !configFile.isDirectory()) {
            readConfigData();

            // Parse date and if more than 5 days passed, a recache is required
            Date now = new Date();
            int diffInDays = (int) (now.getTime() - lastConfigCachedDate.getTime()) / (1000 * 3600 * 24);

            if (diffInDays < DAYS_TILL_RECACHE) {
                configNeeded = false;
            }
        }

        if (configNeeded || !configWasRead) {
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
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(CONFIG_FILENAME));
            lastConfigCachedDate = (Date) file.readObject();
            imageBaseURL = file.readUTF();
            imageSecureBaseURL = file.readUTF();
            resultsPosterSizes = (String[]) file.readObject();
            resultsBackdropSizes = (String[]) file.readObject();

            configWasRead = true;
            file.close();
        } catch (FileNotFoundException ex) {
            // No file found, config will be recached
            configWasRead = false;
        } catch (IOException ex) {
            // Error reading - config will be recached
            configWasRead = false;
        } catch (ClassNotFoundException ex) {
            configWasRead = false;
        }
    }

    // Writes out the config data to file
    // NOTE: Only call after all config data was recached
    private void writeConfigData() {
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(CONFIG_FILENAME));
            file.writeObject(new Date());
            file.writeUTF(imageBaseURL);
            file.writeUTF(imageSecureBaseURL);
            file.writeObject(resultsPosterSizes);
            file.writeObject(resultsBackdropSizes);
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
                    imageBaseURL = (String) imageConfigData.get(CONFIG_BASE_URL);
                    imageSecureBaseURL = (String) imageConfigData.get(CONFIG_SECURE_BASE_URL);

                    // Get the string arrays for the poster and backdrop size strings
                    JSONArray posterSizesData = (JSONArray) imageConfigData.get(CONFIG_POSTER_SIZES);
                    JSONArray backdropSizesData = (JSONArray) imageConfigData.get(CONFIG_BACKDROP_SIZES);
                    resultsPosterSizes = Arrays.copyOf(posterSizesData.toArray(), posterSizesData.toArray().length, String[].class);
                    resultsBackdropSizes = Arrays.copyOf(backdropSizesData.toArray(), backdropSizesData.toArray().length, String[].class);

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

            Set<Integer> genrePref = new HashSet<>();
            Set<Integer> genreRestric = new HashSet<>();
            String[] genreStrings = new String[movie.getGenreIdInfo().size()];
            for (int i = 0; i < movie.getGenreIdInfo().size(); i++) {
                genreStrings[i] = getGenreStringForID(movie.getGenreIdInfo().get(i), genres);
            }

            return genreStrings;
        } catch (MalformedURLException | org.json.simple.parser.ParseException ex) {
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

        //System.out.println(searchProfile.getName());
        if ((isOffline) && getType.equals(MoviesRequestType.SEARCH_MOVIES)) {
            String searchName = searchProfile.getName().toLowerCase();
            String entryInfoName = ((String) entryInfo.get("title")).toLowerCase();
          //  if (searchName.indexOf(entryInfoName) == -1) {
            if (!(searchName.equals(entryInfoName))) {
                return false;
            }
        }
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
