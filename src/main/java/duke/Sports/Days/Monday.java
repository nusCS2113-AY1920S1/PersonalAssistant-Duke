package duke.Sports.Days;

import duke.Sports.MyClass;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Monday {

    Map<MyClass, ArrayList<Date>> Monday = new HashMap<>();

    public Monday() {
        /**
         * To put in the Duke class somewhere.
         * Add in duke constructor: monday = new Monday();
         * Add in attribute declaration: private Monday monday;
         MyClass swimming = new MyClass("Swimming",false,"Monday");
         ArrayList<Date> times = new ArrayList<>();
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");
         Date startTime = simpleDateFormat.parse("1500");
         Date endTime = simpleDateFormat.parse("1600");
         times.add(startTime);
         times.add(endTime);
         monday.addClassToDay(swimming,times);
         if (monday.accessClassInDay("Swimming") == swimming) {
         System.out.println("OK");
         }
         if (monday.accessStartTimeInDay("Swimming") == startTime) {
         System.out.println("OK");
         }
         if (monday.accessEndTimeInDay("Swimming") == endTime) {
         System.out.println("OK");
         }
         */
    }

    //Add classes to a day
    public void addClassToDay(MyClass myclass, ArrayList<Date> times) {
        Monday.put(myclass,times);
    }

    //Remove class from a day
    public void removeClassFromDay(String className) {
        for (MyClass i : Monday.keySet()) {
            if (i.getInfo().equals(className)) {
                Monday.remove(i);
            }
        }
    }

    //Remove all classes in a day
    public void removeAllClasses() {
        Monday.clear();
    }

    //Find total number of classes in a day
    public void findNumClasses() {
        Monday.size();
    }

    //Access a class in a day
    public MyClass accessClassInDay(String className) {
        MyClass temp = null;
        for (MyClass i : Monday.keySet()) {
            if (i.getInfo().equals(className)) {
                temp = i;
            }
        }
        return temp;
    }

    //Access the start time of a class in a day
    public Date accessStartTimeInDay(String className) {
        Date temp = null;
        for (MyClass i : Monday.keySet()) {
            if (i.getInfo().equals(className)) {
                temp = Monday.get(i).get(0);
            }
        }
        return temp;
    }

    //Access the end time of a class in a day
    public Date accessEndTimeInDay(String className) {
        Date temp = null;
        for (MyClass i : Monday.keySet()) {
            if (i.getInfo().equals(className)) {
                temp = Monday.get(i).get(1);
            }
        }
        return temp;
    }

}
