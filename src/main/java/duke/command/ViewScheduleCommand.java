package duke.command;

import duke.core.*;
import duke.exception.*;
import duke.task.*;

import java.util.*;
import java.io.*;
import java.text.*;


public class ViewScheduleCommand extends Command{

    protected String strDate;
    public ViewScheduleCommand(String strDate) {
        this.strDate = strDate;
    }

    public void execute(Ui ui, Storage storage, TaskList tasks) {
        ui.printScheduleArray("Here are the tasks for this date:", tasks.generateListByDate(strDate));
    }
}
