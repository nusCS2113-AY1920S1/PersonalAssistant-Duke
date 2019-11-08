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
        Deadline movie_d = new Deadline("joker", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movie_d);
        assertEquals(true, WatchlistHandler.contains("joker"));
        assertEquals(1, WatchlistHandler.getSize());
        assertEquals("D", WatchlistHandler.getWatchlist().get(0).getType());
        Deadline movie_d2 = new Deadline("spiderman", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movie_d2);
        assertEquals(true, WatchlistHandler.contains("spiderman"));
        assertEquals(2, WatchlistHandler.getSize());
        assertEquals("D", WatchlistHandler.getWatchlist().get(1).getType());
    }

    @Test
    public void addToWatchlist_period_success() {
        Period movie_d = new Period("joker", "P", "20/09/1997 14:00", "08/10/2019 15:00");
        WatchlistHandler.add(movie_d);
        assertEquals(true, WatchlistHandler.contains("joker"));
        assertEquals(1, WatchlistHandler.getSize());
        assertEquals("P", WatchlistHandler.getWatchlist().get(0).getType());
        Period movie_d2 = new Period("superman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_d2);
        assertEquals(true, WatchlistHandler.contains("superman"));
        assertEquals(2, WatchlistHandler.getSize());
        assertEquals("P", WatchlistHandler.getWatchlist().get(1).getType());
    }

    @Test
    public void addToWatchlist_failure() {
        assertEquals(false, WatchlistHandler.contains("joker"));
        assertEquals(0, WatchlistHandler.getSize());
    }

    @Test
    public void mark_as_Done_Success() {
        Deadline movie_d = new Deadline("joker", "D", "20/09/1997 14:00");
        Period movie_d2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_d);
        WatchlistHandler.add(movie_d2);
        WatchlistHandler.getWatchlist().get(0).setDone(true);
        assertEquals(true, WatchlistHandler.getWatchlist().get(0).isDone());
        WatchlistHandler.getWatchlist().get(1).setDone(true);
        assertEquals(true, WatchlistHandler.getWatchlist().get(1).isDone());
    }

    @Test
    public void mark_as_Done_Failure() {
        Deadline movie_d = new Deadline("joker", "D", "20/09/1997 14:00");
        Period movie_d2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_d);
        WatchlistHandler.add(movie_d2);
        assertEquals(false, WatchlistHandler.getWatchlist().get(0).isDone());
        assertEquals(false, WatchlistHandler.getWatchlist().get(1).isDone());
    }

    @Test
    public void watchlist_Remove_Success() {
        Deadline movie_d = new Deadline("joker", "D", "20/09/1997 14:00");
        Period movie_d2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_d);
        WatchlistHandler.add(movie_d2);
        assertEquals(true, WatchlistHandler.removeFromWatchlist("joker", new MovieHandler()));
        assertEquals(true, WatchlistHandler.removeFromWatchlist("spiderman", new MovieHandler()));
    }

    @Test
    public void watchlist_Remove_Failure() {
        Deadline movie_d = new Deadline("batman", "D", "20/09/1997 14:00");
        Period movie_d2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_d);
        WatchlistHandler.add(movie_d2);
        assertEquals(false, WatchlistHandler.removeFromWatchlist("joker", new MovieHandler()));
        assertEquals(false, WatchlistHandler.removeFromWatchlist("superman", new MovieHandler()));
    }

    @Test
    public void duplicate_detection_success() {
        Deadline movie_d = new Deadline("joker", "D", "20/09/1997 14:00");
        Deadline movie_d2 = new Deadline("joker", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movie_d);
        assertEquals(false, WatchlistHandler.add(movie_d2));

        Period movie_e = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        Period movie_e2 = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_e);
        assertEquals(false, WatchlistHandler.add(movie_e2));
    }

    @Test
    public void duplicate_detection_failure() {
        Deadline movie_d = new Deadline("joker", "D", "20/09/1997 14:00");
        Deadline movie_d2 = new Deadline("batman", "D", "20/09/1997 14:00");
        WatchlistHandler.add(movie_d);
        assertEquals(true, WatchlistHandler.add(movie_d2));

        Period movie_e = new Period("spiderman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        Period movie_e2 = new Period("superman", "P", "20/09/1997 14:00", "10/10/2019 15:00");
        WatchlistHandler.add(movie_e);
        assertEquals(true, WatchlistHandler.add(movie_e2));
    }
}
