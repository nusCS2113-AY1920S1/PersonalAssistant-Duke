package duke.components;

import duke.DukeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongTest {

    static Song song;

    @BeforeAll
    static void createTestSong() throws DukeException {
        song = new Song("testing","C",120);
        Bar bar = new Bar(0, "2_UA 4_RT 4_LC");
        song.addBar(bar);
    }

    @Test
    void testToString() throws DukeException {
        assertEquals("testing C 120 [[UAs],[UA],[UA],[UA],[RTs],[RT],[LCs],[LC]] ",song.toString());
    }

    //todo: showSongChart
}