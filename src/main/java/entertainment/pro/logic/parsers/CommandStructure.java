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
            put(COMMANDKEYS.SEARCH, new COMMANDKEYS[] {
                COMMANDKEYS.MOVIES, COMMANDKEYS.TVSHOWS, COMMANDKEYS.CAST, COMMANDKEYS.FILTERS
            });

            put(COMMANDKEYS.VIEW, new COMMANDKEYS[] {
                COMMANDKEYS.PROFILE, COMMANDKEYS.FILTERS, COMMANDKEYS.PREFERENCE, COMMANDKEYS.WATCHLIST,
                COMMANDKEYS.INFO, COMMANDKEYS.BLACKLIST,
                COMMANDKEYS.MOVIES, COMMANDKEYS.TV, COMMANDKEYS.BACK, COMMANDKEYS.ENTRY, COMMANDKEYS.RECOMMENDATION

            });

            put(COMMANDKEYS.BLACKLIST, new COMMANDKEYS[] {
                COMMANDKEYS.ADD, COMMANDKEYS.REMOVE
            });

            put(COMMANDKEYS.HELP, new COMMANDKEYS[]{
                    COMMANDKEYS.SEARCH, COMMANDKEYS.VIEW, COMMANDKEYS.HELP, COMMANDKEYS.MORE,
                    COMMANDKEYS.SET,  COMMANDKEYS.PLAYLIST, COMMANDKEYS.ME, COMMANDKEYS.BLACKLIST,
                    COMMANDKEYS.WATCHLIST, COMMANDKEYS.GET, COMMANDKEYS.PREFERENCE, COMMANDKEYS.RESTRICTION,
                    COMMANDKEYS.FIND, COMMANDKEYS.EXIT
            });

            put(COMMANDKEYS.YES, new COMMANDKEYS[] {
                //EMPTY
            });

            put(COMMANDKEYS.EXIT, new COMMANDKEYS[] {
                    //EMPTY
            });

            put(COMMANDKEYS.SET, new COMMANDKEYS[] {
                COMMANDKEYS.NAME, COMMANDKEYS.AGE, COMMANDKEYS.PREFERENCE, COMMANDKEYS.SORT, COMMANDKEYS.RESTRICTION
            });

            put(COMMANDKEYS.PREFERENCE, new COMMANDKEYS[] {
                COMMANDKEYS.ADD, COMMANDKEYS.REMOVE, COMMANDKEYS.CLEAR
            });

            put(COMMANDKEYS.PLAYLIST, new COMMANDKEYS[] {
                COMMANDKEYS.CREATE, COMMANDKEYS.DELETE, COMMANDKEYS.ADD, COMMANDKEYS.REMOVE,
                COMMANDKEYS.SET, COMMANDKEYS.LIST, COMMANDKEYS.CLEAR, COMMANDKEYS.BACK
            });

            put(COMMANDKEYS.RESTRICTION, new COMMANDKEYS[] {
                COMMANDKEYS.ADD, COMMANDKEYS.REMOVE, COMMANDKEYS.CLEAR
            });

            /*
            put(COMMANDKEYS.get, new COMMANDKEYS[]{
                COMMANDKEYS.recommendation
            });
            */

            put(COMMANDKEYS.WATCHLIST, new COMMANDKEYS[] {
                COMMANDKEYS.ADD,  COMMANDKEYS.SET,  COMMANDKEYS.DELETE
            });
            put(COMMANDKEYS.FIND, new COMMANDKEYS[] {
                COMMANDKEYS.CINEMA
        });
    }};


    public static boolean hasSubRoot(COMMANDKEYS root) {
        return CommandStructure.cmdStructure.get(root).length != 0;
    }

    public static boolean hasSubRoot(String root) {
        return CommandStructure.cmdStructure.get(COMMANDKEYS.valueOf(root)).length != 0;
    }



    public static COMMANDKEYS[] AllRoots = new COMMANDKEYS[]{
        COMMANDKEYS.SEARCH,
        COMMANDKEYS.VIEW,
        COMMANDKEYS.HELP,
        COMMANDKEYS.YES,
        COMMANDKEYS.SET,
        COMMANDKEYS.PLAYLIST,
        COMMANDKEYS.BLACKLIST,
        COMMANDKEYS.WATCHLIST,
        COMMANDKEYS.PREFERENCE,
        COMMANDKEYS.RESTRICTION,
        COMMANDKEYS.FIND,
        COMMANDKEYS.EXIT};
}
