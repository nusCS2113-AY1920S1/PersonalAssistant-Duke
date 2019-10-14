package Commands;

import Execution.CommandStack;

import java.util.TreeMap;

public class CommandStructure {



    /**
     * Defining the Command structure for the program
     *
     */
    public static TreeMap<COMMAND_KEYS, COMMAND_KEYS[]> cmdStructure = new TreeMap<COMMAND_KEYS, COMMAND_KEYS[]>() {{
        put(COMMAND_KEYS.search, new COMMAND_KEYS[]{
            COMMAND_KEYS.movies, COMMAND_KEYS.tvshows, COMMAND_KEYS.cast, COMMAND_KEYS.filters
        });

        put(COMMAND_KEYS.view, new COMMAND_KEYS[]{
            COMMAND_KEYS.profile, COMMAND_KEYS.filters, COMMAND_KEYS.preferences, COMMAND_KEYS.watchlist, COMMAND_KEYS.info, COMMAND_KEYS.showtimes , COMMAND_KEYS.blacklist
        });

        put(COMMAND_KEYS.add, new COMMAND_KEYS[]{
            COMMAND_KEYS.blacklist, COMMAND_KEYS.watchlist
        });

        put(COMMAND_KEYS.remove, new COMMAND_KEYS[]{
                COMMAND_KEYS.blacklist, COMMAND_KEYS.watchlist
        });

        put(COMMAND_KEYS.help, new COMMAND_KEYS[]{
            COMMAND_KEYS.search, COMMAND_KEYS.view, COMMAND_KEYS.add, COMMAND_KEYS.help, COMMAND_KEYS.more, COMMAND_KEYS.yes ,COMMAND_KEYS.set ,  COMMAND_KEYS.playlist , COMMAND_KEYS.yes
        });

        put(COMMAND_KEYS.more, new COMMAND_KEYS[]{
            //EMPTY
        });

        put(COMMAND_KEYS.yes, new COMMAND_KEYS[]{
            //EMPTY
        });

        put(COMMAND_KEYS.set, new COMMAND_KEYS[]{
                COMMAND_KEYS.name , COMMAND_KEYS.age , COMMAND_KEYS.preference
        });

        put(COMMAND_KEYS.preference, new COMMAND_KEYS[]{
                COMMAND_KEYS.add , COMMAND_KEYS.remove
        });

        put(COMMAND_KEYS.playlist, new COMMAND_KEYS[]{
                COMMAND_KEYS.create , COMMAND_KEYS.delete , COMMAND_KEYS.add , COMMAND_KEYS.remove , COMMAND_KEYS.set
        });

    }};


    public static TreeMap<COMMAND_KEYS , String> cmdHelp = new TreeMap<>() {
        {
            put(COMMAND_KEYS.search , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.view , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.add , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.help , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.more , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.set , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.preference , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
            put(COMMAND_KEYS.playlist , "Search for movies,tvshows, cast etc.\neg.: search movies MOVIE_NAME [-P][-C][-L]");
        }
    };

    public static COMMAND_KEYS[] AllRoots = new COMMAND_KEYS[]{COMMAND_KEYS.search ,COMMAND_KEYS.remove , COMMAND_KEYS.view , COMMAND_KEYS.add ,COMMAND_KEYS.help , COMMAND_KEYS.more , COMMAND_KEYS.yes , COMMAND_KEYS.set ,  COMMAND_KEYS.playlist};
}