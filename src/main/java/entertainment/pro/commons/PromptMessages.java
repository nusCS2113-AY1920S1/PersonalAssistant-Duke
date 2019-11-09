package entertainment.pro.commons;

/**
 * Contains and defines messages printed by the app.
 */
public class PromptMessages {
    // view back messages
    public static final String VIEW_BACK_FAILURE = "Sorry, there is nothing to view back!";
    public static final String VIEW_BACK_SUCCESS = "Nice! Displaying back your search results...";
    public static final String TO_VIEW_BACK_SEARCHES = "To go back to search results, enter 'view back'";

    // process message
    public static final String WAIT_FOR_APP_TO_PROCESS = "Please wait while the app processes your request...";

    //genre related messages
    public static final String INVALID_GENRE_NAME = "I'm sorry but you have entered a invalid genre...";
    public static final String REPETITVE_GENRE_NAME = "I'm sorry but the genre entered by you already exists " +
            "or you are entering the same genre more than a time...";
    public static final String GENRE_DOES_NOT_EXIST = "I'm sorry but the genre entered by you does not exists " +
            "under your preferences";

    // search for movies related messages
    public static final String VIEW_CURRENT_MOVIES_SUCCESS =
            "Data fetch is complete! Displaying list of currently showing movies...";
    public static final String VIEW_UPCOMING_MOVIES_SUCCESS =
            "Data fetch is complete! Displaying list of upcoming movies...";
    public static final String VIEW_TRENDING_MOVIES_SUCCESS =
            "Data fetch is complete! Displaying list of most trending movies...";
    public static final String VIEW_POPULAR_MOVIES_SUCCESS =
            "Data fetch is complete! Displaying list of most popular movies...";
    public static final String VIEW_TOP_RATED_MOVIES_SUCCESS =
            "Data fetch is complete! Displaying list of top-rated movies...";
    public static final String VIEW_SEARCH_MOVIES_SUCCESS =
            "Data fetch is complete! Displaying list of movies related to the title entered...";
    public static final String SEARCH_TYPE_IS_CURRENT_MOVIES =
            "Search type is currently showing movies...";
    public static final String SEARCH_TYPE_IS_UPCOMING_MOVIES =
            "Search type is upcoming movies...";
    public static final String SEARCH_TYPE_IS_TRENDING_MOVIES =
            "Search type is most trending movies...";
    public static final String SEARCH_TYPE_IS_POPULAR_MOVIES =
            "Search type is most popular movies...";
    public static final String SEARCH_TYPE_IS_TOP_RATED_MOVIES =
            "Search type is top-rated movies...";
    public static final String SEARCH_TYPE_IS_SEARCH_MOVIES =
            "Search type is movies related to the title entered...";

    // search for tv related messages
    public static final String VIEW_CURRENT_TV_SUCCESS =
            "Data fetch is complete! Displaying list of currently showing TV shows...";
    public static final String VIEW_TRENDING_TV_SUCCESS =
            "Data fetch is complete! Displaying list of most trending TV shows...";
    public static final String VIEW_POPULAR_TV_SUCCESS =
            "Data fetch is complete! Displaying list of most popular TV shows...";
    public static final String VIEW_TOP_RATED_TV_SUCCESS =
            "Data fetch is complete! Displaying list of top-rated TV shows...";
    public static final String VIEW_SEARCH_TV_SUCCESS =
            "Data fetch is complete! Displaying list of TV shows related to the title entered...";
    public static final String SEARCH_TYPE_IS_CURRENT_TV =
            "Search type is currently showing TV shows...";
    public static final String SEARCH_TYPE_IS_TRENDING_TV =
            "Search type is most trending TV shows...";
    public static final String SEARCH_TYPE_IS_POPULAR_TV =
            "Search type is most popular TV shows...";
    public static final String SEARCH_TYPE_IS_TOP_RATED_TV =
            "Search type is top-rated TV shows...";
    public static final String SEARCH_TYPE_IS_SEARCH_TV =
            "Search type is TV shows related to the title entered...";

    // general search failure related messages
    public static final String EMPTY_PARAM_IN_SEARCH = "Empty parameter entered in search command";
    public static final String INVALID_PARAM_IN_SEARCH = "Invalid parameter entered in search command";

    // invalid combination of parameters message
    public static final String INVALID_COMBI_OF_FLAGS =
            "I'm sorry but this is a invalid command. "
                    + "You can either set your preferences to be what's saved on the app "
                    + "or enter new preferences particularly for this search request "
                    + "but can't do both at the same time!";

