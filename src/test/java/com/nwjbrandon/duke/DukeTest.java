package com.nwjbrandon.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DukeTest {

    @RegisterExtension
    SystemIO io = new SystemIO();

    @Test
    void testMainMethod() throws IOException {

        provideInput("reminder\nbye");
        Duke.main(new String[0]);
        String output = getOutput();
        String expected = "\t____________________________________________________________\n" +
                "\t Hello! I'm Duke\n" +
                "\t What can I do for you?\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\t Got it. I've added this task:\n" +
                "\t   [D][✗] submit (by: 22nd of September 2019, 1pm)\n" +
                "\t Now you have 1 tasks in the list.\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\t Got it. I've added this task:\n" +
                "\t   [E][✗] buy bbtea (at: 14th of September 2019, 6pm)\n" +
                "\t Now you have 2 tasks in the list.\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\t Got it. I've added this task:\n" +
                "\t   [D][✗] get rekted (by: 1st of September 2019, 11pm)\n" +
                "\t Now you have 3 tasks in the list.\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\t Got it. I've added this task:\n" +
                "\t   [T][✗] eat mochi\n" +
                "\t Now you have 4 tasks in the list.\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\t Got it. I've added this task:\n" +
                "\t   [D][✗] shave beard (by: 14th of September 2019, 8pm)\n" +
                "\t Now you have 5 tasks in the list.\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\t Here are the tasks in your lists:\n" +
                "\t 1.[D][✗] submit (by: 22nd of September 2019, 1pm)\n" +
                "\t 2.[E][✗] buy bbtea (at: 14th of September 2019, 6pm)\n" +
                "\t 3.[D][✗] get rekted (by: 1st of September 2019, 11pm)\n" +
                "\t 4.[T][✗] eat mochi\n" +
                "\t 5.[D][✗] shave beard (by: 14th of September 2019, 8pm)\n" +
                "\t____________________________________________________________\n" +
                "\t____________________________________________________________\n" +
                "\tHere are the overdue tasks:\n" +
                "\t 1.[D][✗] get rekted (by: 1st of September 2019, 11pm)\n" +
                "\t 2.[E][✗] buy bbtea (at: 14th of September 2019, 6pm)\n" +
                "\t 3.[D][✗] shave beard (by: 14th of September 2019, 8pm)\n" +
                "\n" +
                "\tHere are the tasks you have today:\n" +
                "\n" +
                "\tHere are the upcoming tasks:\n" +
                "\t 1.[D][✗] submit (by: 22nd of September 2019, 1pm)\n" +
                "\n" +
                "\tHere are the undefined tasks:\n" +
                "\t 1.[T][✗] eat mochi\n" +
                "\n" +
                "\t____________________________________________________________\n" +
                "\n" +
                "\t____________________________________________________________\n" +
                "\t Bye. Hope to see you again soon!\n" +
                "\t____________________________________________________________\n";

        assertEquals(expected, output);
    }

}
