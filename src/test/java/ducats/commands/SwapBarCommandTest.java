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

public class SwapBarCommandTest {
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
            Bar dummyBar3 = new Bar(3, "1_MC");
            dummySong.addBar(dummyBar1);
            dummySong.addBar(dummyBar2);
            dummySong.addBar(dummyBar3);

            dummySongList.add(dummySong);
            dummyStorage.updateFile(dummySongList);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_normalInput_success() throws DucatsException {
        SwapBarCommand swapBarTest1 = new SwapBarCommand("swapbar 1 2");
        String testOutput1 = swapBarTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "Got it. I've swap this bar:\n"
                + "  [[UAs],[UA],[UA],[UA],[UA],[UA],[UA],[UA]]\n"
                + " With this bar: [[UBs],[UB],[UB],[UB],[UB],[UB],[UB],[UB]] in the song dummy.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput1, testOutput1);


        SwapBarCommand swapBarTest2 = new SwapBarCommand("swapbar 2 3");
        String testOutput2 = swapBarTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "Got it. I've swap this bar:\n"
                + "  [[UAs],[UA],[UA],[UA],[UA],[UA],[UA],[UA]]\n"
                + " With this bar: [[MCs],[MC],[MC],[MC],[MC],[MC],[MC],[MC]] in the song dummy.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput2, testOutput2);

        SwapBarCommand swapBarTest3 = new SwapBarCommand("swapbar 3 1");
        String testOutput3 = swapBarTest3.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput3 = "\n" + "_____________________________________________\n"
                + "Got it. I've swap this bar:\n"
                + "  [[UAs],[UA],[UA],[UA],[UA],[UA],[UA],[UA]]\n"
                + " With this bar: [[UBs],[UB],[UB],[UB],[UB],[UB],[UB],[UB]] in the song dummy.\n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput3, testOutput3);
    }

    @Test
    public void execute_emptyInput_exceptionThrown() throws DucatsException {
        SwapBarCommand newTest10 = new SwapBarCommand("swapbar ");
        DucatsException testDucatsException10 = assertThrows(DucatsException.class, () -> {
            newTest10.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException10 = new DucatsException("swapbar ", "swap");
        assertEquals(expectedDucatsException10.getMessage(), testDucatsException10.getMessage());
    }

    @Test
    public void execute_invalidBarInput_exceptionThrown() throws DucatsException {
        SwapBarCommand newTest4 = new SwapBarCommand("swapbar random1 random2");
        DucatsException testDucatsException4 = assertThrows(DucatsException.class, () -> {
            newTest4.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException4 = new DucatsException("swapbar random1 random2", "swap");
        assertEquals(expectedDucatsException4.getMessage(), testDucatsException4.getMessage());

        SwapBarCommand newTest5 = new SwapBarCommand("swapbar $%^ !@#");
        DucatsException testDucatsException5 = assertThrows(DucatsException.class, () -> {
            newTest5.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException5 = new DucatsException("swapbar $%^ !@#", "swap");
        assertEquals(expectedDucatsException5.getMessage(), testDucatsException5.getMessage());

        SwapBarCommand newTest6 = new SwapBarCommand("swapbar  1");
        DucatsException testDucatsException6 = assertThrows(DucatsException.class, () -> {
            newTest6.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException6 = new DucatsException("swapbar  1", "swap");
        assertEquals(expectedDucatsException6.getMessage(), testDucatsException6.getMessage());
    }

    @Test
    public void execute_invalidBarNum_exceptionThrown() throws DucatsException {
        SwapBarCommand newTest7 = new SwapBarCommand("swapbar 0 1");
        DucatsException testDucatsException7 = assertThrows(DucatsException.class, () -> {
            newTest7.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException7 = new DucatsException("swapbar 0 1", "swap");
        assertEquals(expectedDucatsException7.getMessage(), testDucatsException7.getMessage());

        SwapBarCommand newTest8 = new SwapBarCommand("swapbar -1 1");
        DucatsException testDucatsException8 = assertThrows(DucatsException.class, () -> {
            newTest8.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException8 = new DucatsException("swapbar -1 1", "swap");
        assertEquals(expectedDucatsException8.getMessage(), testDucatsException8.getMessage());

        SwapBarCommand newTest9 = new SwapBarCommand("swapbar 4 1");
        DucatsException testDucatsException9 = assertThrows(DucatsException.class, () -> {
            newTest9.execute(dummySongList, dummyUi, dummyStorage);
        });
        DucatsException expectedDucatsException9 = new DucatsException("swapbar 4 1", "swap");
        assertEquals(expectedDucatsException9.getMessage(), testDucatsException9.getMessage());
    }

    @Test
    public void isExit_normalInput_success() {
        SwapBarCommand swapBarTest1 = new SwapBarCommand("swapbar 1 2");
        assertEquals(false, swapBarTest1.isExit());
    }
}
