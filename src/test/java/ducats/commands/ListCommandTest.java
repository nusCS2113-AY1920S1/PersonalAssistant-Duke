package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {
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
        dummySongList = new SongList();
        dummyUi = new Ui();
        dummyFileDelimiter = System.getProperty("file.separator");
        dummyStorage = new Storage(System.getProperty("user.dir") + dummyFileDelimiter + "test");

        Song dummySong1 = new Song("dummy1", "aminor", 120);
        Song dummySong2 = new Song("dummy2", "cmajor", 60);

        dummySongList.add(dummySong1);
        dummySongList.add(dummySong2);
        dummyStorage.updateFile(dummySongList);
    }

    @Test
    public void execute_normalInput_success() throws DucatsException {
        ListCommand listTest1 = new ListCommand();
        String testOutput1 = listTest1.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput1 = "\n" + "_____________________________________________\n"
                + "1. dummy1 aminor 120 \n"
                + "2. dummy2 cmajor 60 \n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput1, testOutput1);

        Song dummySong3 = new Song("dummy3", "fmajor", 80);
        dummySongList.add(dummySong3);
        dummyStorage.updateFile(dummySongList);
        ListCommand listTest2 = new ListCommand();
        String testOutput2 = listTest2.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput2 = "\n" + "_____________________________________________\n"
                + "1. dummy1 aminor 120 \n"
                + "2. dummy2 cmajor 60 \n"
                + "3. dummy3 fmajor 80 \n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput2, testOutput2);

        dummySongList.remove(1);
        dummyStorage.updateFile(dummySongList);
        ListCommand listTest3 = new ListCommand();
        String testOutput3 = listTest3.execute(dummySongList, dummyUi, dummyStorage);
        String expectedOutput3 = "\n" + "_____________________________________________\n"
                + "1. dummy1 aminor 120 \n"
                + "2. dummy3 fmajor 80 \n"
                + "_____________________________________________\n";
        assertEquals(expectedOutput3, testOutput3);
    }

}
