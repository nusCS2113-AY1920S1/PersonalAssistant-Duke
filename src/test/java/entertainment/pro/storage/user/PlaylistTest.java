package entertainment.pro.storage.user;

import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.logic.PlaylistExceptions;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.PlaylistMovieInfoObject;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    private final String playlistName = "testName";
    private Playlist testPlaylist = new Playlist(playlistName);
    private final String playlistDescription = "testDescription";
    private final MovieInfoObject testMovieInfoObject1 = new MovieInfoObject(1234, "title1", true, new Date(), "summary1", "posterpath", "backdroppath", 1.2, new ArrayList<>(), false);
    private final MovieInfoObject testMovieInfoObject2 = new MovieInfoObject(4321, "title2", true, new Date(), "summary2", "posterpath", "backdroppath", 1.2, new ArrayList<>(), false);
    private ArrayList<MovieInfoObject> movies = new ArrayList<>(); {
        movies.add(testMovieInfoObject1);
        movies.add(testMovieInfoObject2);
    }

    @Test
    void addToPlaylistTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("1");
            arrayListTest.add("2");
            flagMapTest.put("-m", arrayListTest);
            Playlist actual = playlistCommands.add(testPlaylist, flagMapTest, movies);
            Playlist expected = new Playlist(playlistName, "", convert(movies));
            assertEqualPlaylist(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeFromPlaylistTest() {
        try {
            testPlaylist = testPlaylist.add(convert(movies));
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("1");
            flagMapTest.put("-m", arrayListTest);
            ArrayList<MovieInfoObject> playlistMovies = new ArrayList<>();
            playlistMovies.add(testMovieInfoObject2);
            Playlist actual = playlistCommands.remove(testPlaylist, flagMapTest);
            Playlist expected = new Playlist(playlistName, "", convert(playlistMovies));
            assertEqualPlaylist(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void clearPlaylistTest() {
        try {
            testPlaylist = testPlaylist.add(convert(movies));
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            Playlist actual = playlistCommands.clear(testPlaylist);
            Playlist expected = new Playlist(playlistName, "", new ArrayList<>());
            assertEqualPlaylist(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setNameTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("new title");
            flagMapTest.put("-n", arrayListTest);
            Playlist actual = playlistCommands.setToPlaylist(testPlaylist, flagMapTest);
            Playlist expected = new Playlist("new title", "", new ArrayList<>());
            assertEqualPlaylist(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setDescriptionTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add(playlistDescription);
            flagMapTest.put("-d", arrayListTest);
            Playlist actual = playlistCommands.setToPlaylist(testPlaylist, flagMapTest);
            Playlist expected = new Playlist(playlistName, playlistDescription, new ArrayList<>());
            assertEqualPlaylist(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setBothTest() {
        try {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListDescription = new ArrayList<>();
            arrayListDescription.add(playlistDescription);
            flagMapTest.put("-d", arrayListDescription);
            ArrayList<String> arrayListName = new ArrayList<>();
            arrayListName.add("new title");
            flagMapTest.put("-n", arrayListName);
            Playlist actual = playlistCommands.setToPlaylist(testPlaylist, flagMapTest);
            Playlist expected = new Playlist("new title", playlistDescription, new ArrayList<>());
            assertEqualPlaylist(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void duplicateAddTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            UserProfile userProfile = new EditProfileJson().load();
            testPlaylist = testPlaylist.add(convert(movies));
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("1");
            arrayListTest.add("2");
            flagMapTest.put("-m", arrayListTest);
            PlaylistExceptions.checkAddCommand(playlistName, flagMapTest, userProfile, movies);
        });
    }

    @Test
    void nonExistentRemoveTest() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            UserProfile userProfile = new EditProfileJson().load();
            TreeMap<String, ArrayList<String>> flagMapTest = new TreeMap<>();
            ArrayList<String> arrayListTest = new ArrayList<>();
            arrayListTest.add("1");
            arrayListTest.add("2");
            flagMapTest.put("-m", arrayListTest);
            PlaylistExceptions.checkRemoveCommand(playlistName, flagMapTest, userProfile, new ArrayList<>());
        });
    }

    @AfterAll
    static void clear() {
        PlaylistCommands playlistCommands = new PlaylistCommands("testName");
        playlistCommands.delete();
        assertFalse(new File("./testName.json").exists());
    }

    private ArrayList<PlaylistMovieInfoObject> convert(ArrayList<MovieInfoObject> movies) {
        ArrayList<PlaylistMovieInfoObject> convertMovies = new ArrayList<>();
        for (MovieInfoObject log : movies) {
            Date date = log.getReleaseDateInfo();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String string = dateFormat.format(date);
            System.out.println("help " + log.getTitle() + " " + log.getFullPosterPathInfo());
            boolean fakeType = false;
            PlaylistMovieInfoObject testMovie = new PlaylistMovieInfoObject(fakeType, log.getId(),
                    log.getTitle(), log.getReleaseDateInfo(), log.getSummaryInfo(), log.getRatingInfo(),
                    log.getGenreIdInfo(), log.getFullPosterPathInfo(), log.getFullBackdropPathInfo(),
                    log.isAdultContent(), string);
            convertMovies.add(testMovie);
        }
        return convertMovies;
    }

    private void assertEqualPlaylist(Playlist expected, Playlist actual) {
        assertEquals(expected.getPlaylistName(), actual.getPlaylistName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getMovies(), actual.getMovies());
    }
}