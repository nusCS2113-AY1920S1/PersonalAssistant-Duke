package duke.commands;

import duke.DukeException;
import duke.components.Bar;
import duke.components.Group;
import duke.components.Song;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AsciiCommandTest {

    //@@author Samuel787
    @Test
    public void testPrintBarAscii_validBars_success() {
        //test case 1
        try {
            Bar bar1 = new Bar(5, "4_LE 4_LD 4_LC 4_MC");
            String result = "UC:         \n"
                   + "UB:         \n"
                   + "UA:         \n"
                   + "UG: --------\n"
                   + "UF:         \n"
                   + "UE: --------\n"
                   + "UD:         \n"
                   + "MC: ------@-\n"
                   + "LB:         \n"
                   + "LA: --------\n"
                   + "LG:         \n"
                   + "LF: --------\n"
                   + "LE: @       \n"
                   + "LD: --@-    \n"
                   + "LC:     @   \n";
            assertEquals(result, AsciiCommand.printBarAscii(bar1));
        } catch (DukeException e) {
            fail();
        }

        //test case 2
        try {
            String result = "UC:         \n"
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
                   + "LC:         \n";
            Bar bar2 = new Bar(20, "2_UE 4_MC 4_RT");
            assertEquals(result, AsciiCommand.printBarAscii(bar2));
        } catch (DukeException e) {
            fail();
        }

        //test case 3
        try {
            String result = "UC:         \n"
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
                   + "LC:         \n";
            Bar bar3 = new Bar(0, "8_LA 8_MC 8_UB 8_UE 8_RT 8_UF 8_LB 8_LE");
            assertEquals(result, AsciiCommand.printBarAscii(bar3));
        } catch (DukeException e) {
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
                   + "LC:     @                     \n";
            assertEquals(result, AsciiCommand.printGroupAscii(testGroup1));
        } catch (DukeException e) {
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
                   + "LC:                            @                                  \n";
            assertEquals(result, AsciiCommand.printGroupAscii(testGroup2));
        } catch (DukeException e) {
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
                   + "LC:                           \n";
            assertEquals(result, AsciiCommand.printGroupAscii(testGroup3));
        } catch (DukeException e) {
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
            String result =
                    "UC:          @. @. @                    @              @                                     \n"
                   + "UB:          --------       -! -        --            ---                                    \n"
                   + "UA:                                       @          @                                   @ @ \n"
                   + "UG: --------|--------|--------|--------|----@---|--@-----|--------|--------|--------|--------\n"
                   + "UF:         |        |        |        |      @ |@       |        |        |        |        \n"
                   + "UE: --------|--------|--------|--------|--------|--------|--------|--------|--------|--------\n"
                   + "UD:         |        |        |        |        |        |        |      @ |        |        \n"
                   + "MC: @-^-@-^-|--------|%---^.--|@-^-@-^-|--------|--------|--------|----@---|--^---^-|--------\n"
                   + "LB:         |        |        |        |        |        |        |        |@   @   |        \n"
                   + "LA: --------|--------|--------|--------|--------|--------|--------|--@-----|--------|--------\n"
                   + "LG:         |        |        |        |        |        |        |@       |        |        \n"
                   + "LF: --------|--------|--------|--------|--------|--------|------@-|--------|--------|@-------\n"
                   + "LE:                                                           @                              \n"
                   + "LD:                                                        -@---                      -@-    \n"
                   + "LC:                                                       @                                  \n";
            assertEquals(result, AsciiCommand.printSongAscii(song));
        } catch (DukeException e) {
            fail();
        }
    }
}
