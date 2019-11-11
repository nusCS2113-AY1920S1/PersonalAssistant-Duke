package ducats.commands;

import ducats.DucatsException;
import ducats.Ui;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongConverter;
import ducats.components.SongList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GroupCommandTest {

    //@@author Samuel787

    /**
     * Test for group [start_index] [end_index] [group_name].
     */
    @Test
    public void testGroupCommmand_validInput_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong = "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                + "[[UFs],[UF],[UEs]," + "[UE]," + "[UDs],[UD],[LGs],[LG]] "
                + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
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

        GroupCommand groupCommand = new GroupCommand("group 1 2 test");
        try {
            groupCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<Song> findList = songs.findSong("Jingle");
        ArrayList<Group> resultGroup = findList.get(0).getGroups();
        String expectedResult = "[test [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] [[UFs],[UF],[UEs],[UE],[UDs],"
                + "[UD],[LGs],[LG]] ]";
        String actualResult = resultGroup.toString();
        assertEquals(expectedResult, actualResult);
    }


    /**
     * Test for group [start_index] [end_index] [group_name].
     */
    @Test
    public void testGroupCommmand_inValidEndIndex_noIndexException() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong = "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                + "[[UFs],[UF],[UEs]," + "[UE]," + "[UDs],[UD],[LGs],[LG]] "
                + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
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

        GroupCommand groupCommand = new GroupCommand("group 1 30 test");
        try {
            groupCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("no_index", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        ArrayList<Group> resultGroup = findList.get(0).getGroups();
        String expectedResult = "[test [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] [[UFs],[UF],[UEs],[UE],[UDs],"
                + "[UD],[LGs],[LG]] ]";
        String actualResult = resultGroup.toString();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test for group [start_index] [end_index] [group_name].
     */
    @Test
    public void testGroupCommmand_sameGroupName_nameExistsException() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs]," + "[UE]," + "[UDs],[UD],[LGs],[LG]] "
                        + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
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

        GroupCommand groupCommand = new GroupCommand("group 1 2 test");
        try {
            groupCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
        }

        GroupCommand groupCommandSame = new GroupCommand("group 1 2 test");
        try {
            groupCommandSame.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("name_exists", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        ArrayList<Group> resultGroup = findList.get(0).getGroups();
        String expectedResult = "[test [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] [[UFs],[UF],[UEs],[UE],[UDs],"
                + "[UD],[LGs],[LG]] ]";
        String actualResult = resultGroup.toString();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test for group [start_index] [end_index] [group_name].
     */
    @Test
    public void testGroupCommmand_emptyParameters_groupFormatException() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong = "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                + "[[UFs],[UF],[UEs]," + "[UE]," + "[UDs],[UD],[LGs],[LG]] "
                + "[[MCs],[MC],[MC],[MC],[LCs],[LC],[LC],[LC]]";
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

        GroupCommand groupWrongFormat = new GroupCommand("group");
        try {
            groupWrongFormat.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("group_format", e.getType());
            return;
        }
        assert false;
        ArrayList<Song> findList = songs.findSong("Jingle");
        ArrayList<Group> resultGroup = findList.get(0).getGroups();
        String expectedResult = "[test [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] [[UFs],[UF],[UEs],[UE],[UDs],"
                + "[UD],[LGs],[LG]] ]";
        String actualResult = resultGroup.toString();
        assertEquals(expectedResult, actualResult);
    }

    //@@author rohan-av
    @Test
    void testStartMetronome() {
        GroupCommand groupCommand = new GroupCommand("placeholder");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, groupCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        GroupCommand groupCommand = new GroupCommand("placeholder");
        assertFalse(groupCommand.isExit());
    }
}
