package com.nwjbrandon.duke;

import com.nwjbrandon.duke.services.TaskManager;
import com.nwjbrandon.duke.services.ui.Terminal;
import com.joestelmach.natty.Parser;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Duke {

    public Duke() {
    }

    /**
     * This is the main method which runs everything.
     * @param args Takes in a string argument.
     * @throws IOException Throws the exception for input/output.
     */
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        List<Date> dates = parser.parse("Sep 15, 2019 20:00:00 +7:00").get(0).getDates();
        Terminal.showMessage("Natty is setup on " + dates.get(0));

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
