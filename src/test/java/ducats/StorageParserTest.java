package ducats;

import ducats.components.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StorageParserTest {

    static StorageParser storageParser;
    static Song song;
    static Group group;
    static Bar bar;
    static Chord chord;
    static Note note;

    @BeforeAll
    static void initialize() throws DucatsException {
        storageParser = new StorageParser();
        song = new Song("twinkle", "C", 120);
        bar = new Bar(0, "8_LC 8_LD 8_LE 8_LF 8_LG 8_LA 8_LB 8_MC");
        song.addBar(bar);
        ArrayList<Bar> bars = new ArrayList<>();
        bars.add(bar);
        group = new Group("test", bars);
        //TODO initialization of chord, note
    }

    @Test
    void testConvertSongFromString() throws DucatsException {
        ArrayList<String> lines = new ArrayList<>();
        assertThrows(DucatsException.class, () -> storageParser.convertSongFromString(lines));
        lines.add("twinkle C 120 ");
        lines.add(bar.toString());
        assertEquals(song.toString(), storageParser.convertSongFromString(lines).toString());
        Bar bar2 = new Bar(0, "8_UD 8_UE 8_UF 8_UG 8_UA 8_UB 8_UC 8_RT");
        song.addBar(bar2);
        lines.add(bar2.toString());
        song.createGroup("test", 2, 2);
        lines.add("groups:");
        lines.add("test " + bar2.toString());
        assertArrayEquals(storageParser.getArrayList(song).toArray(),
                storageParser.getArrayList(storageParser.convertSongFromString(lines)).toArray());
    }
}
