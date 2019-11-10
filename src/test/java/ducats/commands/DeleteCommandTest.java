package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteCommandTest {
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

            dummySongList.add(dummySong1);
            dummySongList.add(dummySong2);
            dummyStorage.updateFile(dummySongList);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_normalInput_success() throws DucatsException {
        DeleteCommand deleteTest1 = new DeleteCommand("delete dummy2");
        String testOutput1 = deleteTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "Noted! I've removed this song:\n"
                + " dummy2\n"
                + "Now you have 1 song in the SongList.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput1, testOutput1);

        DeleteCommand deleteTest2 = new DeleteCommand("delete song:1");
        String testOutput2 = deleteTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "Noted! I've removed this song:\n"
                + " dummy1\n"
                + "Now you have 0 songs in the SongList.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput2, testOutput2);
    }

    @Test
    public void execute_emptyInput_exceptionThrown() throws DucatsException {
        DeleteCommand newTest3 = new DeleteCommand("delete ");
        DucatsException testDucatsException3 = assertThrows(DucatsException.class, () -> {
            newTest3.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException3 = new DucatsException("delete ");
        assertEquals(expectedDucatsException3.getMessage(), testDucatsException3.getMessage());
    }

    @Test
    public void execute_invalidSongInput_exceptionThrown() throws DucatsException {
        DeleteCommand newTest4 = new DeleteCommand("delete random");
        DucatsException testDucatsException4 = assertThrows(DucatsException.class, () -> {
            newTest4.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException4 = new DucatsException("", "index");
        assertEquals(expectedDucatsException4.getMessage(), testDucatsException4.getMessage());

        DeleteCommand newTest5 = new DeleteCommand("delete song:0");
        DucatsException testDucatsException5 = assertThrows(DucatsException.class, () -> {
            newTest5.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException5 = new DucatsException("", "index");
        assertEquals(expectedDucatsException5.getMessage(), testDucatsException5.getMessage());

        DeleteCommand newTest6 = new DeleteCommand("delete song:-1");
        DucatsException testDucatsException6 = assertThrows(DucatsException.class, () -> {
            newTest6.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException6 = new DucatsException("", "index");
        assertEquals(expectedDucatsException6.getMessage(), testDucatsException6.getMessage());

        DeleteCommand newTest7 = new DeleteCommand("delete song:3");
        DucatsException testDucatsException7 = assertThrows(DucatsException.class, () -> {
            newTest7.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException7 = new DucatsException("", "index");
        assertEquals(expectedDucatsException7.getMessage(), testDucatsException7.getMessage());
    }

    @Test
    public void isExit_normalInput_success() throws DucatsException {
        DeleteCommand deleteTest1 = new DeleteCommand("delete dummy2");
        assertEquals(false, deleteTest1.isExit());
    }
}
