package entertainment.pro.commons;

/**
 * Contains and defines messages printed by the app.
 *
 */
public class PromptMessages {
    public static final String WAIT_FOR_APP_TO_PROCESS = "Please wait while the app processes your request...";

    public static final String VIEW_CURRENT_MOVIES_SUCCESS = "Data fetch is complete! Displaying list of currently showing movies...";
    public static final String VIEW_UPCOMING_MOVIES_SUCCESS = "Data fetch is complete! Displaying list of upcoming movies...";
    public static final String VIEW_TRENDING_MOVIES_SUCCESS = "Data fetch is complete! Displaying list of most trending movies...";
    public static final String VIEW_POPULAR_MOVIES_SUCCESS = "Data fetch is complete! Displaying list of most popular movies...";
    public static final String VIEW_TOP_RATED_MOVIES_SUCCESS = "Data fetch is complete! Displaying list of top-rated movies...";
    public static final String VIEW_SEARCH_MOVIES_SUCCESS = "Data fetch is complete! Displaying list of movies related to the title entered...";

    public static final String VIEW_CURRENT_TV_SUCCESS = "Data fetch is complete! Displaying list of currently showing TV shows...";
    public static final String VIEW_TRENDING_TV_SUCCESS = "Data fetch is complete! Displaying list of most trending TV shows...";
    public static final String VIEW_POPULAR_TV_SUCCESS = "ata fetch is complete! Displaying list of most popular TV shows...";
    public static final String VIEW_TOP_RATED_TV_SUCCESS = "Data fetch is complete! Displaying list of top-rated TV shows...";
    public static final String VIEW_SEARCH_TV_SUCCESS = "Data fetch is complete! Displaying list of TV shows related to the title entered...";

    public static final String INVALID_COMBI_OF_FLAGS = "I'm sorry but this is a invalid command. " +
            "You can either set your preferences to be what's saved on the app or enter new preferences particularly for this search request " +
            "but can't do both at the same time!";

    public static final String NO_RESULTS_FOUND = "I'm very sorry but could not find any results that matches your request. Try enterting a different command!";
    public static final String DATA_OBTAINED_FROM_API = "[Data displayed here was obtained from an external source]";
    public static final String DATA_OBTAINED_FROM_LOCAL_FILES = "[Data displayed here was obtained from storage files as weak/no internet connection was detected." +
            "Please take note that posters for search results are unavailable for offline search requests]";
    public static final String EMPTY_PARAM_IN_SEARCH = "Empty parameter entered in search command";
    public static final String INVALID_PARAM_IN_SEARCH = "Invalid parameter entered in search command";
    public static final String TO_VIEW_MORE_INFO = "To view more information about a movie/TV show, enter the command, " +
            "'view entry NUM' where NUM is the integer of the movie/TV show printed below its title.";
    public static final String TO_VIEW_BACK_SEARCHES = "To go back to search results, press 'tab' and press 'enter' " +
            "to go back the search results.";
    public static final String IO_EXCEPTION_IN_OFFLINE = "I'm sorry but something went wrong when fetching data. PLease try again later!";

    public static final String SORT_SUCCESS = "Nice! You have successfully updated your sort preferences";
    public static final String PREFERNCES_SUCCESS = "Nice! You have successfully updated your preferences";

    public static final String VIEW_BACK_SUCCESS = "Nice! Displaying your last action...";
    public static final String VIEW_BACK_FAILURE = "Sorry! I am unable to display your last action";

    public static final String AUTOCOMPLETION_PROMPT = "Did you mean this?";

    public static final String UNKNOWN_COMMAND = "☹ I'm sorry, but I don't know what that means. "
            + "Refer to help for command formats :-(";
    public static final String INVALID_FORMAT = "Invalid format/parameter is used. Refer to help for command formats :-(";
    public static final String OUT_OF_BOUNDS = "⚠ Index requested is out of bounds! :-(";

    public static final String BLACKLIST_ADD_SUCCUESS = "Successfully added to blacklist!";
    public static final String BLACKLIST_REMOVE_SUCCUESS = "Successfully removed from  blacklist!";
    public static final String BLACKLIST_REMOVE_FAILURE = "Could not find item in your blacklist. Check Spelling?";

    public static final String MISSING_ARGUMENTS = "You are missing a few arguments! Type 'help me' to find out more!";
    public static final String MISSING_COMMAND = "You have entered an empty command!";


    public static final String FILE_NOT_FOUND = "File not found!";
    public static final String FILES_NOT_FOUND  = "File not found! Please wait for next update!";

    public static final String DUPLICATE_BLACKLIST = "Blacklisted item already exists. Here is your blacklist\n";



    //API Messages
    public static final String RECACHING_DATA_API_ERROR = "Sorry, but something went wrong when recaching data";
    public static final String API_FAIL_GENERAL = "☹ I'sorry, but something went wrong when fetching data";
    public static final String API_FAIL_EMPTY = "☹ I'sorry, but I could not find anything related to your request";
    public static final String API_TIME_OUT = "☹ I'sorry, but your request took so long";
    public static final String API_MALFORMED_URL = "Malformed URL in fetching data";
    public static final String SOCKET_TIMEOUT_URL = "Socket Timeout in fetching data";
    public static final String IO_ERROR_URL = "I/O Exception in fetching data";
    public static final String API_INVALID_REQUEST = "☹ I'sorry, but your request is either unclear or invalid";
    //PlaylistExceptions messages
    public static final String PLAYLIST_EXISTS_START =  "☹ I'sorry, but there's already a playlist with called <";
    public static final String PLAYLIST_EXISTS_END =  ">. Please try again with another name.";
    public static final String CANNOT_ADD_TO_PLAYLIST_START = "☹ I'sorry, but <";
    public static final String CANNOT_ADD_TO_PLAYLIST_END = ">. Please try again with another item";
    public static final String CANNOT_REMOVE_FROM_PLAYLIST_START = "☹ I'sorry, but <";
    public static final String CANNOT_REMOVE_FROM_PLAYLIST_END = "> is not a valid item in this playlist. Please try again with another item";
    public static final String PLAYLIST_DOES_NOT_EXISTS_START = "☹ I'sorry, but <";
    public static final String PLAYLIST_DOES_NOT_EXISTS_END = "> does not exists. Please try again with another playlist title.";


}