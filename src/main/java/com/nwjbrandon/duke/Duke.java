package com.nwjbrandon.duke;

import com.nwjbrandon.duke.services.TaskManager;
import com.nwjbrandon.duke.services.ui.Terminal;

import java.io.IOException;

public class Duke {

    public Duke() {
    }

    /**
     * Main application starts running here.
     * @param args - input of array of strings from console
     */
    public static void main(String[] args) throws IOException {
        Terminal.greetingMessage();
        String pwd = System.getProperty("user.dir");
        TaskManager taskManager = new TaskManager();
        String filePath = pwd + "/data/duke.txt";
        taskManager.loadData(filePath);
        while (taskManager.run()) {
            System.out.println();
        }
        taskManager.saveData(filePath);
        Terminal.farewellMessage();
    }


}
