package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.SystemIO;
import com.nwjbrandon.duke.exceptions.DukeTaskCollisionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskListTest {

    @RegisterExtension
    SystemIO io = new SystemIO();

    @Test
    void testTaskCollision() {
        assertThrows(DukeTaskCollisionException.class, () -> {
            final String event = "watch anime /at 29/10/1997 1100\n";
            TaskList taskList = new TaskList();
            taskList.addTask(new Events(event, 1));
            final String deadline = "watch korean drama /by 29/10/1997 1500";
            taskList.addTask(new Deadlines(deadline, 2));
        });
    }

}
