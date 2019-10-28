package entertainment.pro.commons;

/**
 * Contains and defines messages printed by the app.
 *
 */
public class PromptMessages {
    public static final String VIEW_CURRENT_MOVIES_SUCCESS = "Alright! Displaying currently showing movies...";
    public static final String VIEW_UPCOMING_MOVIES_SUCCESS = "Alright! Displaying upcoming movies...";
    public static final String VIEW_TRENDING_MOVIES_SUCCESS = "Alright! Displaying trending movies...";
    public static final String VIEW_POPULAR_MOVIES_SUCCESS = "Alright! Displaying popular movies...";
    public static final String VIEW_TOP_RATED_MOVIES_SUCCESS = "Alright! Displaying top-rated movies...";

    public static final String VIEW_CURRENT_TV_SUCCESS = "Alright! Displaying currently showing TV shows...";
    public static final String VIEW_UPCOMING_TV_SUCCESS = "Alright! Displaying upcoming TV shows...";
    public static final String VIEW_TRENDING_TV_SUCCESS = "Alright! Displaying trending TV shows...";
    public static final String VIEW_POPULAR_TV_SUCCESS = "Alright! Displaying popular TV shows...";
    public static final String VIEW_TOP_RATED_TV_SUCCESS = "Alright! Displaying top-rated TV shows...";

    public static final String SORT_SUCCESS = "Nice! You have successfully updated your sort preferences";
    public static final String PREFERNCES_SUCCESS = "Nice! You have successfully updated your preferences";

    public static final String VIEW_BACK_SUCCESS = "Nice! Displaying your last action...";
    public static final String VIEW_BACK_FAILURE = "Sorry! I am unable to display your last action";

    public static final String AUTOCOMPLETION_PROMPT = "Did you mean this?";

    public static final String UNKNOWN_COMMAND = "☹ I'm sorry, but I don't know what that means. "
            + "Refer to help for command formats :-(";
    public static final String INVALID_FORMAT = "⚡ Invalid format. Refer to help for command formats. :-(";
    public static final String OUT_OF_BOUNDS = "⚠ Index requested is out of bounds! :-(";

    public static final String BLACKLIST_ADD_SUCCUESS = "Successfully added to blacklist!";
    public static final String BLACKLIST_REMOVE_SUCCUESS = "Successfully removed from  blacklist!";
    public static final String BLACKLIST_REMOVE_FAILURE = "Sorry could not find such an item in your blacklist";

    //API Messages
    public static final String API_FAIL_GENERAL = "☹ I'sorry, but something went wrong when fetching data";
    public static final String API_FAIL_EMPTY = "☹ I'sorry, but I could not find anything related to your request";
    public static final String API_TIME_OUT = "☹ I'sorry, but your request took so long";
    public static final String API_OFFLINE = "☹ I'sorry, no internet connection detected. "
            + "So, the app will operate in offline mode";
    public static final String API_INVALID_REQUEST = "☹ I'sorry, but your request is either unclear or invalid";
}