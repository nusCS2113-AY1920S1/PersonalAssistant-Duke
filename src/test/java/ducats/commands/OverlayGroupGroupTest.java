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
import ducats.components.SongConverter;

public class OverlayGroupGroupTest {
    @Test
    public void testGroupGroup() {
        //ducats.Storage storage = new ducats.Storage(Paths.get
        // ("/home/rishi/Desktop/cs2113t/team/main/data/todo_list" +".txt"));
        String fileDelimiter = System.getProperty("file.separator");
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "songlist.txt");
        //ducats.Storage storage = new ducats.Storage(Paths.get("data", "songlist.txt"));
        SongList songs = new SongList();
        SongConverter songconverter = new SongConverter();
        String testSong = "twinkle aminor 123 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] [[UEs;UAs],[UE;UA],[UEs;"
                + "UAs],[UE;UA],[UEs;UAs],[UE;UA],[UEs;UAs],[UE;UA]] [[MCs;UEs],[MC;UE],[MCs;UEs],[MC;UE],[MCs;UEs],"
                + "[MC;UE],[MCs;UEs],[MC;UE]]"
                + " [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]]"
                + " [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]]"
                + " [[MCs],[MC],[MCs],[MC],[MCs],[MC],[MCs],[MC]] [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]]"
                + " [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] [[MCs],[MC],[MCs],[MC],[MCs],[MC],[MCs],[MC]]"
                + " [[MCs],[MC],[MCs],[MC],[MCs],[MC],[MCs],[MC]] [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]]"
                + " [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]]"
                + " [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] [[MCs],[MC],[MCs],[MC],[MCs],[MC],[MCs],[MC]]"
                + " [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]]"
                + " [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]]"
                + " [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] [[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]]"
                + " [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] ";
        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }
        String input = "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E UPPER_A }{UPPER_E UPPER_A }{UPPER_E UPPER_A }{UPPER_E UPPER_A }|"
                + "{MIDDLE_C UPPER_E }{MIDDLE_C UPPER_E }{MIDDLE_C UPPER_E }{MIDDLE_C UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|"
                + "{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|";

        String expected = "{UPPER_A UPPER_E UPPER_A }{UPPER_A UPPER_E UPPER_A }{UPPER_A UPPER_E UPPER_A }"
                + "{UPPER_A UPPER_E UPPER_A }|"
                + "{UPPER_E UPPER_A MIDDLE_C UPPER_E }{UPPER_E UPPER_A MIDDLE_C UPPER_E }"
                + "{UPPER_E UPPER_A MIDDLE_C UPPER_E }{UPPER_E UPPER_A MIDDLE_C UPPER_E }|"
                + "{MIDDLE_C UPPER_E UPPER_A }{MIDDLE_C UPPER_E UPPER_A }{MIDDLE_C UPPER_E UPPER_A }{MIDDLE_C UPPER_E"
                + " UPPER_A }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|"
                + "{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }{MIDDLE_C }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|";


        ducats.commands.GroupCommand grouper = new ducats.commands.GroupCommand("group 1 3 twinkle1");
        ducats.commands.GroupCommand grouper1 = new ducats.commands.GroupCommand("group 2 4 twinkle2");
        Ui ui = new Ui();
        try {
            grouper.execute(songs,ui,storage);
            grouper1.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.print(e);
        }
        OverlayGroupGroup tester = new OverlayGroupGroup("overlay_group_group twinkle 2 twinkle 1");
        try {
            tester.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.println(e);
        }
        //ducats.components.SongList songList = new SongList();
        ArrayList<ducats.components.Song> findList = songs.findSong("twinkle");
        assertEquals(expected,findList.get(0).showSongChart());
    }
}
