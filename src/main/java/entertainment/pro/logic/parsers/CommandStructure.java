package entertainment.pro.logic.parsers;

import entertainment.pro.commons.enums.CommandKeys;

import java.util.TreeMap;

/**
 * Class to define command structure and command keywords.
 */
public class CommandStructure {

    /**
     * Defining the Command structure for the program.
     *
     */
    public static TreeMap<CommandKeys, CommandKeys[]> cmdStructure = new TreeMap<CommandKeys, CommandKeys[]>() {{
            put(CommandKeys.SEARCH, new CommandKeys[] {
                CommandKeys.MOVIES, CommandKeys.TVSHOWS, CommandKeys.CAST, CommandKeys.FILTERS
            });

            put(CommandKeys.VIEW, new CommandKeys[] {
                CommandKeys.PROFILE, CommandKeys.FILTERS, CommandKeys.PREFERENCE, CommandKeys.WATCHLIST,
                CommandKeys.INFO, CommandKeys.BLACKLIST,
                CommandKeys.MOVIES, CommandKeys.TV, CommandKeys.BACK, CommandKeys.ENTRY, CommandKeys.RECOMMENDATION

            });

            put(CommandKeys.BLACKLIST, new CommandKeys[] {
                CommandKeys.ADD, CommandKeys.REMOVE
            });

            put(CommandKeys.HELP, new CommandKeys[]{
                CommandKeys.SEARCH, CommandKeys.VIEW, CommandKeys.HELP, CommandKeys.MORE,
                CommandKeys.SET,  CommandKeys.PLAYLIST, CommandKeys.ME, CommandKeys.BLACKLIST,
                CommandKeys.WATCHLIST, CommandKeys.GET, CommandKeys.PREFERENCE, CommandKeys.RESTRICTION,
                CommandKeys.FIND, CommandKeys.EXIT
            });

            put(CommandKeys.YES, new CommandKeys[] {
                //EMPTY
            });

            put(CommandKeys.EXIT, new CommandKeys[] {
                    //EMPTY
            });

            put(CommandKeys.SET, new CommandKeys[] {
                CommandKeys.NAME, CommandKeys.AGE, CommandKeys.PREFERENCE, CommandKeys.SORT, CommandKeys.RESTRICTION
            });

            put(CommandKeys.PREFERENCE, new CommandKeys[] {
                CommandKeys.ADD, CommandKeys.REMOVE, CommandKeys.CLEAR
            });

            put(CommandKeys.PLAYLIST, new CommandKeys[] {
                CommandKeys.CREATE, CommandKeys.DELETE, CommandKeys.ADD, CommandKeys.REMOVE,
                CommandKeys.SET, CommandKeys.LIST, CommandKeys.CLEAR, CommandKeys.BACK
            });

            put(CommandKeys.RESTRICTION, new CommandKeys[] {
                CommandKeys.ADD, CommandKeys.REMOVE, CommandKeys.CLEAR
            });

            /*
            put(COMMANDKEYS.get, new COMMANDKEYS[]{
                COMMANDKEYS.recommendation
            });
            */

            put(CommandKeys.WATCHLIST, new CommandKeys[] {
                CommandKeys.ADD,  CommandKeys.SET,  CommandKeys.DELETE
            });
            put(CommandKeys.FIND, new CommandKeys[] {
                CommandKeys.CINEMA
            });
        }};


    public static boolean hasSubRoot(CommandKeys root) {
        return CommandStructure.cmdStructure.get(root).length != 0;
    }

    public static boolean hasSubRoot(String root) {
        return CommandStructure.cmdStructure.get(CommandKeys.valueOf(root)).length != 0;
    }



    public static CommandKeys[] AllRoots = new CommandKeys[]{
        CommandKeys.SEARCH,
        CommandKeys.VIEW,
        CommandKeys.HELP,
        CommandKeys.YES,
        CommandKeys.SET,
        CommandKeys.PLAYLIST,
        CommandKeys.BLACKLIST,
        CommandKeys.WATCHLIST,
        CommandKeys.PREFERENCE,
        CommandKeys.RESTRICTION,
        CommandKeys.FIND,
        CommandKeys.EXIT
    };
}
