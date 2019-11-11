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
import ducats.commands.AddOverlayCommand;
import ducats.components.SongConverter;

public class OverlayBarSameSong {

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

        String fileDelimiter = System.getProperty("file.separator");
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

    }

    @Test
    //this test case tests when overlaying 1 bar onto another
    public void testBarSameSong() {

        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        AddOverlayCommand tester = new AddOverlayCommand("overlay 1 2");


        String expected = "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }|"
                + "{UPPER_C }{UPPER_C }{UPPER_C }{UPPER_C }|"
                + "{UPPER_D }{UPPER_D }{UPPER_D }{UPPER_D }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|"
                + "{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|";
        try {
            tester.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.println(e);
        }
        //ducats.components.SongList songList = new SongList();
        ArrayList<ducats.components.Song> findList = songs.findSong("Twinkle");
        assertEquals(expected,findList.get(0).showSongChart());
    }

    @Test
    public void testBarSameSongEmpty() {

        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);
        AddOverlayCommand tester = new AddOverlayCommand("overlay ");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("overlay_format",e.getType());
            return;
        }
        assert false;
    }

    @Test
    public void testBarSameSongFail1() {

        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);


        String expected = "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }|"
                + "{UPPER_C }{UPPER_C }{UPPER_C }{UPPER_C }|"
                + "{UPPER_D }{UPPER_D }{UPPER_D }{UPPER_D }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|"
                + "{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|";
        AddOverlayCommand tester = new AddOverlayCommand("overlay 112121 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("no_index",e.getType());
            //assert true;
            //System.out.println(e.getMessage());
            return;
        }
        assert false;
    }

    @Test
    public void testBarSameSongFail2() {

        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs);


        String expected = "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }|"
                + "{UPPER_C }{UPPER_C }{UPPER_C }{UPPER_C }|"
                + "{UPPER_D }{UPPER_D }{UPPER_D }{UPPER_D }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|"
                + "{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|";
        AddOverlayCommand tester = new AddOverlayCommand("overlay helleo 2");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("number_index",e.getType());
            return;
        }
        assert false;
    }


}  
