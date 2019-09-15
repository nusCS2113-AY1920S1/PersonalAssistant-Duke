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
        io.provideInput("bye");
        Duke.main(new String[0]);
        String output = io.getOutput();
        String expected = "\t____________________________________________________________\n"
                        + "\t Natty is setup on Sun Sep 15 21:00:00 SGT 2019\n"
                        + "\t____________________________________________________________\n"
                        + "\t____________________________________________________________\n"
                        + "\t Hello! I'm Duke\n"
                        + "\t What can I do for you?\n"
                        + "\t____________________________________________________________\n"
                        + "\t____________________________________________________________\n"
                        + "\t Here are the tasks in your lists:\n"
                        + "\t____________________________________________________________\n"
                        + "\t____________________________________________________________\n"
                        + "\t Bye. Hope to see you again soon!\n"
                        + "\t____________________________________________________________\n";
        assertEquals(expected, output);
    }

}
