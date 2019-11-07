import Commands.Command;
import Commands.FilterCommand;
import Commons.LookupTable;
import Commons.UserInteraction;
import StubClasses.StorageStub;
import Tasks.Assignment;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {
    private static Assignment eventWithoutKeyword;
    private static Assignment deadlineWithoutKeyword;
    private static Assignment eventWithKeyword;
    private static Assignment deadlineWithKeyword;
    private static String keyword = "key";
    private StorageStub storage = new StorageStub();
    private static TaskList events = new TaskList();
    private UserInteraction ui = new UserInteraction();
    private static TaskList deadlines = new TaskList();

    @BeforeAll
    public static void setAllVariables() {
        eventWithoutKeyword = new Event("CS2101 EventTest","12/10/2019","1200","1201");
        deadlineWithoutKeyword = new Deadline("CS2101 DeadlineTest","13/10/2019","1202");
        eventWithKeyword = new Event("CS2101 EventWithKey","12/10/2019","1300","1301");
        deadlineWithKeyword = new Deadline("CS2101 DeadlineWithKey","13/10/2019","1200");
    }

    @Test
        public void keywordNotInListTest() {
        deadlines.addTask(deadlineWithoutKeyword);
        events.addTask(eventWithoutKeyword);
        Command command = new FilterCommand(keyword);
        String w1 = "There are no task(s) matching your keyword.";
        String w2 = new String();
        try {
            w2 = command.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            w2 = e.getMessage();
        }
        assertEquals(w1, w2);
    }
    @Test
    public void keywordInListTest() {
        deadlines.addTask(deadlineWithKeyword);
        events.addTask(eventWithKeyword);
        Command command = new FilterCommand(keyword);
        String w1 ="Here are the following events/deadline with the keyword key\n" +
                "1.[E] EventWithKey CS2101 Sat 12/10/2019 01:00 pm to 01:01 pm\n" +
                "2.[D] DeadlineWithKey CS2101 Sun 13/10/2019 12:00 pm" ;
        String w2;
        try {
            w2 = command.execute(events, deadlines, ui, storage);
        } catch (Exception e) {
            w2 = e.getMessage();
        }
        assertEquals(w1, w2);
    }

    @Test
    public void keywordSubstringTest(){
     //keyword would be changed from key to keyword
         keyword = "keyword";
         deadlines.addTask(deadlineWithKeyword);
         events.addTask(eventWithKeyword);
         Command command = new FilterCommand(keyword);
         String w1 = "There are no task(s) matching your keyword.";
         String w2 ;
         try {
              w2 = command.execute(events, deadlines, ui, storage);
         } catch (Exception e) {
             w2 = e.getMessage();
         }
         assertEquals(w1, w2);
    }
}
