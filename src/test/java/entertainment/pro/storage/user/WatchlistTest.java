//@@author hotspur1997

package entertainment.pro.storage.user;

import entertainment.pro.commons.exceptions.DuplicateEntryException;
import entertainment.pro.model.Deadline;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.MovieModel;
import entertainment.pro.model.Period;
import entertainment.pro.storage.utils.BlacklistStorage;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class WatchlistTest {

    @Test
    public void addToWatchlist_deadline_success() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("joker", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movieD);
        assertEquals(true, WatchlistHandler.contains("joker"));
        assertEquals(1, WatchlistHandler.getSize());
        assertEquals("D", WatchlistHandler.getWatchlist().get(0).getType());
        Deadline movieD2 = new Deadline("spiderman", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movieD2);
        assertEquals(true, WatchlistHandler.contains("spiderman"));
        assertEquals(2, WatchlistHandler.getSize());
        assertEquals("D", WatchlistHandler.getWatchlist().get(1).getType());
    }

    @Test
    public void addToWatchlist_period_success() {
        WatchlistHandler.getWatchlist().clear();
        Period movieP = new Period("joker", "P", "20/09/1997 14:00", "08/10/2019 15:00");
        WatchlistHandler.add(movieP);
        assertEquals(true, WatchlistHandler.contains("joker"));
        assertEquals(1, WatchlistHandler.getSize());
        assertEquals("P", WatchlistHandler.getWatchlist().get(0).getType());
        Period movieP2 = new Period("superman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieP2);
        assertEquals(true, WatchlistHandler.contains("superman"));
        assertEquals(2, WatchlistHandler.getSize());
        assertEquals("P", WatchlistHandler.getWatchlist().get(1).getType());
    }

    @Test
    public void addToWatchlist_failure() {
        WatchlistHandler.getWatchlist().clear();
        assertEquals(false, WatchlistHandler.contains("joker"));
        assertEquals(0, WatchlistHandler.getSize());
    }

    @Test
    public void mark_as_Done_Success() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("joker", "D", "20/09/1997 14:00");
        Period movieD2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieD);
        WatchlistHandler.add(movieD2);
        WatchlistHandler.getWatchlist().get(0).setDone(true);
        assertEquals(true, WatchlistHandler.getWatchlist().get(0).isDone());
        WatchlistHandler.getWatchlist().get(1).setDone(true);
        assertEquals(true, WatchlistHandler.getWatchlist().get(1).isDone());
    }

    @Test
    public void mark_as_Done_Failure() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("joker", "D", "20/09/1997 14:00");
        Period movieD2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieD);
        WatchlistHandler.add(movieD2);
        assertEquals(false, WatchlistHandler.getWatchlist().get(0).isDone());
        assertEquals(false, WatchlistHandler.getWatchlist().get(1).isDone());
    }

    @Test
    public void watchlist_Remove_Success() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("joker", "D", "20/09/1997 14:00");
        Period movieD2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieD);
        WatchlistHandler.add(movieD2);
        assertEquals(true, WatchlistHandler.removeFromWatchlist("joker", new MovieHandler()));
        assertEquals(true, WatchlistHandler.removeFromWatchlist("spiderman", new MovieHandler()));
    }

    @Test
    public void watchlist_Remove_Failure() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("batman", "D", "20/09/1997 14:00");
        Period movieD2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieD);
        WatchlistHandler.add(movieD2);
        assertEquals(false, WatchlistHandler.removeFromWatchlist("joker", new MovieHandler()));
        assertEquals(false, WatchlistHandler.removeFromWatchlist("superman", new MovieHandler()));
    }

    @Test
    public void duplicate_detection_success() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("joker", "D", "20/09/1997 14:00");
        Deadline movieD2 = new Deadline("joker", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movieD);
        assertEquals(false, WatchlistHandler.add(movieD2));

        Period movieE = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        Period movieE2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieE);
        assertEquals(false, WatchlistHandler.add(movieE2));
    }

    @Test
    public void duplicate_detection_failure() {
        WatchlistHandler.getWatchlist().clear();
        Deadline movieD = new Deadline("joker", "D", "20/09/1997 14:00");
        Deadline movieD2 = new Deadline("batman", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movieD);
        assertEquals(true, WatchlistHandler.add(movieD2));

        Period movieE = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        Period movieE2 = new Period("superman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movieE);
        assertEquals(true, WatchlistHandler.add(movieE2));
    }
}