package entertainment.pro.logic.contexts;


import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;


public class ContextHelperTest {

    @Test
    public void isRootCommandComplete_validInputs_success(){

        assertEquals(true , ContextHelper.testisRootCommandComplete("blacklist"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("search"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("view"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("yes"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("blacklis"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("watchlis"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("watchlist"));

    }

    @Test
    public void isRootCommandComplete_invalidInputs_failure(){

        assertEquals(false , ContextHelper.testisRootCommandComplete("qwerty"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("polygon"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("jupiter"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("saturn"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("moon"));
    }

    @Test
    public void isSubRootCommandComplete_validInputs_success(){
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("movies"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("tvshows"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("blacklist"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("remove"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("tvshow"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("ad"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("name"));

    }

    @Test
    public void isSubRootCommandComplete_invalidInputs_failure(){
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("qwerty"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("polygon"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("jupiter"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("saturn"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("moon"));

    }


    @Test
    public void getLastIncompleteWords_validInputs_success(){
        String incomplete = ContextHelper.getLastIncompleteWords("search movies Harry potter and the order" , new MovieHandler());
        assertEquals("Harry potter and the order", incomplete);
        String incomplete1 = ContextHelper.getLastIncompleteWords("search movies" , new MovieHandler());
        assertEquals("movies", incomplete1);
        String incomplete2 = ContextHelper.getLastIncompleteWords("blacklist remov" , new MovieHandler());
        assertEquals("remov", incomplete2);
    }

    @Test
    public void getLastIncompleteWords_invalidInputs_failure(){
        String incomplete = ContextHelper.getLastIncompleteWords("" , new MovieHandler());
        assertEquals("", incomplete);
        String incomplete1 = ContextHelper.getLastIncompleteWords("jupiter saturn" , new MovieHandler());
        assertEquals("saturn", incomplete1);
        String incomplete2 = ContextHelper.getLastIncompleteWords("no idea what this is" , new MovieHandler());
        assertEquals("what this is", incomplete2);
    }

    @Test
    public void subStringIndex_validIndex_success(){
        assertEquals( 5, ContextHelper.subStringIndex("hello" , "hello world"));

        assertEquals( 10, ContextHelper.subStringIndex("batman and" , "batman and robbin"));

        assertEquals( 14, ContextHelper.subStringIndex("the art of not" , "the art of not"));
    }

    @Test
    public void subStringIndex_invalidIndex_failure(){

        assertEquals( 0, ContextHelper.subStringIndex("batman" , "hello world"));
        assertEquals( 0, ContextHelper.subStringIndex("jupiter" , "hello world"));
        assertEquals( 0, ContextHelper.subStringIndex("modern day problems" , "hello world"));

    }


    @Test
    public void completeCommand_validIndex_success(){

        ArrayList<String> possibilities = new ArrayList<String>(Arrays.asList("hello world", "hello guys", "hello girls"));
        assertEquals("ello" , ContextHelper.completeCommand(possibilities , "h"));

        ArrayList<String> possibilities1 = new ArrayList<String>(Arrays.asList("batman and jobbin" , "batman and joker" , "batman and jennifer"));
        assertEquals("man and j" , ContextHelper.completeCommand(possibilities1 , "bat"));

        ArrayList<String> possibilities2 = new ArrayList<String>(Arrays.asList("rabbit" , "rabbies" , "rabb"));
        assertEquals("abb" , ContextHelper.completeCommand(possibilities2 , "r"));

    }

    @Test
    public void completeCommand_invalidIndex_failure(){

        ArrayList<String> possibilities = new ArrayList<String>(Arrays.asList("", "Japan", "Korea"));
        assertEquals("" , ContextHelper.completeCommand(possibilities , "h"));

        ArrayList<String> possibilities1 = new ArrayList<String>(Arrays.asList("jobbin" , "mobbin" , "batman and jennifer"));
        assertEquals("" , ContextHelper.completeCommand(possibilities1 , "bat"));

        ArrayList<String> possibilities2 = new ArrayList<String>(Arrays.asList("jupiter" , "saturn" , "uranus"));
        assertEquals("" , ContextHelper.completeCommand(possibilities2 , "r"));

    }





}

