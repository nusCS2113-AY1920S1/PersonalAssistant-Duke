package ducats.commands;

import ducats.Ducats;
import ducats.DucatsException;
import ducats.Ui;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.Bar;

import ducats.components.SongList;
import ducats.components.SongConverter;


import org.junit.jupiter.api.Test;


import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class AsciiCommandTest {

    //@@author Samuel787
    @Test
    public void testPrintBarAscii_validBar_success() {
        String result = "";
        try {
            result = AsciiCommand.printBarAscii(new Bar(1, "4*_UC 4*_UC 4_UC"));
        } catch (DucatsException e) {
            System.out.println(e);
            fail();
        }
        String expectedResult = "UC: @. @. @ \n"
                            + "UB: --------\n"
                            + "UA:         \n"
                            + "UG: --------\n"
                            + "UF:         \n"
                            + "UE: --------\n"
                            + "UD:         \n"
                            + "MC: --------\n"
                            + "LB:         \n"
                            + "LA: --------\n"
                            + "LG:         \n"
                            + "LF: --------\n"
                            + "LE:         \n"
                            + "LD:         \n"
                            + "LC:         \n"
                            + "\n";
        assertEquals(expectedResult, result);

        //test case 2
        try {
            String result2 = "UC:         \n"
                    + "UB:         \n"
                    + "UA:         \n"
                    + "UG: --------\n"
                    + "UF:         \n"
                    + "UE: $-------\n"
                    + "UD:         \n"
                    + "MC: ----@-^-\n"
                    + "LB:         \n"
                    + "LA: --------\n"
                    + "LG:         \n"
                    + "LF: --------\n"
                    + "LE:         \n"
                    + "LD:         \n"
                    + "LC:         \n\n";
            Bar bar2 = new Bar(20, "2_UE 4_MC 4_RT");
            assertEquals(result2, AsciiCommand.printBarAscii(bar2));
        } catch (DucatsException e) {
            fail();
        }

        //test case 3
        try {
            String result3 = "UC:         \n"
                    + "UB:  -!-    \n"
                    + "UA:         \n"
                    + "UG: --------\n"
                    + "UF:      !  \n"
                    + "UE: ---!----\n"
                    + "UD:         \n"
                    + "MC: -!--&---\n"
                    + "LB:       ! \n"
                    + "LA: !-------\n"
                    + "LG:         \n"
                    + "LF: --------\n"
                    + "LE:        !\n"
                    + "LD:       --\n"
                    + "LC:         \n\n";
            Bar bar3 = new Bar(0, "8_LA 8_MC 8_UB 8_UE 8_RT 8_UF 8_LB 8_LE");
            assertEquals(result3, AsciiCommand.printBarAscii(bar3));
        } catch (DucatsException e) {
            fail();
        }

    }


    @Test
    public void testPrintGroupAscii_validGroups_success() {
        //test case 1
        try {
            ArrayList<Bar> barsGroup1 = new ArrayList<>();
            barsGroup1.add(new Bar(0, "4_LE 4_LD 4_LC 4_MC"));
            barsGroup1.add(new Bar(1, "2_UE 4_MC 4_RT"));
            barsGroup1.add(new Bar(2, "8_LA 8_MC 8_UB 8_UE 8_RT 8_UF 8_LB 8_LE"));
            Group testGroup1 = new Group("testGroup1", barsGroup1);
            String result = "UC:                           \n"
                    + "UB:                    -!-    \n"
                    + "UA:                           \n"
                    + "UG: --------|--------|--------\n"
                    + "UF:         |        |     !  \n"
                    + "UE: --------|$-------|---!----\n"
                    + "UD:         |        |        \n"
                    + "MC: ------@-|----@-^-|-!--&---\n"
                    + "LB:         |        |      ! \n"
                    + "LA: --------|--------|!-------\n"
                    + "LG:         |        |        \n"
                    + "LF: --------|--------|--------\n"
                    + "LE: @                        !\n"
                    + "LD: --@-                    --\n"
                    + "LC:     @                     \n\n";
            assertEquals(result, AsciiCommand.printGroupAscii(testGroup1));
        } catch (DucatsException e) {
            fail();
        }

        //test case 2
        try {
            ArrayList<Bar> barsGroup2 = new ArrayList<>();
            barsGroup2.add(new Bar(0, "4_MC 4_RT 4_MC 4_RT"));
            barsGroup2.add(new Bar(0, "4_UC 4_UA 4_UG 4_UF"));
            barsGroup2.add(new Bar(0, "4_UF 4_UG 4_UA 4_UC"));
            barsGroup2.add(new Bar(0, "4_LC 4_LD 4_LE 4_LF"));
            barsGroup2.add(new Bar(0, "4_LG 4_LA 4_MC 4_UD"));
            barsGroup2.add(new Bar(0, "4_LB 4_RT 4_LB 4_RT"));
            barsGroup2.add(new Bar(0, "4_LF 4_LD 4_UA 4_UA"));
            Group testGroup2 = new Group("testGroup2", barsGroup2);

            String result = "UC:          @              @                                     \n"
                    + "UB:          --            ---                                    \n"
                    + "UA:            @          @                                   @ @ \n"
                    + "UG: --------|----@---|--@-----|--------|--------|--------|--------\n"
                    + "UF:         |      @ |@       |        |        |        |        \n"
                    + "UE: --------|--------|--------|--------|--------|--------|--------\n"
                    + "UD:         |        |        |        |      @ |        |        \n"
                    + "MC: @-^-@-^-|--------|--------|--------|----@---|--^---^-|--------\n"
                    + "LB:         |        |        |        |        |@   @   |        \n"
                    + "LA: --------|--------|--------|--------|--@-----|--------|--------\n"
                    + "LG:         |        |        |        |@       |        |        \n"
                    + "LF: --------|--------|--------|------@-|--------|--------|@-------\n"
                    + "LE:                                @                              \n"
                    + "LD:                             -@---                      -@-    \n"
                    + "LC:                            @                                  \n\n";
            assertEquals(result, AsciiCommand.printGroupAscii(testGroup2));
        } catch (DucatsException e) {
            fail();
        }

        //test case 3
        try {
            ArrayList<Bar> barsGroup3 = new ArrayList<>();
            barsGroup3.add(new Bar(0, "4_MC 4_RT 4_MC 4_RT"));
            barsGroup3.add(new Bar(1, "4*_UC 4*_UC 4_UC"));
            barsGroup3.add(new Bar(2, "2_RT 4*_RT 8_UB"));
            Group testGroup3 = new Group("testGroup3", barsGroup3);
            String result = "UC:          @. @. @          \n"
                    + "UB:          --------       -!\n"
                    + "UA:                           \n"
                    + "UG: --------|--------|--------\n"
                    + "UF:         |        |        \n"
                    + "UE: --------|--------|--------\n"
                    + "UD:         |        |        \n"
                    + "MC: @-^-@-^-|--------|%---^.--\n"
                    + "LB:         |        |        \n"
                    + "LA: --------|--------|--------\n"
                    + "LG:         |        |        \n"
                    + "LF: --------|--------|--------\n"
                    + "LE:                           \n"
                    + "LD:                           \n"
                    + "LC:                           \n\n";
            assertEquals(result, AsciiCommand.printGroupAscii(testGroup3));
        } catch (DucatsException e) {
            fail();
        }


    }

    @Test
    public void testPrintSongAscii_validGroups_success() {
        //test case 1
        try {
            Song song = new Song("Winnie the Pooh", "C-Major", 120);
            song.addBar(new Bar(0, "4_MC 4_RT 4_MC 4_RT"));
            song.addBar(new Bar(1, "4*_UC 4*_UC 4_UC"));
            song.addBar(new Bar(2, "2_RT 4*_RT 8_UB"));
            song.addBar(new Bar(0, "4_MC 4_RT 4_MC 4_RT"));
            song.addBar(new Bar(0, "4_UC 4_UA 4_UG 4_UF"));
            song.addBar(new Bar(0, "4_UF 4_UG 4_UA 4_UC"));
            song.addBar(new Bar(0, "4_LC 4_LD 4_LE 4_LF"));
            song.addBar(new Bar(0, "4_LG 4_LA 4_MC 4_UD"));
            song.addBar(new Bar(0, "4_LB 4_RT 4_LB 4_RT"));
            song.addBar(new Bar(0, "4_LF 4_LD 4_UA 4_UA"));
            String result = "UC:          @. @. @                    @              @                    \n"
                    + "UB:          --------       -! -        --            ---                   \n"
                    + "UA:                                       @          @                      \n"
                    + "UG: --------|--------|--------|--------|----@---|--@-----|--------|--------|\n"
                    + "UF:         |        |        |        |      @ |@       |        |        |\n"
                    + "UE: --------|--------|--------|--------|--------|--------|--------|--------|\n"
                    + "UD:         |        |        |        |        |        |        |      @ |\n"
                    + "MC: @-^-@-^-|--------|%---^.--|@-^-@-^-|--------|--------|--------|----@---|\n"
                    + "LB:         |        |        |        |        |        |        |        |\n"
                    + "LA: --------|--------|--------|--------|--------|--------|--------|--@-----|\n"
                    + "LG:         |        |        |        |        |        |        |@       |\n"
                    + "LF: --------|--------|--------|--------|--------|--------|------@-|--------|\n"
                    + "LE:                                                           @             \n"
                    + "LD:                                                        -@---            \n"
                    + "LC:                                                       @                 \n"
                    + "\n"
                    + "UC:                  \n"
                    + "UB:                  \n"
                    + "UA:              @ @ \n"
                    + "UG: --------|--------\n"
                    + "UF:         |        \n"
                    + "UE: --------|--------\n"
                    + "UD:         |        \n"
                    + "MC: --^---^-|--------\n"
                    + "LB: @   @   |        \n"
                    + "LA: --------|--------\n"
                    + "LG:         |        \n"
                    + "LF: --------|@-------\n"
                    + "LE:                  \n"
                    + "LD:           -@-    \n"
                    + "LC:                  \n"
                    + "\n";
            assertEquals(result, AsciiCommand.printSongAscii(song));
        } catch (DucatsException e) {
            fail();
        }
    }

    @Test
    public void testExecute_validBarOfSong_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter
                + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs],"
                        + "[UE],"
                        + "[UDs],[UD],[LGs],[LG]] "
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

        String expected = "UC:   @     \n"
                        + "UB:  ---    \n"
                        + "UA: @       \n"
                        + "UG: --------\n"
                        + "UF:         \n"
                        + "UE: --------\n"
                        + "UD:         \n"
                        + "MC: --------\n"
                        + "LB:     @   \n"
                        + "LA: ------@-\n"
                        + "LG:         \n"
                        + "LF: --------\n"
                        + "LE:         \n"
                        + "LD:         \n"
                        + "LC:         \n"
                        + "\n";

        AsciiCommand asciiCommand = new AsciiCommand("ascii bar 1");

        String result = "";
        try {
            result = asciiCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }

        assertEquals(expected, result);
    }

    @Test
    public void testExecute_invalidBarOfSong_noIndexException() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter
                + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs],"
                        + "[UE],"
                        + "[UDs],[UD],[LGs],[LG]] "
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

        String expected = "UC:   @     \n"
                + "UB:  ---    \n"
                + "UA: @       \n"
                + "UG: --------\n"
                + "UF:         \n"
                + "UE: --------\n"
                + "UD:         \n"
                + "MC: --------\n"
                + "LB:     @   \n"
                + "LA: ------@-\n"
                + "LG:         \n"
                + "LF: --------\n"
                + "LE:         \n"
                + "LD:         \n"
                + "LC:         \n"
                + "\n";

        AsciiCommand asciiCommand = new AsciiCommand("ascii bar 100");

        String result = "";
        try {
            result = asciiCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("no_index", e.getType());
            return;
        }
        assert false;
        assertEquals(expected, result);
    }

    @Test
    public void testExecute_validGroupOfSong_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter
                + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs],"
                        + "[UE],"
                        + "[UDs],[UD],[LGs],[LG]] "
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

        GroupCommand group1 = new GroupCommand("group 1 2 test");
        try {
            group1.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        String expected = "UC:   @              \n"
                        + "UB:  ---             \n"
                        + "UA: @                \n"
                        + "UG: --------|--------\n"
                        + "UF:         |@       \n"
                        + "UE: --------|--@-----\n"
                        + "UD:         |    @   \n"
                        + "MC: --------|--------\n"
                        + "LB:     @   |        \n"
                        + "LA: ------@-|--------\n"
                        + "LG:         |      @ \n"
                        + "LF: --------|--------\n"
                        + "LE:                  \n"
                        + "LD:                  \n"
                        + "LC:                  \n"
                        + "\n";

        AsciiCommand asciiCommand = new AsciiCommand("ascii group test");

        String result = "";
        try {
            result = asciiCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }

        assertEquals(expected, result);
    }


    @Test
    public void testExecute_invalidGroupOfSong_groupNotFoundException() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir") + fileDelimiter
                + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs],"
                        + "[UE],"
                        + "[UDs],[UD],[LGs],[LG]] "
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

        GroupCommand group1 = new GroupCommand("group 1 2 test");
        try {
            group1.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
        }

        String expected = "UC:   @              \n"
                + "UB:  ---             \n"
                + "UA: @                \n"
                + "UG: --------|--------\n"
                + "UF:         |@       \n"
                + "UE: --------|--@-----\n"
                + "UD:         |    @   \n"
                + "MC: --------|--------\n"
                + "LB:     @   |        \n"
                + "LA: ------@-|--------\n"
                + "LG:         |      @ \n"
                + "LF: --------|--------\n"
                + "LE:                  \n"
                + "LD:                  \n"
                + "LC:                  \n"
                + "\n";

        AsciiCommand asciiCommand = new AsciiCommand("ascii group mellow");

        String result = "";
        try {
            result = asciiCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("group_not_found", e.getType());
            return;
        }
        assert false;
        assertEquals(expected, result);
    }


    @Test
    public void testExecute_validSong_success() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir")
                + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs],"
                        + "[UE],"
                        + "[UDs],[UD],[LGs],[LG]] "
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

        String expected = "UC:   @                       \n"
                        + "UB:  ---                      \n"
                        + "UA: @                         \n"
                        + "UG: --------|--------|--------\n"
                        + "UF:         |@       |        \n"
                        + "UE: --------|--@-----|--------\n"
                        + "UD:         |    @   |        \n"
                        + "MC: --------|--------|$-------\n"
                        + "LB:     @   |        |        \n"
                        + "LA: ------@-|--------|--------\n"
                        + "LG:         |      @ |        \n"
                        + "LF: --------|--------|--------\n"
                        + "LE:                           \n"
                        + "LD:                           \n"
                        + "LC:                       $   \n"
                        + "\n";

        AsciiCommand asciiCommand = new AsciiCommand("ascii song Jingle");

        String result = "";
        try {
            result = asciiCommand.execute(songs, ui, storage);
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }

        assertEquals(expected, result);
    }

    @Test
    public void testExecute_invalidSong_noSongException() {
        String fileDelimiter = System.getProperty("file.separator");
        SongConverter songconverter = new SongConverter();
        ducats.Storage storage = new ducats.Storage(System.getProperty("user.dir")
                + fileDelimiter + "songlist.txt");

        SongList songs = new SongList();
        String testSong =
                "Jingle aminor 120 [[UAs],[UA],[UCs],[UC],[LBs],[LB],[LAs],[LA]] "
                        + "[[UFs],[UF],[UEs],"
                        + "[UE],"
                        + "[UDs],[UD],[LGs],[LG]] "
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

        String expected = "UC:   @                       \n"
                + "UB:  ---                      \n"
                + "UA: @                         \n"
                + "UG: --------|--------|--------\n"
                + "UF:         |@       |        \n"
                + "UE: --------|--@-----|--------\n"
                + "UD:         |    @   |        \n"
                + "MC: --------|--------|$-------\n"
                + "LB:     @   |        |        \n"
                + "LA: ------@-|--------|--------\n"
                + "LG:         |      @ |        \n"
                + "LF: --------|--------|--------\n"
                + "LE:                           \n"
                + "LD:                           \n"
                + "LC:                       $   \n"
                + "\n";

        AsciiCommand asciiCommand = new AsciiCommand("ascii song Twinkle");

        String result = "";
        try {
            result = asciiCommand.execute(songs, ui, storage);
        } catch (DucatsException e) {
            System.out.println(e);
            assertEquals("no_song", e.getType());
            return;
        }
        assert false;
        assertEquals(expected, result);
    }



    //@@author rohan-av
    @Test
    void testStartMetronome() {
        AsciiCommand asciiCommand = new AsciiCommand("ascii song blank");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, asciiCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        AsciiCommand asciiCommand = new AsciiCommand("ascii song blank");
        assertFalse(asciiCommand.isExit());
    }
}
