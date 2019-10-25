package entertainment.pro.logic.parsers;

import entertainment.pro.commons.enums.COMMANDKEYS;

import java.util.TreeMap;

public class CommandStructure {

    /**
     * Defining the Command structure for the program.
     *
     */
    public static TreeMap<COMMANDKEYS, COMMANDKEYS[]> cmdStructure = new TreeMap<COMMANDKEYS , COMMANDKEYS[]>() {{
        put(COMMANDKEYS.search , new COMMANDKEYS[]{
                COMMANDKEYS.movies , COMMANDKEYS.tvshows , COMMANDKEYS.cast , COMMANDKEYS.filters
        });

        put(COMMANDKEYS.view , new COMMANDKEYS[]{
                COMMANDKEYS.profile , COMMANDKEYS.filters , COMMANDKEYS.preferences , COMMANDKEYS.watchlist , COMMANDKEYS.info , COMMANDKEYS.showtimes , COMMANDKEYS.blacklist,
                COMMANDKEYS.movies, COMMANDKEYS.tv, COMMANDKEYS.back, COMMANDKEYS.entry

        });

        put(COMMANDKEYS.blacklist , new COMMANDKEYS[]{
                COMMANDKEYS.add, COMMANDKEYS.remove
        });


        put(COMMANDKEYS.watchlist , new COMMANDKEYS[]{
                COMMANDKEYS.add, COMMANDKEYS.remove , COMMANDKEYS.done
        });

//
//        put(COMMANDKEYS.add , new COMMANDKEYS[]{
//                COMMANDKEYS.blacklist, COMMANDKEYS.watchlist
//        });
//
//        put(COMMANDKEYS.remove , new COMMANDKEYS[]{
//                COMMANDKEYS.blacklist, COMMANDKEYS.watchlist
//        });

        put(COMMANDKEYS.help , new COMMANDKEYS[]{
                COMMANDKEYS.search , COMMANDKEYS.view , COMMANDKEYS.add , COMMANDKEYS.help , COMMANDKEYS.more , COMMANDKEYS.yes , COMMANDKEYS.set ,  COMMANDKEYS.playlist , COMMANDKEYS.yes
        });

//        put(COMMANDKEYS.more , new COMMANDKEYS[]{
//                //EMPTY
//        });

        put(COMMANDKEYS.yes , new COMMANDKEYS[]{
                //EMPTY
        });

        put(COMMANDKEYS.set , new COMMANDKEYS[]{
                COMMANDKEYS.name , COMMANDKEYS.age , COMMANDKEYS.preference, COMMANDKEYS.sort
        });

        put(COMMANDKEYS.preference , new COMMANDKEYS[]{
                COMMANDKEYS.add , COMMANDKEYS.remove , COMMANDKEYS.clear
        });

        put(COMMANDKEYS.playlist , new COMMANDKEYS[]{
                COMMANDKEYS.create , COMMANDKEYS.delete , COMMANDKEYS.add , COMMANDKEYS.remove , COMMANDKEYS.set
        });

        put(COMMANDKEYS.restriction , new COMMANDKEYS[]{
                COMMANDKEYS.add , COMMANDKEYS.remove , COMMANDKEYS.clear
        });

        put(COMMANDKEYS.get , new COMMANDKEYS[]{
                COMMANDKEYS.recommendation
        });

        put(COMMANDKEYS.watchlist , new COMMANDKEYS[]{
                COMMANDKEYS.add,  COMMANDKEYS.set,  COMMANDKEYS.delete
        });
    }};




    public static COMMANDKEYS[] AllRoots = new COMMANDKEYS[]{
            COMMANDKEYS.search
            , COMMANDKEYS.remove
            , COMMANDKEYS.view
            , COMMANDKEYS.add
            , COMMANDKEYS.help
            , COMMANDKEYS.more
            , COMMANDKEYS.yes
            , COMMANDKEYS.set
            , COMMANDKEYS.playlist
            , COMMANDKEYS.blacklist
            , COMMANDKEYS.watchlist
            , COMMANDKEYS.preference};
}
