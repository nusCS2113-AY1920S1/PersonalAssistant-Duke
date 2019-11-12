//@@author pdotdeep

package entertainment.pro.logic.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.parsers.commands.SearchCommand;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandSuperTest {
    @Test
    public void subCommand_validInputs_success() throws Exception {
        SearchCommand sc = new SearchCommand(null);



        assertEquals(true, sc.subCommand(new String[]{"search", "movies", "testing"}));
        assertEquals(CommandKeys.MOVIES, sc.getSubRootCommand());
        assertEquals(true, sc.subCommand(new String[]{"search", "tvshows", "testing"}));
        assertEquals(CommandKeys.TVSHOWS, sc.getSubRootCommand());
        assertEquals(true, sc.subCommand(new String[]{"search", "mioivies", "testing"}));
        assertEquals(CommandKeys.MOVIES, sc.getSubRootCommand());
        assertEquals(true, sc.subCommand(new String[]{"search", "telvshows", "testing"}));
        assertEquals(CommandKeys.TVSHOWS, sc.getSubRootCommand());
        assertEquals(false, sc.subCommand(new String[]{"search"}));
        assertEquals(CommandKeys.NONE, sc.getSubRootCommand());



    }


    @Test
    public void subCommand_invalidInputs_throws_MissingInfoException() {
        SearchCommand sc = new SearchCommand(new MovieHandler());

        assertThrows(MissingInfoException.class, () -> {
            sc.subCommand(new String[]{"search"});
        });
        assertThrows(MissingInfoException.class, () -> {
            sc.subCommand(new String[]{});
        });

    }


    @Test
    public void subCommand_invalidInputs_failure() {
        SearchCommand sc = new SearchCommand(new MovieHandler());

        try {
            assertEquals(false, sc.subCommand(new String[]{}));
            assertEquals(CommandKeys.NONE, sc.getSubRootCommand());
            assertEquals(false, sc.subCommand(new String[]{"Jupiter is a planet"}));
            assertEquals(CommandKeys.NONE, sc.getSubRootCommand());
            assertEquals(false, sc.subCommand(new String[]{"search"}));
            assertEquals(CommandKeys.NONE, sc.getSubRootCommand());

        } catch (MissingInfoException e) {
            System.out.print(e.toString());
        }


    }


    @Test
    public void processFlags_validInputs_success() throws InvalidFormatCommandException {

        SearchCommand sc = new SearchCommand(null);

        String command = "search movies joker -A albert -a alex , ashley , alan -b benny -c";
        sc.processFlags(command);
        assertEquals(true, sc.getFlagMap().containsKey("-A"));
        assertEquals(true, sc.getFlagMap().containsKey("-a"));
        assertEquals(true, sc.getFlagMap().containsKey("-b"));

        ArrayList<String> aflag = sc.getFlagMap().get("-A");
        aflag.sort(null);
        ArrayList<String> aflag2 = sc.getFlagMap().get("-a");
        aflag2.sort(null);
        ArrayList<String> bflag = sc.getFlagMap().get("-b");
        bflag.sort(null);
        assertEquals(true, aflag.equals(new ArrayList<String>(
            Arrays.asList("albert"))));
        assertEquals(true, aflag2.equals(new ArrayList<String>(
                Arrays.asList("alan", "alex", "ashley"))));
        assertEquals(true, bflag.equals(new ArrayList<String>(
                Arrays.asList("benny"))));

        ArrayList<String> cflag = sc.getFlagMap().get("-c");
        assertEquals(0, cflag.size());

        ArrayList<String> dflag = sc.getFlagMap().get("-d");
        assertEquals(null, dflag);


        SearchCommand sc2 = new SearchCommand(null);
        String command2 = "search movies joker";
        sc2.processFlags(command2);
        assertEquals(0, sc2.getFlagMap().keySet().size());


    }


    @Test
    public void processFlags_validInputs_failure() throws InvalidFormatCommandException {

        SearchCommand sc2 = new SearchCommand(null);
        String command2 = "search movies joker";
        sc2.processFlags(command2);
        assertEquals(0, sc2.getFlagMap().keySet().size());


        String command3 = "";
        sc2.processFlags(command3);
        assertEquals(0, sc2.getFlagMap().keySet().size());


    }


    @Test
    public void processPayload_validInputs_success() {

        SearchCommand sc = new SearchCommand(null);

        String command = "search movies joker -A albert -a alex , ashley , alan -b benny -c";
        sc.processPayload(command.split(" "));
        assertEquals("joker", sc.getPayload());

        String command2 = "search movies batman and robbin";
        sc.processPayload(command2.split(" "));
        assertEquals("batman and robbin", sc.getPayload());


        String command3 = "search movies Atlantis";
        sc.processPayload(command3.split(" "));
        assertEquals("Atlantis", sc.getPayload());

        String command4 = "search movies";
        sc.processPayload(command4.split(" "));
        assertEquals("", sc.getPayload());

        String command5 = "search movies ";
        sc.processPayload(command5.split(" "));
        assertEquals("", sc.getPayload());

    }


    @Test
    public void processPayload_invalidInputs() {

        SearchCommand sc = new SearchCommand(null);


        String command4 = "search movies";
        sc.processPayload(command4.split(" "));
        assertEquals("", sc.getPayload());

        String command5 = "search movies ";
        sc.processPayload(command5.split(" "));
        assertEquals("", sc.getPayload());

        String command6 = "";
        sc.processPayload(command6.split(" "));
        assertEquals("", sc.getPayload());

        String command7 = "blreacjlist adu joker";
        sc.processPayload(command7.split(" "));
        assertEquals("joker", sc.getPayload());


    }
}
