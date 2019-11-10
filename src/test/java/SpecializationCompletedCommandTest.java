//import gazeeebo.UI.Ui;
//import gazeeebo.commands.specialization.CompletedCommand;
//import gazeeebo.commands.specialization.ModuleCategory;
//import gazeeebo.exception.DukeException;
//import gazeeebo.storage.Storage;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.TreeMap;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class SpecializationCompletedCommandTest {
//    private ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private PrintStream mine = new PrintStream(output);
//    private PrintStream original = System.out;
//    Ui ui = new Ui();
//    Storage storage = new Storage();
//
//
//    @BeforeEach
//    void setupStream() {
//        System.setOut(mine);
//    }
//
//    @AfterEach
//    void restoreStream() {
//        System.out.flush();
//        System.setOut(original);
//    }
//
//    @Test
//    void completedCommandTest() throws NumberFormatException, DukeException, ArrayIndexOutOfBoundsException, IOException {
//        Map<String, ArrayList<ModuleCategory>> specMap = new TreeMap<>();
//        Map<String, ArrayList<String>> completedEMap =new TreeMap<>();
//        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
//        System.setIn(in);
//        new CompletedCommand(ui, storage, specMap, completedEMap);
//        assertEquals("Which specialization number is your module under?\n" +
//                "1. Communications & Networking\n" +
//                "2. Embedded Computing\n" +
//                "3. Intelligent Systems\n" +
//                "4. Interactive Digital Media\n" +
//                "5. Large-Scale Computing\n" +
//                "6. System-On-A-Chip Design\r\n"
//                + "Which module have you completed?\n" +
//                        "1. CS2107 Introduction to Information System\n" +
//                        "2. CS3103 Computer Networks Practice\n" +
//                        "3. EE3131C Communication Systems\n" +
//                        "4. CS4222 Wireless Networking\n" +
//                        "5. CS4226 Internet Architecture\n" +
//                        "6. EE4210 Network Protocols and Applications\n" +
//                        "7. CS5223 Distributed Systems\n" +
//                        "8. CS5321 Network Security\n" +
//                        "9. EE5135 Digital Communications\n",
//                output.toString());
//    }
//}
