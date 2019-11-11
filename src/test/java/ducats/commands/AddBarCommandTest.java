package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AddBarCommandTest {
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

            Song dummySong = new Song("dummy", "aminor", 120);
            dummySongList.add(dummySong);
            dummyStorage.updateFile(dummySongList);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_normalInput_success() throws DucatsException {
        AddBarCommand addBarTest1 = new AddBarCommand("addbar 2_UA 4_MC 8_LC 8_LF");
        String testOutput1 = addBarTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "Got it. I've added this bar:\n"
                + "  [[UAs],[UA],[UA],[UA],[MCs],[MC],[LCs],[LFs]]\n"
                + "to dummy\n"
                + "Now you have 1 bar in the song.\n"
                + "_____________________________________________"
                + "\n";
        assertEquals(expectedOutput1, testOutput1);

        AddBarCommand addBarTest2 = new AddBarCommand("addbar 2*_UF 4_LD");
        String testOutput2 = addBarTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "Got it. I've added this bar:\n"
                + "  [[UFs],[UF],[UF],[UF],[UF],[UF],[LDs],[LD]]\n"
                + "to dummy\n"
                + "Now you have 2 bar in the song.\n"
                + "_____________________________________________"
                + "\n";
        assertEquals(expectedOutput2, testOutput2);
    }

    @Test
    public void execute_emptyInput_exceptionThrown() throws DucatsException {
        AddBarCommand newTest3 = new AddBarCommand("addbar ");
        DucatsException testDucatsException3 = assertThrows(DucatsException.class, () -> {
            newTest3.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException3 = new DucatsException("addbar ");
        assertEquals(expectedDucatsException3.getMessage(), testDucatsException3.getMessage());
    }

    @Test
    public void execute_invalidFullBarDuration_exceptionThrown() throws DucatsException {
        AddBarCommand newTest4 = new AddBarCommand("addbar 4_UA 4_UA 4_UA");
        DucatsException testDucatsException4 = assertThrows(DucatsException.class, () -> {
            newTest4.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException4 = new DucatsException("addbar 4_UA 4_UA 4_UA", "addbar");
        assertEquals(expectedDucatsException4.getMessage(), testDucatsException4.getMessage());
    }

    @Test
    public void execute_invalidSingleNoteDuration_exceptionThrown() throws DucatsException {
        AddBarCommand newTest5 = new AddBarCommand("addbar 2_UA 4_MC 8_LC 16_LF 16_LD");
        DucatsException testDucatsException5 = assertThrows(DucatsException.class, () -> {
            newTest5.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException5 = new DucatsException("addbar 4_UA 4_UA 4_UA", "addbar");
        assertEquals(expectedDucatsException5.getMessage(), testDucatsException5.getMessage());
    }

    @Test
    public void execute_invalidPitch_exceptionThrown() throws DucatsException {
        AddBarCommand newTest6 = new AddBarCommand("addbar 2_UA, 2_MA");
        DucatsException testDucatsException6 = assertThrows(DucatsException.class, () -> {
            newTest6.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException6 = new DucatsException("addbar 2_UA, 2_MA", "addbar");
        assertEquals(expectedDucatsException6.getMessage(), testDucatsException6.getMessage());
    }

    @Test
    public void isExit_normalInput_success() {
        AddBarCommand addBarTest1 = new AddBarCommand("addbar 2_UA 4_MC 8_LC 8_LF");
        assertEquals(false, addBarTest1.isExit());
    }
}
