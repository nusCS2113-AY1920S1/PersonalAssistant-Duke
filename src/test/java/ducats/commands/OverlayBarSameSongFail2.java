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

public class OverlayBarSameSongFail2 {
    @Test

    //this test case tests when the user inputs a string instead of a nuber

    public void testGroupGroup() {

        // ("/home/rishi/Desktop/cs2113t/team/main/data/todo_list" +".txt"));
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");
        SongConverter songconverter = new SongConverter();
        //ducats.Storage storage = new ducats.Storage(Paths.get("data", "songlist.txt"));
        SongList songs = new SongList();
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


        Ui ui = new Ui();
        ducats.commands.OpenCommand open = new ducats.commands.OpenCommand("open Twinkle");
        try {
            open.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            //assert true;
            //System.out.println(e.getMessage());
            return;
        }
        assert false;
        //ducats.components.SongList songList = new SongList();
        ArrayList<ducats.components.Song> findList = songs.findSong("Twinkle");
        //assertEquals(expected,findList.get(0).showSongChart());
    }
}  
