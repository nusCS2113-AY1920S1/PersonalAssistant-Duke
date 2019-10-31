package entertainment.pro.logic.contexts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.logic.contexts.CommandContext;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.contexts.CommandContext;
import entertainment.pro.logic.parsers.CommandStructure;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class CommandContextTest {

    @Test
    public void testGetPossibilitiesForRoot(){
        CommandContext.initialiseContext();
        ArrayList<String> possibleRoots = CommandContext.getPossibilitiesForRoot("searc");
        ArrayList<String> possibleRoots2 = CommandContext.getPossibilitiesForRoot("black");
        ArrayList<String> possibleRoots3 = CommandContext.getPossibilitiesForRoot("watch");
        ArrayList<String> possibleRoots4 = CommandContext.getPossibilitiesForRoot("");
        ArrayList<String> possibleRoots5 = CommandContext.getPossibilitiesForRoot("somerubbish");
        assertEquals(possibleRoots.get(0) , "search");
        assertEquals(possibleRoots2.get(0) , "blacklist");
        assertEquals(possibleRoots3.get(0) , "watchlist");
        assertEquals(possibleRoots4.get(0) , "search");
        assertEquals(possibleRoots5.size() , 0);

    }

    @Test
    public void testGetRoot(){
        CommandContext.initialiseContext();
        ArrayList<COMMANDKEYS> a = new ArrayList<>();
        Collections.addAll(a, CommandStructure.AllRoots);
        a.sort(null);
        ArrayList<COMMANDKEYS> b = new ArrayList<>();
        for(String s :CommandContext.getRoot()) {
            b.add(COMMANDKEYS.valueOf(s));
        }
        b.sort(null);
        assertEquals(a.equals(b), true);

    }

    @Test
    public void testGetPossibilitiesForSubRoot() {

        CommandContext.initialiseContext();
        ArrayList<String> possible = CommandContext.getPossibilitiesSubRoot("movie");
        assertEquals(possible.get(0) , "movies");
        ArrayList<String> possible1 = CommandContext.getPossibilitiesSubRoot("");
        assertEquals(possible1.get(0) , "movies");
        ArrayList<String> possible2 = CommandContext.getPossibilitiesSubRoot("prof");
        assertEquals(possible2.get(0) , "profile");
        ArrayList<String> possible3 = CommandContext.getPossibilitiesSubRoot("showt");
        assertEquals(possible3.get(0) , "showtimes");
        ArrayList<String> possible4 = CommandContext.getPossibilitiesSubRoot("prefer");
        assertEquals(possible4.get(0) , "preferences");
        ArrayList<String> possible5 = CommandContext.getPossibilitiesSubRoot("prefdcwecweer");
        assertEquals(possible5.size() , 0);

        ArrayList<String> possible6 = CommandContext.getPossibilitiesSubRoot("view" , "showt");
        assertEquals(possible6.get(0) , "showtimes");
        ArrayList<String> possible7 = CommandContext.getPossibilitiesSubRoot("help" , "playl");
        assertEquals(possible7.get(0) , "playlist");
        ArrayList<String> possible8 = CommandContext.getPossibilitiesSubRoot("search" , "black");
        assertEquals(possible8.size() , 0);


    }

    @Test
    public void testGetPossibilitiesSubRootForRoot(){
        CommandContext.initialiseContext();
        for(COMMANDKEYS ck: CommandStructure.AllRoots){
            ArrayList<String> possible = CommandContext.getPossibilitiesSubRootGivenRoot(ck.toString());
            possible.sort(null);
            ArrayList<String> actual =  new ArrayList<>();
            for (COMMANDKEYS c : CommandStructure.cmdStructure.get(ck)){
                actual.add(c.toString());
            }
            actual.sort(null);
            assertEquals(true , possible.equals(actual));
        }

    }


}

