package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.logic.parsers.TimeParser;
import entertainment.pro.ui.MovieHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.SortProfile;

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


public class RetrieveRequest implements InfoFetcher, InfoFetcherWithPreference {
    private RequestListener mListener;
    private ArrayList<MovieInfoObject> p_Movies;
    MovieHandler movieHandler =  new MovieHandler();
    SortProfile sortProfile = movieHandler.getSortProfile();
    static int index = 0;

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

    private String string;

    private static final String kMOVIE_CAST = "cast_id";
    private static final String kADULT = "adult";

    public static String getCastStrings(MovieInfoObject mMovie) {

        try {
            String jsonResult = "";
            int getMovieType = mMovie.getMovieType();
            if (getMovieType == 0) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "movie/" + mMovie.getID() + "/credits?api_key=" +
                    RetrieveRequest.API_KEY));
            } else if (getMovieType == 1) {
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

    public static String getCertStrings(MovieInfoObject mMovie) {
        try {
            String jsonResult = "";
            int getMovieType = mMovie.getMovieType();
            if (getMovieType == 0) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "movie/" + mMovie.getID() + "/release_dates?api_key=" +
                    RetrieveRequest.API_KEY));
                index = 0;
            } else if (getMovieType == 1) {
                jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "tv/" + mMovie.getID() + "/content_ratings?api_key=" +
                    RetrieveRequest.API_KEY + "&language=en-US"));
                index = 1;
            }
           // System.out.println(MAIN_URL + "movie/" + mMovie.getID() + "/release_dates?api_key=" +
             //   RetrieveRequest.API_KEY);
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(jsonResult);
            JSONArray casts = (JSONArray) jsonData.get("results");
            //System.out.println("yes1");
            String certStrings = "";
            String ret = "N/A";
            if (index == 0) {
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
        CURRENT_TV,
        POPULAR_TV,
        UPCOMING_TV,
        TRENDING_TV,
        SEARCH_PEOPLE,
        POP_CAST,
        NEW_TV
    }

    public RetrieveRequest(RequestListener listener) {
        mListener = listener;
        // Check if config is needed
        checkIfConfigNeeded();
    }

    public ArrayList<MovieInfoObject> getParsedMovies() {
        return p_Movies;
    }

    public void beginMovieRequest(RetrieveRequest.MoviesRequestType type, boolean isAdult) {
        String requestURL = RetrieveRequest.MAIN_URL;
        switch (type) {
            case CURRENT_MOVIES:
                requestURL += RetrieveRequest.CURRENT_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG&include_adult=" + isAdult;
                index = 0;
                break;
            case POPULAR_MOVIES:
                requestURL += RetrieveRequest.POPULAR_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG&include_adult=" + isAdult;
                index = 0;
                break;
            case UPCOMING_MOVIES:
                requestURL += RetrieveRequest.UPCOMING_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG&include_adult=" + isAdult;
                index = 0;
                break;
            case TRENDING_MOVIES:
                requestURL += RetrieveRequest.TRENDING_MOVIE_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&region=SG&include_adult=" + isAdult;
                index = 0;
                break;
            case CURRENT_TV:
                requestURL += RetrieveRequest.CURRENT_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&include_adult=" + isAdult;
                index = 1;
                break;
            case POPULAR_TV:
                requestURL += RetrieveRequest.POPULAR_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&include_adult=" + isAdult;
                index = 1;
                break;
            case TRENDING_TV:
                requestURL += RetrieveRequest.TRENDING_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&include_adult=" + isAdult;
                index = 1;
                break;
            case NEW_TV:
                requestURL += RetrieveRequest.NEW_TV_URL + RetrieveRequest.API_KEY +
                    "&language=en-US&page=1&include_adult=" + isAdult;
                index = 1;
                break;
            default:
                requestURL = null;
        }
        System.out.println(requestURL);
        fetchJSONData(requestURL);
    }

    private String getCertAdvice(int age) {
        String ret = "";
        if (age <= 10) {
            ret = "G";
        } else if (age <= 13) {
            ret = "PG";
        } else if (age <= 17) {
            ret = "PG-13";
        } else {
            ret = "R";
        }
        System.out.println(ret);
        return ret;
    }

    public void beginMovieSearchRequest(String movieTitle, boolean adult) {
        String url = "";
        try {
            url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8") + "&include_adult=";
            url += adult;
            fetchJSONData(url);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        index = 0;
       // System.out.println("this is newest:" + url);
        fetchJSONData(url);
    }


    public String beginAddRequest(String movieTitle) {
        try {
            String url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8");
            URLRetriever retrieve = new URLRetriever();
            String json = retrieve.readURLAsString(new URL(url));
            fetchedMoviesJSON(json);
            return p_Movies.get(0).getTitle();
        } catch (UnsupportedEncodingException | MalformedURLException | SocketTimeoutException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void beginMovieSearchRequestWithPreference(String movieTitle, ArrayList<Integer> genrePreference, ArrayList<Integer> genreRestriction, boolean adult) {
        try {
            String url = MAIN_URL + MOVIE_SEARCH_URL + API_KEY + "&query=" + URLEncoder.encode(movieTitle, "UTF-8") + "&include_adult=";
            url += adult;
            fetchJSONDataWithPreference(url, genrePreference, genreRestriction);

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<MovieInfoObject> beginSearchGenre (String genre, boolean adult) {
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
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return p_Movies;
    }

    private String parseCastJSON(JSONObject jsonObject) {
        String name = jsonObject.get("name").toString();
        return name;
    }

    public void getOfflineSearch(String payload, ArrayList<Long> genres, boolean adult) {
        ArrayList<MovieInfoObject> parsedMovies = new ArrayList(10);
        int end = 0;
        for (int i = 1; i <= 200; i += 1) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = null;
            try {
                try {
                    jsonArray = (JSONArray) parser.parse(new FileReader("data/movieDatabase/" + i + ".json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //String file = "data/moviedatabase/" + i + ".json";
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            if (payload.equals("")) {
                System.out.println("this");
            }
            if (genres.size() == 0) {
                System.out.println("this");
            }
            if ((payload.equals("")) && (genres.size() == 0) && (adult == true)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    int movieType = 0;
                    long ID = (long) jsonObject.get("id");
                    String title = (String) jsonObject.get("title");
                    String release = (String) jsonObject.get("release_date");
                    String summary = (String) jsonObject.get("overview");
                    double rating = (double) jsonObject.get("vote_average");
                    //JSONArray genreIDs = (JSONArray) jsonObject.get("genres");

                    JSONArray genreIDsJsonArray = (JSONArray) jsonObject.get("genres");
                    long[] genreIDs = new long[genreIDsJsonArray.size()];
                    for (int a = 0; a < genreIDs.length; a++) {
                        genreIDs[a] = (long) genreIDsJsonArray.get(a);
                    }


                    String posterPath = "data/cross.png";
                    String backdropPath = "data/cross.png";
                    Date releaseDate = TimeParser.convertToDate(release);

                    MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                    parsedMovies.add(movieInfo);
                    if (j == 9) {
                        end = 1;
                        break;
                    }
                }
            } else if ((payload.equals("")) && (genres.size() == 0) && (adult == false)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    if (jsonObject.get("adult").toString().equals("true")) {
                        continue;
                    } else {
                        int movieType = 0;
                        long ID = (long) jsonObject.get("id");
                        String title = (String) jsonObject.get("title");
                        Date releaseDate = (Date) jsonObject.get("release_date");
                        String summary = (String) jsonObject.get("overview");
                        double rating = (double) jsonObject.get("vote_average");
                        long[] genreIDs = (long[]) jsonObject.get("genres");
                        String posterPath = "data/cross.png";
                        String backdropPath = "data/cross.png";

                        MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                        parsedMovies.add(movieInfo);
                        if (parsedMovies.size() == 10) {
                            end = 1;
                            break;
                        }
                    }
                }
            } else if ((payload.equals("")) && (genres.size() != 0) && (adult == false)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    if (jsonObject.get("adult").toString().equals("true")) {
                        continue;
                    }
                    Set<Long> hashMap = new HashSet<Long>();
                    hashMap = (Set<Long>) jsonObject.get("genres");
                    for (int k = 0; k < genres.size(); k += 1) {
                        if (genres.get(k).equals(hashMap)) {
                            int movieType = 0;
                            long ID = (long) jsonObject.get("id");
                            String title = (String) jsonObject.get("title");
                            Date releaseDate = (Date) jsonObject.get("release_date");
                            String summary = (String) jsonObject.get("overview");
                            double rating = (double) jsonObject.get("vote_average");
                            long[] genreIDs = (long[]) jsonObject.get("genres");
                            String posterPath = "data/cross.png";
                            String backdropPath = "data/cross.png";
                            MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                            parsedMovies.add(movieInfo);
                            break;
                        }
                    }
                    if (parsedMovies.size() >= 10) {
                        end = 1;
                        break;
                    }
                }
            } else if ((payload.equals("")) && (genres.size() != 0) && (adult == true)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    Set<Long> hashMap = new HashSet<Long>();
                    hashMap = (Set<Long>) jsonObject.get("genres");
                    for (int k = 0; k < genres.size(); k += 1) {
                        if (genres.get(k).equals(hashMap)) {
                            int movieType = 0;
                            long ID = (long) jsonObject.get("id");
                            String title = (String) jsonObject.get("title");
                            Date releaseDate = (Date) jsonObject.get("release_date");
                            String summary = (String) jsonObject.get("overview");
                            double rating = (double) jsonObject.get("vote_average");
                            long[] genreIDs = (long[]) jsonObject.get("genres");
                            String posterPath = "data/cross.png";
                            String backdropPath = "data/cross.png";
                            MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                            parsedMovies.add(movieInfo);
                            break;
                        }
                    }
                    if (parsedMovies.size() >= 10) {
                        end = 1;
                        break;
                    }
                }
            } else if (!(payload.equals("")) && (genres.size() == 0) && (adult == false)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    if (jsonObject.get("adult").toString().equals("true")) {
                        continue;
                    }
                    if (jsonObject.get("title").toString().contains(payload)) {
                        int movieType = 0;
                        long ID = (long) jsonObject.get("id");
                        String title = (String) jsonObject.get("title");
                        Date releaseDate = (Date) jsonObject.get("release_date");
                        String summary = (String) jsonObject.get("overview");
                        double rating = (double) jsonObject.get("vote_average");
                        long[] genreIDs = (long[]) jsonObject.get("genres");
                        String posterPath = "data/cross.png";
                        String backdropPath = "data/cross.png";
                        MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                        parsedMovies.add(movieInfo);
                    }
                    if (parsedMovies.size() >= 10) {
                        end = 1;
                        break;
                    }
                }
            } else if (!(payload.equals("")) && (genres.size() == 0) && (adult == true)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    if (jsonObject.get("title").toString().contains(payload)) {
                        int movieType = 0;
                        long ID = (long) jsonObject.get("id");
                        String title = (String) jsonObject.get("title");
                        Date releaseDate = (Date) jsonObject.get("release_date");
                        String summary = (String) jsonObject.get("overview");
                        double rating = (double) jsonObject.get("vote_average");
                        long[] genreIDs = (long[]) jsonObject.get("genres");
                        String posterPath = "data/cross.png";
                        String backdropPath = "data/cross.png";
                        MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                        parsedMovies.add(movieInfo);
                    }
                }
                if (parsedMovies.size() >= 10) {
                    end = 1;
                    break;
                }
            } else if (!(payload.equals("")) && (genres.size() != 0) && (adult == false)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    if (jsonObject.get("adult").toString().equals("true")) {
                        continue;
                    }
                    Set<Long> hashMap = new HashSet<Long>();
                    hashMap = (Set<Long>) jsonObject.get("genres");
                    for (int k = 0; k < genres.size(); k += 1) {
                        if (genres.get(k).equals(hashMap)) {
                            if (jsonObject.get("title").toString().contains(payload)) {
                                int movieType = 0;
                                long ID = (long) jsonObject.get("id");
                                String title = (String) jsonObject.get("title");
                                Date releaseDate = (Date) jsonObject.get("release_date");
                                String summary = (String) jsonObject.get("overview");
                                double rating = (double) jsonObject.get("vote_average");
                                long[] genreIDs = (long[]) jsonObject.get("genres");
                                String posterPath = "data/cross.png";
                                String backdropPath = "data/cross.png";
                                MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                                parsedMovies.add(movieInfo);
                                break;
                            }
                        }
                        if (parsedMovies.size() >= 10) {
                            end = 1;
                            break;
                        }
                    }
                }
            } else if (!(payload.equals("")) && (genres.size() != 0) && (adult == true)) {
                for (int j = 0; j < jsonArray.size(); j += 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get((j));
                    Set<Long> hashMap = new HashSet<Long>();
                    hashMap = (Set<Long>) jsonObject.get("genres");
                    for (int k = 0; k < genres.size(); k += 1) {
                        if (genres.get(k).equals(hashMap)) {
                            if (jsonObject.get("title").toString().contains(payload)) {
                                int movieType = 0;
                                long ID = (long) jsonObject.get("id");
                                String title = (String) jsonObject.get("title");
                                Date releaseDate = (Date) jsonObject.get("release_date");
                                String summary = (String) jsonObject.get("overview");
                                double rating = (double) jsonObject.get("vote_average");
                                long[] genreIDs = (long[]) jsonObject.get("genres");
                                String posterPath = "data/cross.png";
                                String backdropPath = "data/cross.png";
                                MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);
                                parsedMovies.add(movieInfo);
                                break;
                            }
                        }
                    }
                    if (parsedMovies.size() >= 10) {
                        end = 1;
                        break;
                    }
                }
            }
            if (end == 1) {
                break;
            }
        }
        mListener.requestCompleted(parsedMovies);
        p_Movies = parsedMovies;
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
            JSONArray movies;
            if (index != 2){
                movies = (JSONArray) movieData.get("results");
            } else {
                movies = (JSONArray) movieData.get("cast");

            }
            ArrayList<MovieInfoObject> parsedMovies = new ArrayList(20);


            for (int i = 0; i < movies.size(); i++) {
                parsedMovies.add(parseMovieJSON((JSONObject) movies.get(i)));
            }
         /**   if (sortProfile.getAlphaOrder().equals("Y")) {
                Collections.sort(parsedMovies, new Comparator<MovieInfoObject>() {
                    public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                        return v1.getTitle().compareTo(v2.getTitle());
                    }
                });
            } else if (sortProfile.getLatestDatesOrder().equals("Y")) {
                Collections.sort(parsedMovies, new Comparator<MovieInfoObject>() {
                    public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                        return v2.getReleaseDate().compareTo(v1.getReleaseDate());
                    }
                });
            } else if (sortProfile.getHighestRatingOrder().equals("Y")) {
                Collections.sort(parsedMovies, new Comparator<MovieInfoObject>() {
                    public int compare(MovieInfoObject v1, MovieInfoObject v2) {
                        return Double.compare(v2.getRating(), v1.getRating());
                    }
                });
            }
**/
            mListener.requestCompleted(parsedMovies);
            p_Movies = parsedMovies;
            //} else {

            //}
           // System.out.println("so far ok3");
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(RetrieveRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fetchedMoviesJSONWithPreference(String json, ArrayList<Integer> genrePreference,  ArrayList<Integer> genreRestriction) {
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

            int count = 0;

            MovieResultFilter filter = new MovieResultFilter(genrePreference, genreRestriction);
            for (int i = 0; i < movies.size(); i++) {
                MovieInfoObject newMovie = parseMovieJSON((JSONObject) movies.get(i));
                if (!genrePreference.isEmpty() && genreRestriction.isEmpty()) {
                    //pref not empty, restriction empty"
                    if (filter.isFitGenrePreference(newMovie)) {
                        parsedMovies.add(newMovie);
                    }
                } else if (!genrePreference.isEmpty()) {
                    //pref not empty, restriction not empty
                    if (filter.isFitGenrePreference(newMovie) && filter.isFitGenreRestriction(newMovie)) {
                        parsedMovies.add(newMovie);
                    }
                } else if (!genreRestriction.isEmpty()) {
                    //pref empty, restriction not empty"
                    if (filter.isFitGenreRestriction(newMovie)) {
                        parsedMovies.add(newMovie);
                    }
                } else {
                    //pref empty, restriction empty"
                    parsedMovies.add(newMovie);
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

    private void fetchJSONDataWithPreference(String URLString, ArrayList<Integer> genrePreference, ArrayList<Integer> genreRestriction) {
        Thread fetchThread = null;
        try {
            fetchThread = new Thread(new MovieInfoFetcherWithPreference(new URL(URLString), this, genrePreference, genreRestriction));
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
        //long ID = (long) movieData.get(kMOVIE_ID);
        //boolean adult = (boolean) movieData.get(kADULT);
        String title = "";
        int movieType = -1;
        if (index == 0) {
            title = (String) movieData.get(kMOVIE_TITLE);
            movieType = 0;
        } else if (index == 1) {
            title = (String) movieData.get(kTV_TITLE);
            movieType = 1;
        } else if (index == 2) {
            String identity = (String)movieData.get("media_type");
            if (identity.equals("movie")) {
                title = (String) movieData.get(kMOVIE_TITLE);
                movieType = 0;
            } else {
                title = (String) movieData.get(kTV_TITLE);
                movieType = 1;
            }
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

        MovieInfoObject movieInfo = new MovieInfoObject(movieType, ID, title, releaseDate, summary, rating, genreIDs, posterPath, backdropPath);

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


    public void getAllTheMovie() throws org.json.simple.parser.ParseException {
        String jsonResult = "";
        int j = 501;
        int b = 0;
        String filename = "";
        for (int i = 250000; i < 300000; i += 2) {
            int de = i / 500;
            de += 1;
            if (de > j) {
                j += 1;
                filename = "data/movieDatabase/" + j + ".json";
                File oldfile = new File(filename);
                b = 1;
            } else {
                filename = "data/movieDatabase/" + j + ".json";
                b = 0;
            }
            System.out.println(filename);
            try {
                jsonResult = URLRetriever.readURLAsString(new URL("https://api.themoviedb.org/3/movie/" +
                        i + "?api_key=" + RetrieveRequest.API_KEY + "&language=en-US"));
                // jsonResult = URLRetriever.readURLAsString(new URL("https://api.themoviedb.org/3/discover/movie?api_key=" +
                //       RetrieveRequest.API_KEY + "&sort_by=popularity.desc" + "&with_genres=35&page=" + i));

            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            }

            JSONParser parser1 = new JSONParser();
            JSONArray jsonObject1 = new JSONArray();
            if (b == 0) {
                try {
                    jsonObject1 = (JSONArray) parser1.parse(new FileReader(filename));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
            }

            //=================
            try {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject2 = (JSONObject) parser.parse(jsonResult);
                JSONArray jsonArray2 = new JSONArray();

                //for (int a = 0; a < jsonObject2.size(); a += 1) {
                //  if ()

                //}
                jsonObject2.remove("backdrop_path");
                jsonObject2.remove("belongs_to_collection");
                jsonObject2.remove("budget");
                jsonObject2.remove("homepage");
                //jsonObject2.remove("id");
                jsonObject2.remove("imdb_id");
                jsonObject2.remove("original_language");
                jsonObject2.remove("original_title");
                jsonObject2.remove("popularity");
                jsonObject2.remove("poster_path");
                jsonObject2.remove("production_companies");
                jsonObject2.remove("production_countries");
                jsonObject2.remove("revenue");
                jsonObject2.remove("spoken_languages");
                jsonObject2.remove("tagline");
                jsonObject2.remove("video");
                jsonObject2.remove("vote_count");
                jsonObject2.remove("status");

                JSONArray xtra = new JSONArray();
                JSONArray get = (JSONArray) jsonObject2.get("genres");
                for (int d = 0; d < get.size(); d += 1) {
                    JSONObject jsonObject = (JSONObject) get.get(d);
                    long num = (long) jsonObject.get("id");
                    xtra.add(num);
                }
                jsonObject2.remove("genres");
                jsonObject2.put("genres", xtra);


                /**JSONObject temp = (JSONObject)jsonObject2.get("adult");
                 JSONObject temp2 = (JSONObject)jsonObject2.get("genres");
                 JSONObject temp3 = (JSONObject)jsonObject2.get("overview");
                 JSONObject temp4 = (JSONObject)jsonObject2.get("release_date");
                 JSONObject temp5 = (JSONObject)jsonObject2.get("runtime");
                 JSONObject temp6 = (JSONObject)jsonObject2.get("title");
                 JSONObject temp7 = (JSONObject)jsonObject2.get("vote_average");
                 //JSONArray jsonArray2 = (JSONArray)jsonObject2.get("results");


                 jsonArray2.add(temp);
                 jsonArray2.add(temp2);
                 jsonArray2.add(temp3);
                 jsonArray2.add(temp4);
                 jsonArray2.add(temp5);
                 jsonArray2.add(temp6);
                 jsonArray2.add(temp7);
                 **/
                //JSONArray addEverything = new JSONArray();
                //ad.add(jsonObject1);
                try {
                    jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "movie/" + i + "/credits?api_key=" +
                            RetrieveRequest.API_KEY));
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                JSONParser newParser = new JSONParser();
                JSONObject jsonData = (JSONObject) newParser.parse(jsonResult);
                JSONArray casts = (JSONArray) jsonData.get("cast");
                JSONArray firstFiveStudentArray = new JSONArray();
                int num = casts.size();
                int index = 5;
                if (num < 5) {
                    index = num;
                }
                for (int c = 0; c < index; c++) {
                    JSONObject studentObj = (JSONObject) casts.get(c);
                    String name = studentObj.get("name").toString();

                    /**studentObj.remove("cast_id");
                     studentObj.remove("character");
                     studentObj.remove("gender");
                     studentObj.remove("credit_id");
                     studentObj.remove("profile_path");
                     studentObj.remove("id");
                     studentObj.remove("order");
                     **/
                    if (studentObj != null) {
                        firstFiveStudentArray.add(name);
                    }
                }
                //===========
                String ret = "N/A";
                try {
                    String jsonResult1 = "";
                    jsonResult = URLRetriever.readURLAsString(new URL(MAIN_URL + "movie/" + i + "/release_dates?api_key=" +
                            RetrieveRequest.API_KEY));
                    JSONParser parser11 = new JSONParser();
                    JSONObject jsonData1 = (JSONObject) parser11.parse(jsonResult);
                    JSONArray casts1 = (JSONArray) jsonData1.get("results");
                    //System.out.println("yes1");
                    String certStrings = "";
                    //String ret = "N/A";
                    for (int e = 0; e < casts1.size(); e += 1) {
                        JSONObject castPair = (JSONObject) casts1.get(e);
                        if (castPair.get("iso_3166_1").equals("GB")) {
                            JSONArray jsonObjectify = (JSONArray) castPair.get("release_dates");
                            JSONObject tem = (JSONObject) jsonObjectify.get(0);
                            ret = tem.get("certification").toString();
                        }
                    }
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (org.json.simple.parser.ParseException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }




                //============
                jsonObject2.put("cert", ret);
                jsonObject2.put("cast", firstFiveStudentArray);
                jsonObject1.add(jsonObject2);

                File fileToSaveJson = new File(filename);
                //====================

                //jsonArray2.addAll(casts)

                //================
                byte[] jsonArray = jsonObject1.toString().getBytes();
                BufferedOutputStream bos;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(fileToSaveJson));
                    bos.write(jsonArray);
                    bos.flush();
                    bos.close();

                } catch (FileNotFoundException e4) {
                    // TODO Auto-generated catch block
                    e4.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    jsonArray = null;
                    parser = null;
                    System.gc();
                }
                System.out.println(i);
            } catch (NullPointerException e) {
                i += 1;
            }
        }

    }






    public void create() {
        for (int i = 200; i <= 1000; i += 1) {
            File newFile = new File("data/movieDatabase/" + i + ".json");
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
