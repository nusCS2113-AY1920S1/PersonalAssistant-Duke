package duke.Sports.Days;

import duke.Sports.MyClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Monday {

    public Monday() throws ParseException {
        Map<MyClass, ArrayList<Date>> Monday = new HashMap<>();

        MyClass swimming = new MyClass("Swimming",false,"Monday");
        ArrayList<Date> times = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");
        Date startTime = simpleDateFormat.parse("1500");
        Date endTime = simpleDateFormat.parse("1600");
        times.add(startTime);
        times.add(endTime);

        //Add classes to a day
        Monday.put(swimming,times);

        //Access a class in a day
        Monday.get(swimming);

        //Remove a class in a day
        Monday.remove(swimming);

        //Removes all classes in a day
        Monday.clear();

        //Find total number of classes in a day
        Monday.size();
    }

}
