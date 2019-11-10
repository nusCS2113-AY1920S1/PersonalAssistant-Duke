package ducats.components;

import ducats.DucatsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongTest {

    private static Song song;
    private static Song song2;

    @BeforeAll
    static void createTestSong() throws DucatsException {
        song = new Song("testing","C",120);
        Bar bar = new Bar(0, "2_UA 4_RT 4_LC");
        song.addBar(bar);
        song2 = new Song("testing","C",120);
        Bar bar2 = new Bar(0, "2_UA 4_RT 4_LC");
        song2.addBar(bar2);
    }

    @Test
    void testToString() throws DucatsException {
        assertEquals("testing C 120 [[UAs],[UA],[UA],[UA],[RTs],[RT],[LCs],[LC]] ",song.toString());
    }

    @Test
    void testGetters() throws DucatsException {
        assertEquals("testing", song.getName());
        assertEquals("C", song.getKey());
        assertEquals(120, song.getTempo());
        Bar bar = new Bar(0, "2_UA 4_RT 4_LC");
        song.addBar(bar);
        assertEquals(bar.toString(), song.getBars().get(0).toString());
        assertEquals(bar, song.getBars().get(1));
        assertEquals(0, song.getGroups().size());
    }

    @Test
    void testGetNumBars() {
        assertEquals(1, song.getNumBars());
    }

    @Test
    void testToStringArrayList() throws DucatsException {
        ArrayList<String> expected = new ArrayList<>();
        Bar bar = new Bar(0, "2_UA 4_RT 4_LC");
        expected.add("testing C 120 ");
        expected.add(bar.toString());
        expected.add(bar.toString());
        assertArrayEquals(expected.toArray(),song.toStringArrayList().toArray());
        ArrayList<Bar> testGroup = new ArrayList<>();
        testGroup.add(bar);
        Group group = new Group("test", testGroup);
        song.addGroup(group);
        expected.add("groups:");
        expected.add(group.toString());
        assertArrayEquals(expected.toArray(), song.toStringArrayList().toArray());
    }

    @Test
    void testCreateGroup() throws DucatsException {
        Bar bar = new Bar(0, "2_UA 4_RT 4_LC");
        ArrayList<Bar> testGroup = new ArrayList<>();
        testGroup.add(bar);
        Group group = new Group("test!", testGroup);
        song2.createGroup("test!", 1,1);
        assertEquals(song2.getGroups().get(0).toString(), group.toString());
    }

    //todo: showSongChart
}