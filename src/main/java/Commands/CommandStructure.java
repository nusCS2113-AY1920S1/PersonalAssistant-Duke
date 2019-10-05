package Commands;

import java.util.TreeMap;

public class CommandStructure {


    public static TreeMap<COMMAND_KEYS, COMMAND_KEYS[]> cmdStructure = new TreeMap<COMMAND_KEYS, COMMAND_KEYS[]>() {{
        put(COMMAND_KEYS.search, new COMMAND_KEYS[]{
                COMMAND_KEYS.movies, COMMAND_KEYS.tvshows ,COMMAND_KEYS.cast ,COMMAND_KEYS.filters
        });

        put(COMMAND_KEYS.view, new COMMAND_KEYS[]{
                COMMAND_KEYS.profile, COMMAND_KEYS.filters, COMMAND_KEYS.preferences , COMMAND_KEYS.watchlist , COMMAND_KEYS.info , COMMAND_KEYS.showtimes
        });

        put(COMMAND_KEYS.add, new COMMAND_KEYS[]{
                COMMAND_KEYS.blacklist , COMMAND_KEYS.watchlist ,
        });

        put(COMMAND_KEYS.help, new COMMAND_KEYS[]{
                COMMAND_KEYS.search , COMMAND_KEYS.view , COMMAND_KEYS.add ,COMMAND_KEYS.help , COMMAND_KEYS.more , COMMAND_KEYS.yes
        });

        put(COMMAND_KEYS.more, new COMMAND_KEYS[]{
                //EMPTY
        });

        put(COMMAND_KEYS.yes, new COMMAND_KEYS[]{
                //EMPTY
        });

    }};

    public static COMMAND_KEYS[] AllRoots = new COMMAND_KEYS[]{COMMAND_KEYS.search , COMMAND_KEYS.view , COMMAND_KEYS.add ,COMMAND_KEYS.help , COMMAND_KEYS.more , COMMAND_KEYS.yes};



}
