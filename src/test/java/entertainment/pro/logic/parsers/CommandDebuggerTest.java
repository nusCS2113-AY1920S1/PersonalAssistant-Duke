package entertainment.pro.logic.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.model.CommandPair;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

public class CommandDebuggerTest {

    @Test
    public void commandSpellChecker_validInputs_success() {

        try {


            CommandPair cp = CommandDebugger.commandSpellChecker(new String[]{"seatch" , "moves" , "batman" } , CommandKeys.NONE, new MovieHandler());
            assertEquals(CommandKeys.SEARCH, cp.getRootCommand());
            assertEquals(CommandKeys.MOVIES, cp.getSubRootCommand());

            CommandPair cp1 = CommandDebugger.commandSpellChecker(new String[]{"bucklist" , "addu" , "batman" } , CommandKeys.NONE, new MovieHandler());
            assertEquals(CommandKeys.BLACKLIST, cp1.getRootCommand());
            assertEquals(CommandKeys.ADD, cp1.getSubRootCommand());


            CommandPair cp2 = CommandDebugger.commandSpellChecker(new String[]{"wtchlist" , "dlelte" , "batman" } , CommandKeys.NONE, new MovieHandler());
            assertEquals(CommandKeys.WATCHLIST, cp2.getRootCommand());
            assertEquals(CommandKeys.DELETE, cp2.getSubRootCommand());


            CommandPair cp3 = CommandDebugger.commandSpellChecker(new String[]{"prefrerences" , "add" , "batman" } , CommandKeys.NONE, new MovieHandler());
            assertEquals(CommandKeys.PREFERENCE, cp3.getRootCommand());
            assertEquals(CommandKeys.ADD, cp3.getSubRootCommand());


            CommandPair cp4 = CommandDebugger.commandSpellChecker(new String[]{"playllist" , "creatte" , "batman" } , CommandKeys.NONE, new MovieHandler());
            assertEquals(CommandKeys.PLAYLIST, cp4.getRootCommand());
            assertEquals(CommandKeys.CREATE, cp4.getSubRootCommand());


        } catch (MissingInfoException e) {

        }


    }


    @Test
    public void commandSpellChecker_invalidInputs_success() {

        CommandPair cp = null;

        try {


            cp = CommandDebugger.commandSpellChecker(new String[]{""} , CommandKeys.NONE, new MovieHandler());
            assertEquals(CommandKeys.PLAYLIST, cp.getRootCommand());
            assertEquals(CommandKeys.CREATE, cp.getSubRootCommand());


        } catch (MissingInfoException e) {

            assertEquals(null , cp);


        }


    }


}
