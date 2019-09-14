package com.nwjbrandon.duke;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DukeTest extends TestExtender {

    @Test
    void testMainMethod() throws IOException {
        provideInput("bye");
        Duke.main(new String[0]);
        String output = getOutput();
        String expected = "\t____________________________________________________________\n"
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
        provideInput("reminder\n");
    }

}
