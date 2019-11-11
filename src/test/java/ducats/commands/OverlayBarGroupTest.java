package ducats.components;


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
import ducats.commands.OverlayBarGroup;
import ducats.components.SongConverter;
import ducats.Storage;

public class OverlayBarGroupTest  {

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

        SongConverter songconverter = new SongConverter();
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }


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
        System.out.print("done with test1");
    }

    @Test
    //this test case tests when overlaying 1 bar onto another group - working
    public void testBarGroup() {

        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        String expected = "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }|"
                + "{UPPER_C UPPER_A }{UPPER_C UPPER_A }{UPPER_C UPPER_A }{UPPER_C UPPER_A }|"
                + "{UPPER_D UPPER_A }{UPPER_D UPPER_A }{UPPER_D UPPER_A }{UPPER_D UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|"
                + "{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|";
        OverlayBarGroup tester = new OverlayBarGroup("overlay_bar_group 1 2");
        System.out.println(songs.findSong("Twinkle").toString());
        try {
            tester.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.print("asdas");
            System.out.println(e);
        }
        //ducats.components.SongList songList = new SongList();
        ArrayList<ducats.components.Song> findList = songs.findSong("Twinkle");
        assertEquals(expected,findList.get(0).showSongChart());
    }

    @Test
    //this tests if the class can detect if the group doesnt exist,i.e. greater than
    public void testBarGroupFail1() {

        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        OverlayBarGroup tester = new OverlayBarGroup("overlay_bar_group 10210210 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("no_index",e.getType());
            return;
        }
        assert false;

    }

    @Test
    //this tests if the class can detect if the group doesnt exist,i.e. the index is negative
    public void testBarGroupFail2() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        OverlayBarGroup tester = new OverlayBarGroup("overlay_bar_group -1 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("no_index",e.getType());
            return;
        }
        assert false;
    }

    @Test
    //this tests if the class can detect if the group doesnt exist,i.e. the index is a string
    public void testBarGroupFail3() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        OverlayBarGroup tester = new OverlayBarGroup("overlay_bar_group lewllewq 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("number_index",e.getType());
            return;
        }
        assert false;
    }

    @Test
    //this tests if the class can detect if the group doesnt exist,i.e. the index is a blank
    public void testBarGroupFail4() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        OverlayBarGroup tester = new OverlayBarGroup("overlay_bar_group ");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("overlay_bar_group_format",e.getType());
            return;
        }
        assert false;
    }

    @Test
    //this tests if the class can detect if the group doesnt exist,i.e. the command is just the command
    public void testBarGroupFail5() {
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        OverlayBarGroup tester = new OverlayBarGroup("overlay_bar_group");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("overlay_bar_group_format",e.getType());
            return;
        }
        assert false;
    }
}  
