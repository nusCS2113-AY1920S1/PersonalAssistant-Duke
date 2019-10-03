package Operations;

import Model_Classes.Event;
import Model_Classes.FixedDuration;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Date;

public class CheckAnomaly {

    public static Boolean checkTime(FixedDuration currEvent) {
        double currDuration;
        currDuration = currEvent.getDuration() * 3600000;
        ArrayList<Task> curr = TaskList.currentList();
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            Date checkDate = currEvent.checkDate();
            if( curr.get(i) instanceof FixedDuration ) {
                double duration;
                duration = ((FixedDuration) curr.get(i)).getDuration() * 3600000;
                Date listDate = ((FixedDuration) curr.get(i)).checkDate();
                if ( listDate.getYear() == checkDate.getYear() && listDate.getMonth() == checkDate.getMonth() && listDate.getDay() == checkDate.getDay() ) {
                    double checkTime = listDate.getTime();
                    double currTime = checkDate.getTime();
                    if( checkTime <= currTime + currDuration && checkTime >= currTime || checkTime + duration <= currTime + currDuration && checkTime + duration >= currTime ) {
                        return false;
                    }
                }
            } else if( curr.get(i) instanceof Event ) {
                Date listDate = ((Event) curr.get(i)).checkDate();
                // Checks if both events are on the same day
                if ( listDate.getYear() == checkDate.getYear() && listDate.getMonth() == checkDate.getMonth() && listDate.getDay() == checkDate.getDay() ) {
                    double checkTime = listDate.getTime();
                    double currTime = checkDate.getTime();
                    if( checkTime <= currTime + currDuration && checkTime >= currTime ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static Boolean checkTime(Date at){
        return true;
    }
}
