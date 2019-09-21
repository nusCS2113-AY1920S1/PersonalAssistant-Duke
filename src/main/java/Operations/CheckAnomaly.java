package Operations;

import Model_Classes.Event;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Date;

public class CheckAnomaly {
    public static Boolean checkTime(Date at, ArrayList<Task> tasks){
        for( int i = 0; i<tasks.size(); i++ ){
            if( tasks.get(i) instanceof Event && ((Event) tasks.get(i)).checkDate().equals(at) ){
                return false;
            }
        }
        return true;
    }
}
