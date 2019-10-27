package entertainment.pro.logic.contexts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SearchResultContextTest {

    @Test public void testaddResults(){

        ArrayList<MovieInfoObject> searchresults = new ArrayList<MovieInfoObject>(Arrays.asList(
                new MovieInfoObject(1,12,"title 1" ) ,
                new MovieInfoObject(1,15,"title 2" ) ,
                new MovieInfoObject(1,18,"title 3" )));

        ArrayList<String> possibilities = new ArrayList<String>(Arrays.asList("title 1" , "title 2" , "title 3"));

        SearchResultContext.addResults(searchresults);
        ArrayList<String> possible = SearchResultContext.getPossibilities("tit");
        possible.sort(null);
        possibilities.sort(null);

        assertEquals(true ,  possibilities.equals(possible));


    }


}



