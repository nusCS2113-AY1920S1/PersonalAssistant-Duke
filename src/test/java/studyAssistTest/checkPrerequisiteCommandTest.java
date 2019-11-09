//@@ author mononokehime14
package studyAssistTest;
import gazeeebo.commands.studyassist.checkPrerequisiteCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class checkPrerequisiteCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void checkPrerequisite_wrongModuleException(){
        Storage storage = new Storage();
        Ui ui =new Ui();
        ui.fullCommand = "prerequisite CS2";
        try {
            new checkPrerequisiteCommand().execute(ui,storage);
        }catch (DukeException | IOException e){
            assertEquals("We currently do not support this module",e.getMessage());
        }
    }
    @Test
    void checkPrerequisiteTest(){
        Storage storage = new Storage();
        Ui ui =new Ui();
        ui.fullCommand = "prerequisite CS2040C";
        try {
            new checkPrerequisiteCommand().execute(ui,storage);
        }catch (DukeException | IOException e){
            assertEquals("CS2040C\n" +
                    "└── CS1010\n",outContent.toString());
        }
    }


}
