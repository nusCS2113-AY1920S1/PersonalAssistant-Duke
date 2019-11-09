package entertainment.pro.storage.user;

import entertainment.pro.logic.parsers.commands.PlaylistCommand;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    private Playlist testPlaylist;
    private static final String playlistName = "testName";
    private static final String playlistDescription = "testDescription";
    private static final File playlistFile = new File("./" + playlistName + ".json");
    private final MovieInfoObject testMovieInfoObject1 = new MovieInfoObject(1234, "title1", true, new Date(), "summary1", "posterpath", "backdroppath", 1.2, new ArrayList<>(), false);
    private final MovieInfoObject testMovieInfoObject2 = new MovieInfoObject(4321, "title2", true, new Date(), "summary2", "posterpath", "backdroppath", 1.2, new ArrayList<>(), false);
    ArrayList<MovieInfoObject> arrayList = new ArrayList<>(); {
        arrayList.add(testMovieInfoObject1);
        arrayList.add(testMovieInfoObject2);
    }
    private final ArrayList<MovieInfoObject> movies = arrayList;


    @BeforeAll
    public static void setUp() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            playlistCommands.create();
            assertTrue(playlistFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addToPlaylistTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("1");
            arrayListTest.add("2");
            flagMapTest.put("-m", arrayListTest);
            playlistCommands.add(flagMapTest, movies);
            assertEquals(2, testPlaylist.getMovies().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeFromPlaylistTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("1");
            flagMapTest.put("-m", arrayListTest);
            playlistCommands.remove(flagMapTest);
            assertEquals(1, testPlaylist.getMovies().size());
            assertEquals("title2", testPlaylist.getMovies().get(0).getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearPlaylistTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            playlistCommands.clear();
            assertEquals(new ArrayList<>(), testPlaylist.getMovies());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void clear() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            playlistCommands.create();
            assertFalse(playlistFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
