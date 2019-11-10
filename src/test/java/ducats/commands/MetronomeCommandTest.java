package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MetronomeCommandTest {

    //@@author rohan-av

    private static Ui ui;
    private static SongList songList;
    private static Storage storage;

    @BeforeAll
    static void initialize() {
        ui = new Ui();
        songList = new SongList();
        storage = new Storage("dummy_filepath");
    }

    @Test
    void testStartMetronome() throws DucatsException {
        MetronomeCommand metronomeCommand = new MetronomeCommand("metronome 10 120 4/4");
        metronomeCommand.execute(songList, ui, storage);
        assertArrayEquals(new int[]{10, 120, 4, 4}, metronomeCommand.startMetronome());
        MetronomeCommand badMetronomeCommand = new MetronomeCommand("metronome 10 120 44");
        assertThrows(DucatsException.class, () -> badMetronomeCommand.execute(songList, ui, storage));
    }

    @Test
    void testIsExit() {
        MetronomeCommand metronomeCommand = new MetronomeCommand("placeholder");
        assertFalse(metronomeCommand.isExit());
    }
}
