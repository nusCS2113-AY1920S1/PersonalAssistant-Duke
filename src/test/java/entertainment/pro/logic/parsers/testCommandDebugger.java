package entertainment.pro.logic.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.contexts.CommandContext;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.model.CommandPair;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

public class testCommandDebugger {

    @Test
    public void commandSpellChecker_validInputs_success() {

        try {


            CommandPair cp = CommandDebugger.commandSpellChecker(new String[]{"seatch" , "moves" , "batman" } , COMMANDKEYS.none , new MovieHandler());
            assertEquals(COMMANDKEYS.search , cp.getRootCommand());
            assertEquals(COMMANDKEYS.movies , cp.getSubRootCommand());

            CommandPair cp1 = CommandDebugger.commandSpellChecker(new String[]{"bucklist" , "addu" , "batman" } , COMMANDKEYS.none , new MovieHandler());
            assertEquals(COMMANDKEYS.blacklist , cp1.getRootCommand());
            assertEquals(COMMANDKEYS.add , cp1.getSubRootCommand());


            CommandPair cp2 = CommandDebugger.commandSpellChecker(new String[]{"wtchlist" , "dlelte" , "batman" } , COMMANDKEYS.none , new MovieHandler());
            assertEquals(COMMANDKEYS.watchlist , cp2.getRootCommand());
            assertEquals(COMMANDKEYS.delete , cp2.getSubRootCommand());


            CommandPair cp3 = CommandDebugger.commandSpellChecker(new String[]{"prefrerences" , "add" , "batman" } , COMMANDKEYS.none , new MovieHandler());
            assertEquals(COMMANDKEYS.preference , cp3.getRootCommand());
            assertEquals(COMMANDKEYS.add , cp3.getSubRootCommand());


            CommandPair cp4 = CommandDebugger.commandSpellChecker(new String[]{"playllist" , "creatte" , "batman" } , COMMANDKEYS.none , new MovieHandler());
            assertEquals(COMMANDKEYS.playlist , cp4.getRootCommand());
            assertEquals(COMMANDKEYS.create , cp4.getSubRootCommand());


        } catch (MissingInfoException e) {

        }


    }


    @Test
    public void commandSpellChecker_invalidInputs_success() {

        CommandPair cp = null;

        try {


            cp = CommandDebugger.commandSpellChecker(new String[]{""} , COMMANDKEYS.none , new MovieHandler());
            assertEquals(COMMANDKEYS.playlist , cp.getRootCommand());
            assertEquals(COMMANDKEYS.create , cp.getSubRootCommand());


        } catch (MissingInfoException e) {

            assertEquals(null , cp);


        }


    }


}
