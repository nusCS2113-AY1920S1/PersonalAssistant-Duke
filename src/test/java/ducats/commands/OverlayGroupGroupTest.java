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
    /**
     * A function that creates a  song from the bars so that it is easier to test the commands.
     * type-specific error messages.
     *
     * @param ui - Ui class that is being printed to.
     * @param storage - Storage class as the group function needs it.
     * @param songs - Songlist for the list of songs needed for a command.
     * @param testSong - the string of bars that the user wants to convert to a song.
     * @param groupcommand1 - the group command syntax to be performed.
     * @param groupCommand2 - the group command syntax to be performed
     *
     */
    public void createSong(Ui ui, ducats.Storage storage, SongList songs, String testSong, String groupcommand1,
                           String groupCommand2) {

        String fileDelimiter = System.getProperty("file.separator");

        SongConverter songconverter = new SongConverter();

        try {
            songs.add(songconverter.convertSongFromString(testSong));
        } catch (Exception e) {
            System.out.println(testSong);
            songs = new SongList();
        }
        ducats.commands.GroupCommand grouper = new ducats.commands.GroupCommand(groupcommand1);
        ducats.commands.GroupCommand grouper1 = new ducats.commands.GroupCommand(groupCommand2);

        try {
            grouper.execute(songs,ui,storage);
            grouper1.execute(songs,ui,storage);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Test
    public void testGroupGroup() {
        String fileDelimiter = System.getProperty("file.separator");
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
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs,testSong,"group 1 3 twinkle1","group 2 4 twinkle2");
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


    @Test
    //larger group onto smaller group
    public void testGroupGroupUnequalGroup() {
        String fileDelimiter = System.getProperty("file.separator");
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs,testSong,"group 1 4 Twinkle1","group 2 4 Twinkle");
        String expected = "{UPPER_A }{UPPER_A }{UPPER_A }{UPPER_A }|"
                + "{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }{UPPER_B UPPER_A }|"
                + "{UPPER_C UPPER_B }{UPPER_C UPPER_B }{UPPER_C UPPER_B }{UPPER_C UPPER_B }|"
                + "{UPPER_D UPPER_C }{UPPER_D UPPER_C }{UPPER_D UPPER_C }{UPPER_D UPPER_C }|"
                + "{UPPER_E }{UPPER_E }{UPPER_E }{UPPER_E }|"
                + "{UPPER_F }{UPPER_F }{UPPER_F }{UPPER_F }|"
                + "{UPPER_G }{UPPER_G }{UPPER_G }{UPPER_G }|";
        OverlayGroupGroup tester = new OverlayGroupGroup("overlay_group_group Twinkle 1 Twinkle 2");
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
    //smaller group onto larger group
    public void testGroupGroupUnequalGroup2() {
        String fileDelimiter = System.getProperty("file.separator");
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs,testSong,"group 1 7 Twinkle1","group 2 4 Twinkle");
        String expected = "{UPPER_A UPPER_B }{UPPER_A UPPER_B }{UPPER_A UPPER_B }{UPPER_A UPPER_B }|"
                + "{UPPER_B UPPER_C }{UPPER_B UPPER_C }{UPPER_B UPPER_C }{UPPER_B UPPER_C }|"
                + "{UPPER_C UPPER_D }{UPPER_C UPPER_D }{UPPER_C UPPER_D }{UPPER_C UPPER_D }|"
                + "{UPPER_D UPPER_B }{UPPER_D UPPER_B }{UPPER_D UPPER_B }{UPPER_D UPPER_B }|"
                + "{UPPER_E UPPER_C }{UPPER_E UPPER_C }{UPPER_E UPPER_C }{UPPER_E UPPER_C }|"
                + "{UPPER_F UPPER_D }{UPPER_F UPPER_D }{UPPER_F UPPER_D }{UPPER_F UPPER_D }|"
                + "{UPPER_G UPPER_B }{UPPER_G UPPER_B }{UPPER_G UPPER_B }{UPPER_G UPPER_B }|";
        OverlayGroupGroup tester = new OverlayGroupGroup("overlay_group_group Twinkle 2 Twinkle 1");
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
    //when not enough parameters are given
    public void testGroupGroupEmpty() {
        String fileDelimiter = System.getProperty("file.separator");
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs,testSong,"group 1 7 Twinkle1","group 2 4 Twinkle");
        OverlayGroupGroup tester = new OverlayGroupGroup("overlay_group_group ");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            assertEquals("overlay_group_group_format",e.getType());
            return;
        }
    }

    @Test
    //when the song does not exist
    public void testGroupGroupFail() {
        String fileDelimiter = System.getProperty("file.separator");
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs,testSong,"group 1 7 Twinkle1","group 2 4 Twinkle");
        OverlayGroupGroup tester = new OverlayGroupGroup("overlay_group_group Twinkle21 2 Twinkle 1");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {

            assertEquals("no_song",e.getType());
            return;
        }
    }


    @Test
    //when the index given is a string
    public void testGroupGroupFail1() {
        String fileDelimiter = System.getProperty("file.separator");
        String testSong = "Twinkle aminor 120 [[UAs],[UA],[UAs],[UA],[UAs],[UA],[UAs],[UA]] "
                + "[[UBs],[UB],[UBs],[UB],[UBs],[UB],[UBs],[UB]] "
                + "[[UCs],[UC],[UCs],[UC],[UCs],[UC],[UCs],[UC]] "
                + "[[UDs],[UD],[UDs],[UD],[UDs],[UD],[UDs],[UD]] "
                + "[[UEs],[UE],[UEs],[UE],[UEs],[UE],[UEs],[UE]] "
                + "[[UFs],[UF],[UFs],[UF],[UFs],[UF],[UFs],[UF]] "
                + "[[UGs],[UG],[UGs],[UG],[UGs],[UG],[UGs],[UG]] ";
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter + "data");
        Ui ui = new Ui();
        SongList songs = new SongList();
        createSong(ui, storage, songs,testSong,"group 1 7 Twinkle1","group 2 4 Twinkle");
        OverlayGroupGroup tester = new OverlayGroupGroup("overlay_group_group Twinkle fehukwehujk Twinkle 1");
        try {
            tester.execute(songs,ui,storage);
        } catch (ducats.DucatsException e) {
            System.out.print(e.getType());
            assertEquals("number_index",e.getType());
            return;
        }
    }

}
