package TriviaTest;
import gazeeebo.Storage.Storage;
import gazeeebo.TriviaManager.TriviaManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TriviaManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    //private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        //System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        //System.setErr(originalErr);
    }
    @Test
    public void testLearnInput() throws IOException {
        String testinput = "Love Qinhuai River";
        TriviaManager triviaManager = new TriviaManager();
        Storage storage = new Storage();
        triviaManager.learnInput(testinput,storage);
        assertEquals("Love Qinhuai River",triviaManager.CommandMemory.get("Love").get(0));
        triviaManager.CommandMemory.remove("Love");
    }

    @Test
    public void testShowPossibleInput() throws IOException{
        String testinput = "Love Qinhuai River";
        TriviaManager triviaManager= new TriviaManager();
        Storage storage = new Storage();
        triviaManager.learnInput(testinput,storage);
        triviaManager.showPossibleInputs("Love");
//        System.out.print("Could it be one of the below inputs?\n");
//        System.out.print(triviaManager.CommandMemory.get("Love"));
        assertEquals("Could it be one of the below inputs?\n"+triviaManager.CommandMemory.get("Love").toString()+"\n",outContent.toString());
        triviaManager.CommandMemory.remove("Love");
    }
}
