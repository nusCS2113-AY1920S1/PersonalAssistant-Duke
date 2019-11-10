package entertainment.pro.logic.contexts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.logic.parsers.CommandStructure;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class CommandContextTest {

    @Test
    void getPossibilitiesForRoot_validInputs_success() {
        ArrayList<String> possibleRoots = CommandContext.getPossibilitiesForRoot("searc");
        ArrayList<String> possibleRoots2 = CommandContext.getPossibilitiesForRoot("black");
        ArrayList<String> possibleRoots3 = CommandContext.getPossibilitiesForRoot("watch");
        assertEquals("search" , possibleRoots.get(0));
        assertEquals("blacklist" , possibleRoots2.get(0));
        assertEquals("watchlist" , possibleRoots3.get(0));
    }

    @Test
    void getPossibilitiesForRoot_invalidInputs_failure() {

        CommandContext.initialiseContext();
        ArrayList<String> possibleRoots4 = CommandContext.getPossibilitiesForRoot("");
        ArrayList<String> possibleRoots5 = CommandContext.getPossibilitiesForRoot("somerubbish");

        assertEquals(12 , possibleRoots4.size());
        assertEquals( 0 , possibleRoots5.size());

    }

    @Test
    public void getRoot_validInputs_success(){
        CommandContext.initialiseContext();
        ArrayList<CommandKeys> a = new ArrayList<>();
        Collections.addAll(a, CommandStructure.AllRoots);
        a.sort(null);
        ArrayList<CommandKeys> b = new ArrayList<>();
        for(String s :CommandContext.getRoot()) {
            b.add(CommandKeys.valueOf(s.toUpperCase()));
        }
        b.sort(null);
        assertEquals(true , a.equals(b));
    }

    @Test
    public void getPossibilitiesSubRoot_validInputs_success(){
        CommandContext.initialiseContext();
        ArrayList<String> possible = CommandContext.getPossibilitiesSubRoot("movie");
        assertEquals("movies" , possible.get(0));
        ArrayList<String> possible1 = CommandContext.getPossibilitiesSubRoot("");
        assertEquals("movies" , possible1.get(0));
        ArrayList<String> possible2 = CommandContext.getPossibilitiesSubRoot("prof");
        assertEquals("profile" , possible2.get(0));

        ArrayList<String> possible4 = CommandContext.getPossibilitiesSubRoot("prefer");
        assertEquals("preference" , possible4.get(0));

        ArrayList<String> possible6 = CommandContext.getPossibilitiesSubRoot("view" , "black");
        assertEquals("blacklist", possible6.get(0));
        ArrayList<String> possible7 = CommandContext.getPossibilitiesSubRoot("help" , "playl");
        assertEquals("playlist" , possible7.get(0));
    }

    @Test
    public void getPossibilitiesSubRoot_invalidInputs_success(){
        CommandContext.initialiseContext();

        ArrayList<String> possible5 = CommandContext.getPossibilitiesSubRoot("prefdcwecweer");
        assertEquals(0, possible5.size());


        ArrayList<String> possible8 = CommandContext.getPossibilitiesSubRoot("search" , "black");
        assertEquals(0 , possible8.size());


    }

    @Test
    public void getPossibilitiesSubRootGivenRoot_validInputs_success(){
        CommandContext.initialiseContext();
        for(CommandKeys ck: CommandStructure.AllRoots){
            ArrayList<String> possible = CommandContext.getPossibilitiesSubRootGivenRoot(ck.toString());
            possible.sort(null);
            ArrayList<String> actual =  new ArrayList<>();
            for (CommandKeys c : CommandStructure.cmdStructure.get(ck)){
                actual.add(c.toString().toLowerCase());
            }
            actual.sort(null);
            assertEquals(true , possible.equals(actual));
        }

    }

    @Test
    public void getPossibilitiesSubRootGivenRoot_invalidInputs_failure(){
        CommandContext.initialiseContext();

        String [] invalidTest = {"Qwerty" , "polygon" , "name" , "value" , "jupiter" };
        for(String ck: invalidTest){
            ArrayList<String> possible = CommandContext.getPossibilitiesSubRootGivenRoot(ck);

            assertEquals(0 , possible.size());
        }

    }

}