    // no result found message
    public static final String NO_RESULTS_FOUND =
            "I'm very sorry but could not find any results that matches your request. "
                    + "Try entering a different command!";

    // API related message
    public static final String DATA_OBTAINED_FROM_API =
            "[Data displayed here was obtained from an external source]";
    public static final String RECACHING_DATA_API_ERROR = "Sorry, but something went wrong when recaching data";
    public static final String API_FAIL_GENERAL = "I'sorry, but something went wrong when fetching data. Please specify your search request clearly...";
    public static final String API_NULL_DATA = "Null data in API";
    public static final String API_TIME_OUT = "☹ I'sorry, but your request took so long";
    public static final String API_MALFORMED_URL = "Malformed URL in fetching data";
    public static final String SOCKET_TIMEOUT_URL = "Socket Timeout in fetching data";
    public static final String IO_ERROR_URL = "I/O Exception in fetching data";
    public static final String API_INVALID_REQUEST = "☹ I'sorry, but your request is either unclear or invalid";
    public static final String NULL_URL = "Null URL detected.";
    public static final String DATA_EXTRACT_FROM_API_SUCCESS = "Data has been extracted from API successfully";
    public static final String EXTRACT_MORE_INFO_START = "Extracting more information about a particular movie/TV show";
    public static final String EXTRACT_MORE_INFO_COMPLETE = "Extraction of more information about a particular " +
            "movie/TV show complete";
    public static final String EXTRACT_CAST_SUCCESS = "Extraction of cast details is a success";
    public static final String EXTRACT_CERT_SUCCESS = "Extraction of cert details is a success";
    public static final String UNABLE_TO_EXTRACT_CAST = "Unable to extract cast details from API";
    public static final String UNABLE_TO_EXTRACT_CERT = "Unable to extract cert details from API";



    // retrieve data from local files message
    public static final String DATA_OBTAINED_FROM_LOCAL_FILES =
            "[Data displayed here was obtained from storage files as weak/no internet connection was detected."
                    + "Please take note that posters for search results are unavailable for offline search requests]";
    public static final String IO_EXCEPTION_IN_OFFLINE = "IO Exception when extracting data from offline";
    public static final String DATA_EXTRACT_FROM_OFFLINE_NEEDED = "Data needs to be extracted from offline files";
    public static final String DATA_EXTRACT_FROM_OFFLINE_SUCCESS = "Data has been extracted from offline files successfully";
    public static final String START_OFFLINE_DATA_EXTRACTION = "Extraction of offline data begins";
    public static final String OFFLINE_DATA_EXTRACTION_FAILED = "Unable to extract data from offline files";
    public static final String OFFLINE_DATA_EXTRACTION_SUCCESS = "Extraction of offline data is successful";
    public static final String EXTRACTING_FROM_FILE = "Extracting from respective file";
    public static final String EXTRACTED_FROM_FILE = "Extracted from respective file";
    public static final String PARSE_EXCEPTION_IN_EXTRACTION = "Parse exception took place when extracting data";

    // view more info related message
    public static final String TO_VIEW_MORE_INFO = "To view more information about a movie/TV show, enter the command, "
            + "'view entry NUM' where NUM is the integer of the movie/TV show printed below its title.";

    // editing preference related messages
    public static final String PREFERENCES_SUCCESS = "Nice! You have successfully updated your preferences";


    public static final String DID_YOU_MEAN = "Did you mean:";

    public static final String LOGGER_UNKNOWN_COMMAND_TYPED = "Unknown command typed";

    public static final String COMMAND_MISSING_ARGS = "Command is missing a few arguments";

    public static final String UNABLE_TO_PROCESS = "Sorry we are unable to process your command. " +
            "Please check help for more details!";

    // autocomplete related messages
    public static final String AUTOCOMPLETION_PROMPT = "Did you mean this?";

    // command related messages
    public static final String UNKNOWN_COMMAND = "I'm sorry, but I don't know what that means. "
            + "Refer to help for command formats :-(";
    public static final String INVALID_FORMAT =
            "Invalid format/parameter is used. Refer to help for command formats :-(";
    public static final String OUT_OF_BOUNDS = "⚠ Index requested is out of bounds! :-(";
    public static final String MISSING_ARGUMENTS = "You are missing a few arguments! Type 'help me' to find out more!";
    public static final String MISSING_COMMAND = "You have entered an empty command!";

