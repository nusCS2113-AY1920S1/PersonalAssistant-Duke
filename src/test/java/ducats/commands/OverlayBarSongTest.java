package ducats.commands;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ducats.Parser;
import ducats.commands.AddBarCommand;
import ducats.commands.NewCommand;
import ducats.components.SongList;

import ducats.commands.OverlayGroupGroup;

import ducats.Ui;
import java.nio.file.Paths;
import java.util.ArrayList;
import ducats.components.Song;
import ducats.commands.OverlayBarSong;
import ducats.components.SongConverter;


public class OverlayBarSongTest {

    /**
     * A function that creates a predefined song so that it is easier to test the commands.
     * type-specific error messages.
     *
     * @param ui - Ui class that is being printed to.
     * @param storage - Storage class as the group function needs it.
     * @param songs - Songlist for the list of songs needed for a command
     *
     */
    public void createSong(Ui ui, ducats.Storage storage, SongList songs) {
        // ("/home/rishi/Desktop/cs2113t/team/main/data/todo_list" +".txt"));
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        String testSong2 = "Twinkle1 aminor 120 [[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] ";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
            songs.add(songconverter.convertSongFromString(testSong2));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }
        String input = "{UPPER_A}{UPPER_A}{UPPER_A}{UPPER_A}|"
                + "{UPPER_B}{UPPER_B}{UPPER_B}{UPPER_B}|"
                + "{UPPER_C}{UPPER_C}{UPPER_C}{UPPER_C}|"
                + "{UPPER_D }{UPPER_D }{UPPER_D }{UPPER_D }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|";


        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Twinkle");
        try {
            open.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ducats.commands.GroupCommand grouper = new ducats.commands.GroupCommand("group 1 3 twinkle1");
        ducats.commands.GroupCommand grouper1 = new ducats.commands.GroupCommand("group 2 4 twinkle2");
        try {
            grouper.execute(songs,ui,storage);
            grouper1.execute(songs,ui,storage);
        } catch (Exception e) {
            //System.out.print("addsadsa");
            System.out.print(e);
        }


    }

    @Test
    //this test case tests when overlaying 1 bar onto another
    public void testOverlayBarSong() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        OverlayBarSong tester = new OverlayBarSong("overlay_bar_song Twinkle 1 Twinkle1 2");
        String expected = "{UPPER_B }{UPPER_B }{UPPER_B }{UPPER_B }|"
                + "{UPPER_C UPPER_A }{UPPER_C UPPER_A }{UPPER_C UPPER_A }{UPPER_C UPPER_A }|"
                + "{UPPER_D }{UPPER_D }{UPPER_D }{UPPER_D }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|"
                + "{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|";
        try {
            tester.execute(songs,ui,storage);
        } catch (Exception e) {

            System.out.println(e);
        }
        //ducats.components.SongList songList = new SongList();
        ArrayList<ducats.components.Song> findList = songs.findSong("Twinkle1");
        assertEquals(expected,findList.get(0).showSongChart());
    }

    @Test
    //this test case tests when overlaying bar being overlayed doesnt exist
    public void testOverlayBarSongFail1() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);

        OverlayBarSong tester = new OverlayBarSong("overlay_bar_song Twinkle 10210 Twinkle1 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("no_index",e.getType());
            //System.out.println(e);
            return;
        }
        assert false;
        //ducats.components.SongList songList = new SongList();
    }

    @Test
    //this test case tests when not enough parameters are sent
    public void testOverlayBarSongFail2() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);

        OverlayBarSong tester = new OverlayBarSong("overlay_bar_song ");

        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("overlay_bar_song_format",e.getType());
            return;
        }
        assert false;
    }

    @Test
    //this test case tests when overlaying bar index is a string
    public void testOverlayBarSongFail3() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);

        OverlayBarSong tester = new OverlayBarSong("overlay_bar_song Twinkle hello Twinkle1 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("number_index",e.getType());
            //System.out.println(e);
            return;
        }
        assert false;
        //ducats.components.SongList songList = new SongList();
    }

    @Test
    //this test case tests when overlaying song does not exist
    public void testOverlayBarSongFail4() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);

        OverlayBarSong tester = new OverlayBarSong("overlay_bar_song Twinkle123 1 Twinkle1 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("no_song",e.getType());
            //System.out.println(e);
            return;
        }
        assert false;
        //ducats.components.SongList songList = new SongList();
    }

    @Test
    //this test case tests when overlayed song does not exist
    public void testOverlayBarSongFail5() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);

        OverlayBarSong tester = new OverlayBarSong("overlay_bar_song Twinkle 1 Twinkle123 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("no_song",e.getType());
            //System.out.println(e);
            return;
        }
        assert false;
        //ducats.components.SongList songList = new SongList();
    }
}  
