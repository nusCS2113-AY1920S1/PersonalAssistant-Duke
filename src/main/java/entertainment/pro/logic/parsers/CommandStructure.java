package entertainment.pro.logic.parsers;

import entertainment.pro.commons.enums.COMMANDKEYS;

import java.util.TreeMap;

/**
 * Class to define command structure and command keywords.
 */
public class CommandStructure {

    /**
     * Defining the Command structure for the program.
     *
     */
    public static TreeMap<COMMANDKEYS, COMMANDKEYS[]> cmdStructure = new TreeMap<COMMANDKEYS, COMMANDKEYS[]>() {{
            put(COMMANDKEYS.search, new COMMANDKEYS[] {
                COMMANDKEYS.movies, COMMANDKEYS.tvshows, COMMANDKEYS.cast, COMMANDKEYS.filters
            });

            put(COMMANDKEYS.view, new COMMANDKEYS[] {
                COMMANDKEYS.profile, COMMANDKEYS.filters, COMMANDKEYS.preferences, COMMANDKEYS.watchlist,
                COMMANDKEYS.info, COMMANDKEYS.showtimes, COMMANDKEYS.blacklist,
                COMMANDKEYS.movies, COMMANDKEYS.tv, COMMANDKEYS.back, COMMANDKEYS.entry

            });

            put(COMMANDKEYS.blacklist, new COMMANDKEYS[] {
                COMMANDKEYS.add, COMMANDKEYS.remove
            });

            put(COMMANDKEYS.help , new COMMANDKEYS[]{
                    COMMANDKEYS.search , COMMANDKEYS.view , COMMANDKEYS.help , COMMANDKEYS.more ,
                    COMMANDKEYS.yes , COMMANDKEYS.set ,  COMMANDKEYS.playlist , COMMANDKEYS.me , COMMANDKEYS.blacklist,
                    COMMANDKEYS.watchlist, COMMANDKEYS.get, COMMANDKEYS.preference, COMMANDKEYS.restriction,
                    COMMANDKEYS.find
            });

            put(COMMANDKEYS.yes, new COMMANDKEYS[] {
                //EMPTY
            });

            put(COMMANDKEYS.exit, new COMMANDKEYS[] {
                    //EMPTY
            });

            put(COMMANDKEYS.set, new COMMANDKEYS[] {
                COMMANDKEYS.name, COMMANDKEYS.age, COMMANDKEYS.preference, COMMANDKEYS.sort, COMMANDKEYS.restriction
            });

            put(COMMANDKEYS.preference, new COMMANDKEYS[] {
                COMMANDKEYS.add, COMMANDKEYS.remove, COMMANDKEYS.clear
            });

            put(COMMANDKEYS.playlist, new COMMANDKEYS[] {
                COMMANDKEYS.create, COMMANDKEYS.delete, COMMANDKEYS.add, COMMANDKEYS.remove,
                COMMANDKEYS.set, COMMANDKEYS.list, COMMANDKEYS.clear, COMMANDKEYS.back
            });

            put(COMMANDKEYS.restriction, new COMMANDKEYS[] {
                COMMANDKEYS.add, COMMANDKEYS.remove, COMMANDKEYS.clear
            });

            put(COMMANDKEYS.get, new COMMANDKEYS[]{
                COMMANDKEYS.recommendation
            });

            put(COMMANDKEYS.watchlist, new COMMANDKEYS[] {
                COMMANDKEYS.add,  COMMANDKEYS.set,  COMMANDKEYS.delete
            });
            put(COMMANDKEYS.find, new COMMANDKEYS[] {
                COMMANDKEYS.cinema
        });
    }};


    public static boolean hasSubRoot(COMMANDKEYS root) {
        return CommandStructure.cmdStructure.get(root).length != 0;
    }

    public static boolean hasSubRoot(String root) {
        return CommandStructure.cmdStructure.get(COMMANDKEYS.valueOf(root)).length != 0;
    }



    public static COMMANDKEYS[] AllRoots = new COMMANDKEYS[]{
        COMMANDKEYS.search,
        COMMANDKEYS.view,
        COMMANDKEYS.help,
        COMMANDKEYS.yes,
        COMMANDKEYS.set,
        COMMANDKEYS.playlist,
        COMMANDKEYS.blacklist,
        COMMANDKEYS.watchlist,
        COMMANDKEYS.get,
        COMMANDKEYS.preference,
        COMMANDKEYS.restriction,
        COMMANDKEYS.find,
        COMMANDKEYS.exit};
}
