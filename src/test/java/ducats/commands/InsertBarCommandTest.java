package ducats.commands;

import ducats.DucatsException;
import ducats.Parser;
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

public class InsertBarCommandTest {
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
            Bar dummyBar2 = new Bar(2, "1_MC");
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
        InsertBarCommand insertBarTest1 = new InsertBarCommand("insertbar 3 2_UB 2_UB");
        String testOutput1 = insertBarTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "Got it. I've inserted this bar:\n"
                + "  [[UBs],[UB],[UB],[UB],[UBs],[UB],[UB],[UB]]\n"
                + "to dummy\n"
                + "Now you have 3 bar in the song.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput1, testOutput1);

        InsertBarCommand insertBarTest2 = new InsertBarCommand("insertbar 2 1_LD");
        String testOutput2 = insertBarTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "Got it. I've inserted this bar:\n"
                + "  [[LDs],[LD],[LD],[LD],[LD],[LD],[LD],[LD]]\n"
                + "to dummy\n"
                + "Now you have 4 bar in the song.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput2, testOutput2);

        InsertBarCommand insertBarTest3 = new InsertBarCommand("insertbar 1 1_LE");
        String testOutput3 = insertBarTest3.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput3 = "\n" + "_____________________________________________\n"
                + "Got it. I've inserted this bar:\n"
                + "  [[LEs],[LE],[LE],[LE],[LE],[LE],[LE],[LE]]\n"
                + "to dummy\n"
                + "Now you have 5 bar in the song.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput3, testOutput3);
    }

    @Test
    public void execute_emptyInput_exceptionThrown() throws DucatsException {
        DucatsException testDucatsException13 = assertThrows(DucatsException.class, () -> {
            Command newTest13 = Parser.parse(dummyUi, "insertbar ");
        });
        DucatsException expectedDucatsException13 = new DucatsException("insertbar ", "insertbar");
        assertEquals(expectedDucatsException13.getMessage(), testDucatsException13.getMessage());
    }

    @Test
    public void execute_invalidBarNum_exceptionThrown() throws DucatsException {
        InsertBarCommand newTest4 = new InsertBarCommand("insertbar random 2_MC 2_MC");
        DucatsException testDucatsException4 = assertThrows(DucatsException.class, () -> {
            newTest4.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException4 = new DucatsException("", "number_index");
        assertEquals(expectedDucatsException4.getMessage(), testDucatsException4.getMessage());

        InsertBarCommand newTest5 = new InsertBarCommand("insertbar !@#$%^ 2_MC 2_MC");
        DucatsException testDucatsException5 = assertThrows(DucatsException.class, () -> {
            newTest5.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException5 = new DucatsException("", "number_index");
        assertEquals(expectedDucatsException5.getMessage(), testDucatsException5.getMessage());

        InsertBarCommand newTest6 = new InsertBarCommand("insertbar  2_MC 2_MC");
        DucatsException testDucatsException6 = assertThrows(DucatsException.class, () -> {
            newTest6.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException6 = new DucatsException("insertbar  2_MC 2_MC", "insertbar");
        assertEquals(expectedDucatsException6.getMessage(), testDucatsException6.getMessage());

        InsertBarCommand newTest7 = new InsertBarCommand("insertbar 0 2_MC 2_MC");
        DucatsException testDucatsException7 = assertThrows(DucatsException.class, () -> {
            newTest7.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException7 = new DucatsException("", "index");
        assertEquals(expectedDucatsException7.getMessage(), testDucatsException7.getMessage());

        InsertBarCommand newTest8 = new InsertBarCommand("insertbar -1 2_MC 2_MC");
        DucatsException testDucatsException8 = assertThrows(DucatsException.class, () -> {
            newTest8.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException8 = new DucatsException("", "index");
        assertEquals(expectedDucatsException8.getMessage(), testDucatsException8.getMessage());

        InsertBarCommand newTest9 = new InsertBarCommand("insertbar 4 2_MC 2_MC");
        DucatsException testDucatsException9 = assertThrows(DucatsException.class, () -> {
            newTest9.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException9 = new DucatsException("", "index");
        assertEquals(expectedDucatsException9.getMessage(), testDucatsException9.getMessage());
    }

    @Test
    public void execute_invalidBarContent_exceptionThrown() throws DucatsException {
        InsertBarCommand newTest10 = new InsertBarCommand("insertbar 1 2_MC 4_MC");
        DucatsException testDucatsException10 = assertThrows(DucatsException.class, () -> {
            newTest10.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException10 = new DucatsException("insertbar 1 2_MC 4_MC", "insertbar");
        assertEquals(expectedDucatsException10.getMessage(), testDucatsException10.getMessage());

        InsertBarCommand newTest11 = new InsertBarCommand("insertbar 1 2_UA 4_MC 8_LC 16_LF 16_LD");
        DucatsException testDucatsException11 = assertThrows(DucatsException.class, () -> {
            newTest11.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException11 = new DucatsException("insertbar 1 2_UA 4_MC 8_LC 16_LF 16_LD",
                "insertbar");
        assertEquals(expectedDucatsException11.getMessage(), testDucatsException11.getMessage());

        InsertBarCommand newTest12 = new InsertBarCommand("insertbar 1 ");
        DucatsException testDucatsException12 = assertThrows(DucatsException.class, () -> {
            newTest12.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException12 = new DucatsException("", "index");
        assertEquals(expectedDucatsException12.getMessage(), testDucatsException12.getMessage());
    }

    @Test
    public void isExit_normalInput_success() {
        InsertBarCommand insertBarTest1 = new InsertBarCommand("insertbar 3 2_UB 2_UB");
        assertEquals(false, insertBarTest1.isExit());
    }
}