    // blacklist related messages
    public static final String BLACKLIST_ADD_SUCCUESS = "Successfully added to blacklist!";
    public static final String BLACKLIST_REMOVE_SUCCUESS = "Successfully removed from  blacklist!";
    public static final String BLACKLIST_REMOVE_FAILURE = "Could not find item in your blacklist. Check Spelling?";
    public static final String DUPLICATE_BLACKLIST = "Blacklisted item already exists. Here is your blacklist\n";

    public static final String EXECUTING_MISTYPED_COMMAND = "Executing mistyped command";




    // file related messages
    public static final String FILE_NOT_FOUND = "File not found!";
    public static final String FILES_NOT_FOUND = "File not found! Please wait for next update!";

    //PlaylistExceptions / SetExceptions messages
    public static final String PLAYLIST_PAYLOAD_EMPTY =
            "☹ I'm sorry, but you did not enter a name for your playlist. Please try again in with playlist name";
    public static final String PLAYLIST_EXISTS_START =
            "☹ I'm sorry, but there's already a playlist with called <";
    public static final String PLAYLIST_EXISTS_END =
            ">. Please try again with another name.";
    public static final String CANNOT_ADD_TO_PLAYLIST_START =
            "☹ I'm sorry, but <";
    public static final String CANNOT_ADD_TO_PLAYLIST_END =
            ">. Please try again with another item";
    public static final String PLAYLIST_DOES_NOT_EXISTS_START =
            "☹ I'm sorry, but <";
    public static final String PLAYLIST_DOES_NOT_EXISTS_END =
            "> does not exists. Please try again with another playlist title.";
    public static final String INDEX_OUT_OF_BOUNDS_START =
            "☹ I'm sorry, but the index < ";
    public static final String INDEX_OUT_OF_BOUNDS_END =
            "> is out of range :( Please try again with another index";
    public static final String INDEX_NOT_NUMBER_START =
            "☹ I'm sorry, but <";
    public static final String INDEX_NOT_NUMBER_END =
            "> is not a number :( Please try again with a number";
    public static final String INVALID_FLAG_START =
            "☹ I'm sorry, but <";
    public static final String INVALID_FLAG_END =
            "> is not a valid flag. Please try again in the correct format";
    public static final String INVALID_GENRE_NAME_START =
            "☹ I'm sorry, but <";
    public static final String INVALID_GENRE_NAME_END =
            "> is not a valid genre. Please try again with another genre";
    public static final String GENRE_IN_RESTRICTION_START =
            "☹ I'm sorry, but <";
    public static final String GENRE_IN_RESTRICTION_END =
            "> is already in your restrictions. Please try again with another genre";
    public static final String GENRE_IN_PREFERENCE_START =
            "☹ I'm sorry, but <";
    public static final String GENRE_IN_PREFERENCE_END =
            "> is already in your preferences. Please try again with another genre";
    public static final String AGE_RESTRICTED =
            "☹ I'm sorry, but you are below 21 yrs old and are not allowed to set your adult restriction";
    public static final String SET_PAYLOAD_EMPTY =
            "☹ I'm sorry, but you did not enter your name/age. Please try again";
    public static final String INVALID_ADULT_OPTION_START =
            "☹ I'm sorry, but <";
    public static final String INVALID_ADULT_OPTION_END =
            "> is an invalid option for adult preferences. Please try again with yes / no";
    public static final String INVALID_SORT_OPTION_START =
            "☹ I'm sorry, but <";
    public static final String INVALID_SORT_OPTION_END =
            "> is an invalid sort option. Please try again with 1 / 2 / 3";
    public static final String AGE_IS_NEGATIVE =
            "☹ I'm sorry, but age needs to be a positive integer. Please try again";
    public static final String INVALID_PAYLOAD  =
            "☹ I'm sorry, but payload can't start with '-'. Please try again";

    // cache related messages
    public static final String READING_CACHE_FILES = "Reading cache files";
    public static final String RECONFIG_CACHE_FILES = "Reconfiguring cache files";
    public static final String NEED_TO_CACHE_FILES_AGAIN = "Need to recache file again...";
    public static final String NO_NEED_TO_CACHE_FILES_AGAIN = "No Need to recache file again.";
    public static final String READING_CACHE_DATA = "Reading cache data";
    public static final String READING_CACHE_DATA_IS_COMPLETE = "Reading cache data is complete";
    public static final String IO_EXCEPTION_CACHE_DATA = "IO Exception happened when reading data";
    public static final String CLASS_EXCEPTION_CACHE_DATA = "Class exception happened when reading data";
    public static final String WRITING_CACHE_DATA = "Reading cache data";
}