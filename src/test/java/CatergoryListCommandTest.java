//
//import gazeeebo.storage.Storage;
//import gazeeebo.tasks.Deadline;
//import gazeeebo.tasks.Event;
//import gazeeebo.tasks.Task;
//import gazeeebo.UI.Ui;
//import gazeeebo.commands.CategoryListCommand;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.text.ParseException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CategoryListCommandTest  {
//    private ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private PrintStream mine = new PrintStream(output);
//    private PrintStream original = System.out;
//
//    @BeforeEach
//    void setupStream() {
//        System.setOut(mine);
//    }
//
//    @AfterEach
//    void restoreStream(){
//        System.out.flush();
//        System.setOut(original);
//    }
//
//    @Test
//    void testCategory() throws ParseException, IOException {
//        Ui ui = new Ui();
//        Storage storage = new Storage();
//        CategoryListCommand testC = new CategoryListCommand();
//        ArrayList<Task> list = new ArrayList<>();
//        Deadline newD = new Deadline("yearly assignment", "2019-01-01 01:01:01");
//        Event newE = new Event("project meeting", "2019-09-09 12:12:12-13:13:13");
//        list.add(newD);
//        list.add(newE);
//
//        //testC.Ca(list, 0,list.get(0).toString(),storage);
//        assertEquals("");
//    }
//}
//
