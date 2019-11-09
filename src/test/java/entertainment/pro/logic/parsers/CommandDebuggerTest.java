package entertainment.pro.logic.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.model.CommandPair;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

public class CommandDebuggerTest {

    @Test
    public void commandSpellChecker_validInputs_success() {

        try {


            CommandPair cp = CommandDebugger.commandSpellChecker(new String[]{"seatch" , "moves" , "batman" } , COMMANDKEYS.NONE, new MovieHandler());
            assertEquals(COMMANDKEYS.SEARCH, cp.getRootCommand());
            assertEquals(COMMANDKEYS.MOVIES, cp.getSubRootCommand());

            CommandPair cp1 = CommandDebugger.commandSpellChecker(new String[]{"bucklist" , "addu" , "batman" } , COMMANDKEYS.NONE, new MovieHandler());
            assertEquals(COMMANDKEYS.BLACKLIST, cp1.getRootCommand());
            assertEquals(COMMANDKEYS.ADD, cp1.getSubRootCommand());


            CommandPair cp2 = CommandDebugger.commandSpellChecker(new String[]{"wtchlist" , "dlelte" , "batman" } , COMMANDKEYS.NONE, new MovieHandler());
            assertEquals(COMMANDKEYS.WATCHLIST, cp2.getRootCommand());
            assertEquals(COMMANDKEYS.DELETE, cp2.getSubRootCommand());


            CommandPair cp3 = CommandDebugger.commandSpellChecker(new String[]{"prefrerences" , "add" , "batman" } , COMMANDKEYS.NONE, new MovieHandler());
            assertEquals(COMMANDKEYS.PREFERENCE, cp3.getRootCommand());
            assertEquals(COMMANDKEYS.ADD, cp3.getSubRootCommand());


            CommandPair cp4 = CommandDebugger.commandSpellChecker(new String[]{"playllist" , "creatte" , "batman" } , COMMANDKEYS.NONE, new MovieHandler());
            assertEquals(COMMANDKEYS.PLAYLIST, cp4.getRootCommand());
            assertEquals(COMMANDKEYS.CREATE, cp4.getSubRootCommand());


        } catch (MissingInfoException e) {

        }


    }


    @Test
    public void commandSpellChecker_invalidInputs_success() {

        CommandPair cp = null;

        try {


            cp = CommandDebugger.commandSpellChecker(new String[]{""} , COMMANDKEYS.NONE, new MovieHandler());
            assertEquals(COMMANDKEYS.PLAYLIST, cp.getRootCommand());
            assertEquals(COMMANDKEYS.CREATE, cp.getSubRootCommand());


        } catch (MissingInfoException e) {

            assertEquals(null , cp);


        }


    }


}
