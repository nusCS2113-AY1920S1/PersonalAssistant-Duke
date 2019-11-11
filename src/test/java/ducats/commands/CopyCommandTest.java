package ducats.commands;

import ducats.DucatsException;
import ducats.Ui;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongConverter;
import ducats.components.SongList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CopyCommandTest {

    //@@author Samuel787

    /**
     * Test for copy [start_index] [end_index]
     */
    @Test
    public void testCopyCommmand_twoIndicesInput_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F " + "}{UPPER_F }{UPPER_E }{UPPER_E }|";

        CopyCommand copyCommand = new CopyCommand("copy 1 2");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }


    /**
     * Test for copy [start_index] [end_index] [paste_index]
     */
    @Test
    public void testCopyCommmand_ThreeIndicesInput_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{UPPER_A" + " }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E }|{MIDDLE_C " +
                "}{MIDDLE_C " + "}{MIDDLE_C }{MIDDLE_C }|";

        CopyCommand copyCommand = new CopyCommand("copy 1 2 3");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }

    /**
     * Test for copy [group_name]
     */
    @Test
    public void testCopyCommmand_CopyGroupName_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GroupCommand group1 = new GroupCommand("group 1 2 test");
        try {
            group1.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F " +
                "}{UPPER_F }{UPPER_E }{UPPER_E }|";

        CopyCommand copyCommand = new CopyCommand("copy test");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }


    /**
     * Test for copy [group_name] [paste_index]
     */
    @Test
    public void testCopyCommmand_CopyGroupToIndex_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GroupCommand group1 = new GroupCommand("group 1 2 test");
        try {
            group1.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E }|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|";

        CopyCommand copyCommand = new CopyCommand("copy test 2");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }


    /**
     * Test for invalid copy [group_name]
     */
    @Test
    public void testCopyCommmand_invalidGroupName_failure() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GroupCommand group1 = new GroupCommand("group 1 2 test");
        try {
            group1.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F " +
                "}{UPPER_F }{UPPER_E }{UPPER_E }|";

        CopyCommand copyCommand = new CopyCommand("copy meow");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("group_not_found", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }

    /**
     * Test for invalid copy [group_name] [paste_index]
     */
    @Test
    public void testCopyCommmand_invalidPasteIndex_failure() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GroupCommand group1 = new GroupCommand("group 1 2 test");
        try {
            group1.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F " +
                "}{UPPER_F }{UPPER_E }{UPPER_E }|";

        CopyCommand copyCommand = new CopyCommand("copy meow 2000");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("no_index", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }


    /**
     * Test for invalid copy [start_index] [end_index]
     */
    @Test
    public void testCopyCommmand_invalidIndex_failure() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F " +
                "}{UPPER_F }{UPPER_E }{UPPER_E }|";

        CopyCommand copyCommand = new CopyCommand("copy -1 1");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("no_index", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }

    /**
     * Test for invalid copy [start_index] [end_index] [paste_index]
     */
    @Test
    public void testCopyCommmand_invalidCopyPasteIndex_failure() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] " + "[[UFs],[UF],[UEs]," + "[UE]," +
                        "[UDs],[UD],[LGs],[LG]] " + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }

        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Jingle");
        try {
            open.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String expected = "{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F }{UPPER_F }{UPPER_E }{UPPER_E " +
                "}|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_C }{UPPER_C }|{UPPER_F " +
                "}{UPPER_F }{UPPER_E }{UPPER_E }|";

        CopyCommand copyCommand = new CopyCommand("copy 1 2 -3");
        try {
            copyCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("no_index", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        assertEquals(expected, findList.get(0).showSongChart());
    }

    //@@author rohan-av
    @Test
    void testStartMetronome() {
        CopyCommand copyCommand = new CopyCommand("placeholder");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, copyCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        CopyCommand copyCommand = new CopyCommand("placeholder");
        assertFalse(copyCommand.isExit());
    }
}
