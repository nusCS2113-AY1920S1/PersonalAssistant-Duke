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

public class EditBarCommandTest {
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
        EditBarCommand editBarTest1 = new EditBarCommand("editbar 1 2_MC 2_MC");
        String testOutput1 = editBarTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "Got it. I've edited this bar:\n"
                + "  [[UAs],[UA],[UA],[UA],[UA],[UA],[UA],[UA]]\n"
                + "Now you have [[MCs],[MC],[MC],[MC],[MCs],[MC],[MC],[MC]] in the song dummy.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput1, testOutput1);

        EditBarCommand editBarTest2 = new EditBarCommand("editbar 2 2_LB 2_LB");
        String testOutput2 = editBarTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "Got it. I've edited this bar:\n"
                + "  [[UBs],[UB],[UB],[UB],[UB],[UB],[UB],[UB]]\n"
                + "Now you have [[LBs],[LB],[LB],[LB],[LBs],[LB],[LB],[LB]] in the song dummy.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput2, testOutput2);
    }

    @Test
    public void execute_emptyInput_exceptionThrown() throws DucatsException {
        EditBarCommand newTest3 = new EditBarCommand("editbar ");
        DucatsException testDucatsException3 = assertThrows(DucatsException.class, () -> {
            newTest3.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException3 = new DucatsException("editbar ", "edit");
        assertEquals(expectedDucatsException3.getMessage(), testDucatsException3.getMessage());
    }

    @Test
    public void execute_invalidBarNum_exceptionThrown() throws DucatsException {
        EditBarCommand newTest4 = new EditBarCommand("editbar random 2_MC 2_MC");
        DucatsException testDucatsException4 = assertThrows(DucatsException.class, () -> {
            newTest4.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException4 = new DucatsException("editbar random 2_MC 2_MC", "number_index");
        assertEquals(expectedDucatsException4.getMessage(), testDucatsException4.getMessage());

        EditBarCommand newTest5 = new EditBarCommand("editbar !@#$%^ 2_MC 2_MC");
        DucatsException testDucatsException5 = assertThrows(DucatsException.class, () -> {
            newTest5.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException5 = new DucatsException("editbar !@#$%^ 2_MC 2_MC", "number_index");
        assertEquals(expectedDucatsException5.getMessage(), testDucatsException5.getMessage());

        EditBarCommand newTest6 = new EditBarCommand("editbar  2_MC 2_MC");
        DucatsException testDucatsException6 = assertThrows(DucatsException.class, () -> {
            newTest6.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException6 = new DucatsException("editbar  2_MC 2_MC", "edit");
        assertEquals(expectedDucatsException6.getMessage(), testDucatsException6.getMessage());

        EditBarCommand newTest7 = new EditBarCommand("editbar 0 2_MC 2_MC");
        DucatsException testDucatsException7 = assertThrows(DucatsException.class, () -> {
            newTest7.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException7 = new DucatsException("", "index");
        assertEquals(expectedDucatsException7.getMessage(), testDucatsException7.getMessage());

        EditBarCommand newTest8 = new EditBarCommand("editbar -1 2_MC 2_MC");
        DucatsException testDucatsException8 = assertThrows(DucatsException.class, () -> {
            newTest8.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException8 = new DucatsException("", "index");
        assertEquals(expectedDucatsException8.getMessage(), testDucatsException8.getMessage());

        EditBarCommand newTest9 = new EditBarCommand("editbar 3 2_MC 2_MC");
        DucatsException testDucatsException9 = assertThrows(DucatsException.class, () -> {
            newTest9.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException9 = new DucatsException("editbar 3 2_MC 2_MC", "index");
        assertEquals(expectedDucatsException9.getMessage(), testDucatsException9.getMessage());
    }

    @Test
    public void execute_invalidBarContent_exceptionThrown() throws DucatsException {
        EditBarCommand newTest10 = new EditBarCommand("editbar 1 2_MC 4_MC");
        DucatsException testDucatsException10 = assertThrows(DucatsException.class, () -> {
            newTest10.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException10 = new DucatsException("editbar 1 2_MC 4_MC", "edit");
        assertEquals(expectedDucatsException10.getMessage(), testDucatsException10.getMessage());

        EditBarCommand newTest11 = new EditBarCommand("editbar 1 2_UA 4_MC 8_LC 16_LF 16_LD");
        DucatsException testDucatsException11 = assertThrows(DucatsException.class, () -> {
            newTest11.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException11 = new DucatsException("editbar 1 2_UA 4_MC 8_LC 16_LF 16_LD",
                                                                        "edit");
        assertEquals(expectedDucatsException11.getMessage(), testDucatsException11.getMessage());

        EditBarCommand newTest12 = new EditBarCommand("editbar 1 ");
        DucatsException testDucatsException12 = assertThrows(DucatsException.class, () -> {
            newTest12.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException12 = new DucatsException("", "index");
        assertEquals(expectedDucatsException12.getMessage(), testDucatsException12.getMessage());
    }

    @Test
    public void isExit_normalInput_success() {
        EditBarCommand editBarTest1 = new EditBarCommand("editbar 1 2_MC 2_MC");
        assertEquals(false, editBarTest1.isExit());
    }
}
