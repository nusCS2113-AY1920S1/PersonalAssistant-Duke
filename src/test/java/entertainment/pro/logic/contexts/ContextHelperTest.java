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
    public void testisRootCommandComplete(){
//        Method method = ContextHelper.class.getDeclaredMethod("isRootCommandComplete", String.class);
//        method.setAccessible(true);
//        method.invoke(ContextHelper. , "")
        assertEquals(true , ContextHelper.testisRootCommandComplete("blacklist"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("search"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("view"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("yes"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("blacklis"));
        assertEquals(false , ContextHelper.testisRootCommandComplete("watchlis"));
        assertEquals(true , ContextHelper.testisRootCommandComplete("watchlist"));
        assertEquals(false , ContextHelper.testisRootCommandComplete(""));

    }

    @Test
    public void testisSubRootCommandComplete() {
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("movies"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("tvshows"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("blacklist"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("remove"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("tvshow"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete("ad"));
        assertEquals(true , ContextHelper.testisSubRootCommandComplete("name"));
        assertEquals(false , ContextHelper.testisSubRootCommandComplete(""));

    }

    @Test
    public void testgetLastIncompleteWords(){
        String incomplete = ContextHelper.getLastIncompleteWords("search movies Harry potter and the order" , new MovieHandler());
        assertEquals("Harry potter and the order", incomplete);
        String incomplete1 = ContextHelper.getLastIncompleteWords("search movies" , new MovieHandler());
        assertEquals("movies", incomplete1);
        String incomplete2 = ContextHelper.getLastIncompleteWords("blacklist remov" , new MovieHandler());
        assertEquals("remov", incomplete2);
    }

    @Test
    public void testsubStringIndex(){
        assertEquals( 5, ContextHelper.subStringIndex("hello" , "hello world"));

        assertEquals( 0, ContextHelper.subStringIndex("batman" , "hello world"));

        assertEquals( 10, ContextHelper.subStringIndex("batman and" , "batman and robbin"));

        assertEquals( 14, ContextHelper.subStringIndex("the art of not" , "the art of not"));
    }

    @Test
    public void testcompleteCommand(){
        ArrayList<String> possibilities = new ArrayList<String>(Arrays.asList("hello world", "hello guys", "hello girls"));
        assertEquals("ello" , ContextHelper.completeCommand(possibilities , "h"));

        ArrayList<String> possibilities1 = new ArrayList<String>(Arrays.asList("batman and jobbin" , "batman and joker" , "batman and jennifer"));
        assertEquals("man and j" , ContextHelper.completeCommand(possibilities1 , "bat"));

        ArrayList<String> possibilities2 = new ArrayList<String>(Arrays.asList("rabbit" , "rabbies" , "rabb"));
        assertEquals("abb" , ContextHelper.completeCommand(possibilities2 , "r"));


    }

    @Test
    public void testgetAllHints() throws IOException {

//        MovieHandler mh = new MovieHandler();
//        mh.initialize();

//        ArrayList<String> hints = ContextHelper.getAllHints("sear", mh );
//        assertEquals("search" , hints.get(0) );

    }



}

