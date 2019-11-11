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

public class DeleteBarCommandTest {
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

            Song dummySong = new Song("dummy", "c", 120);
            Bar dummyBar1 = new Bar(1, "1_UA");
            Bar dummyBar2 = new Bar(2, "1_UB");
            dummySong.addBar(dummyBar1);
            dummySong.addBar(dummyBar2);

            dummySongList.add(dummySong);
            dummyStorage.updateFile(dummySongList);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_normalInput_success() throws DucatsException {
        DeleteBarCommand deleteBarTest1 = new DeleteBarCommand("deletebar 1");
        String testOutput1 = deleteBarTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "Noted! I've removed bar: 1\n"
                + "Now you have 1 bar in the song.\n"
                + "_____________________________________________"
                + "\n";
        assertEquals(expectedOutput1, testOutput1);

        DeleteBarCommand deleteBarTest2 = new DeleteBarCommand("deletebar 1");
        String testOutput2 = deleteBarTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "Noted! I've removed bar: 1\n"
                + "Now you have 0 bars in the song.\n"
                + "_____________________________________________"
                + "\n";
        assertEquals(expectedOutput2, testOutput2);
    }

    @Test
    public void execute_emptyInput_exceptionThrown() throws DucatsException {
        DeleteBarCommand newTest3 = new DeleteBarCommand("deletebar ");
        DucatsException testDucatsException3 = assertThrows(DucatsException.class, () -> {
            newTest3.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException3 = new DucatsException("deletebar ");
        assertEquals(expectedDucatsException3.getMessage(), testDucatsException3.getMessage());
    }

    @Test
    public void execute_invalidBarInput_exceptionThrown() throws DucatsException {
        DeleteBarCommand newTest4 = new DeleteBarCommand("deletebar random");
        DucatsException testDucatsException4 = assertThrows(DucatsException.class, () -> {
            newTest4.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException4 = new DucatsException("", "number_index");
        assertEquals(expectedDucatsException4.getMessage(), testDucatsException4.getMessage());

        DeleteBarCommand newTest5 = new DeleteBarCommand("deletebar !@#$%^");
        DucatsException testDucatsException5 = assertThrows(DucatsException.class, () -> {
            newTest5.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException5 = new DucatsException("", "number_index");
        assertEquals(expectedDucatsException5.getMessage(), testDucatsException5.getMessage());

        DeleteBarCommand newTest6 = new DeleteBarCommand("deletebar 1_UA");
        DucatsException testDucatsException6 = assertThrows(DucatsException.class, () -> {
            newTest6.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException6 = new DucatsException("", "number_index");
        assertEquals(expectedDucatsException6.getMessage(), testDucatsException6.getMessage());
    }

    @Test
    public void execute_invalidBarNum_exceptionThrown() throws DucatsException {
        DeleteBarCommand newTest7 = new DeleteBarCommand("deletebar 0");
        DucatsException testDucatsException7 = assertThrows(DucatsException.class, () -> {
            newTest7.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException7 = new DucatsException("", "index");
        assertEquals(expectedDucatsException7.getMessage(), testDucatsException7.getMessage());

        DeleteBarCommand newTest8 = new DeleteBarCommand("deletebar -1");
        DucatsException testDucatsException8 = assertThrows(DucatsException.class, () -> {
            newTest8.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException8 = new DucatsException("", "index");
        assertEquals(expectedDucatsException8.getMessage(), testDucatsException8.getMessage());

        DeleteBarCommand newTest9 = new DeleteBarCommand("deletebar 3");
        DucatsException testDucatsException9 = assertThrows(DucatsException.class, () -> {
            newTest9.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException9 = new DucatsException("", "index");
        assertEquals(expectedDucatsException9.getMessage(), testDucatsException9.getMessage());
    }

    @Test
    public void isExit_normalInput_success() throws DucatsException {
        DeleteBarCommand deleteBarTest1 = new DeleteBarCommand("deletebar 1");
        assertEquals(false, deleteBarTest1.isExit());
    }
}
