package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Song;
import ducats.components.SongList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewCommandTest {
    private static SongList dummySongList;
    private static Ui dummyUi;
    private static String dummyFileDelimiter;
    private static Storage dummyStorage;

    /**
     * The method to be executed before each test in the class to ensure that the dummy song list, ui,
     * file delimiter and storage is set up appropriately before testing the command.
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    @BeforeEach
    public void executedBeforeEach() throws DucatsException {
        try {
            dummySongList = new SongList();
            dummyUi = new Ui();
            dummyFileDelimiter = System.getProperty("file.separator");
            dummyStorage = new Storage(System.getProperty("user.dir") + dummyFileDelimiter + "test");

            Song dummySong1 = new Song("dummy1", "aminor", 120);
            Song dummySong2 = new Song("dummy2", "cmajor", 60);

            Bar dummyBar1 = new Bar(1, "1_UA");
            Bar dummyBar2 = new Bar(2, "1_UB");
            dummySong1.addBar(dummyBar1);
            dummySong2.addBar(dummyBar2);

            dummySongList.add(dummySong1);
            dummySongList.add(dummySong2);
            dummyStorage.updateFile(dummySongList);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_normalInput_success() throws DucatsException {
        ViewCommand viewTest1 = new ViewCommand("view dummy1");
        String testOutput1 = viewTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "UC:         \n"
                + "UB:         \n"
                + "UA: *       \n"
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
                + "LC:\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput1, testOutput1);

        ViewCommand listTest2 = new ViewCommand("view dummy2");
        String testOutput2 = listTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "UC:         \n"
                + "UB: *-      \n"
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
                + "LC:\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput2, testOutput2);
    }

    @Test
    public void execute_invalidSongName_exceptionThrown() throws DucatsException {
        ViewCommand newTest3 = new ViewCommand("view random");
        DucatsException testDucatsException3 = assertThrows(DucatsException.class, () -> {
            newTest3.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException3 = new DucatsException("view random", "view");
        assertEquals(expectedDucatsException3.getMessage(), testDucatsException3.getMessage());
    }

    @Test
    public void isExit_normalInput_success() {
        ViewCommand viewTest1 = new ViewCommand("view dummy1");
        assertEquals(false, viewTest1.isExit());
    }
}
