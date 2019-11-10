package ducats.commands;

import ducats.Ducats;
import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OpenCommandTest {

    //@@author rohan-av
    private static Ui ui;
    private static SongList songList;
    private static Storage storage;

    @BeforeAll
    static void initialize() {
        ui = new Ui();
        songList = new SongList();
        Song twinkle = new Song("twinkle","C", 120);
        Song helloWorld = new Song("helloWorld", "A", 131);
        songList.add(twinkle);
        songList.add(helloWorld);
        storage = new Storage("dummy_filepath");
    }

    @Test
    void testExecute() throws DucatsException {
        OpenCommand command = new OpenCommand("open helloWorld");
        assertEquals(command.execute(songList, ui, storage), Ui.formatOpen(songList, 1));
        assertEquals(1, songList.getActiveIndex());
        OpenCommand resetCommand = new OpenCommand("open twinkle");
        assertEquals(resetCommand.execute(songList, ui, storage), Ui.formatOpen(songList, 0));
        OpenCommand invalidCommand = new OpenCommand("open typo");
        assertThrows(DucatsException.class, () -> invalidCommand.execute(songList, ui, storage));
    }

    @Test
    void testStartMetronome() {
        OpenCommand openCommand = new OpenCommand("placeholder");
        assertArrayEquals(new int[]{-1, -1, -1, -1}, openCommand.startMetronome());
    }

    @Test
    void testIsExit() {
        OpenCommand openCommand = new OpenCommand("placeholder");
        assertFalse(openCommand.isExit());
    }
}
