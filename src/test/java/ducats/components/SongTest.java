package ducats.components;

import ducats.DucatsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongTest {

    static Song song;

    @BeforeAll
    static void createTestSong() throws DucatsException {
        song = new Song("testing","C",120);
        Bar bar = new Bar(0, "2_UA 4_RT 4_LC");
        song.addBar(bar);
    }

    @Test
    void testToString() throws DucatsException {
        assertEquals("testing C 120 [[UAs],[UA],[UA],[UA],[RTs],[RT],[LCs],[LC]] ",song.toString());
    }

    //todo: showSongChart
}