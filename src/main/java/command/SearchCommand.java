package command;

import task.*;
import ui.Ui;
import storage.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *  Used to find the next free timeslot of a duration of
 * user's choosing
 * 
 * @author Hans kurnia
 * @version 1.0
 *  
 */
public class SearchCommand extends Command {
  private long durationToFind;

  public SearchCommand(Long duration) {
    this.durationToFind = duration;
  }

  public void execute(TaskList tasks, Ui ui, Storage storage) {
    ArrayList<Date> dateList = new ArrayList<Date>();
    boolean found = false;
    for (Task item : tasks.getTasks()) {
      if(item.getClass() == task.Event.class || item.getClass() == task.Deadline.class) {
        dateList.add(item.getDate());
      }
    }
    Collections.sort(dateList);
    if(dateList.size()  == 0) {
      ui.printOutput("You have no events that will clash with a slot of this duration");
      found = true;
    }
    else if(dateList.size() == 1) {
      ui.printOutput("You can schedule something after " + dateList.get(0).toString());
    }
    else {
      for(int i = 1; i < dateList.size(); i++){
        long diffInMilliSec = Math.abs(dateList.get(i).getTime() - dateList.get(i-1).getTime());
        long duration = TimeUnit.HOURS.convert(diffInMilliSec, TimeUnit.MILLISECONDS);
        if(durationToFind <= duration){
          ui.printOutput("Next free time slot of duration " + Long.toString(durationToFind) 
                          + "hrs is between \n" + dateList.get(i-1).toString() + " and "
                          + dateList.get(i).toString());
          found = true;
          break;
        }  
      }
      
      if(!found){
          ui.printOutput("You can schedule something after the " + dateList.get(dateList.size()-1).toString());
      }
    }

  }
}