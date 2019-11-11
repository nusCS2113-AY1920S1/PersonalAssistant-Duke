package moomoo.stubs;

import moomoo.feature.ScheduleList;

import java.util.ArrayList;
import java.util.HashMap;



public class ScheduleListStub extends ScheduleList {
    //    public HashMap<String, ArrayList<String>> newCalendar;
    //
    //    public ScheduleListStub() {
    //        newCalendar = new HashMap<>();
    //    }
    //
    //    @Override
    //    public void addToCalendar(String date, String task) {
    //        ArrayList<String> task1 = new ArrayList<>();
    //        task1.add("school fees 50 dollars");
    //        task1.add("electricity bills 10.5 dollars");
    //        ArrayList<String> task2 = new ArrayList<>();
    //        task2.add("installment plan 365 dollars");
    //        ArrayList<String> task3 = new ArrayList<>();
    //        task3.add("spotify plan 12.34 dollars");
    //        task3.add("water bills 1234 dollars");
    //        ArrayList<String> task4 = new ArrayList<>();
    //        task4.add("school fees 50 dollars");
    //        task4.add("electricity bills 10.5 dollars");
    //        newCalendar.put("02/11/2019", task1);
    //        newCalendar.put("10/11/2019", task2);
    //        newCalendar.put("11/11/2019", task3);
    //        newCalendar.put("31/12/2019", task4);
    //    }
    //
    //    @Override
    //    public String showSchedule(String date) {
    //        String output =
    //                ".__________________________.\n"
    //                + "|Outstanding Payment Today |\n"
    //                + "|--------------------------|\n";
    //
    //        if (newCalendar.containsKey(date)) {
    //            for (String n : newCalendar.get(date)) {
    //                String[] amount = n.split(" ");
    //                int index = amount.length - 1;
    //                String blanks = " ";
    //                n = n.replaceAll("[0-9]","");
    //                int blank = 19 - n.length();
    //                for (int i = 1; i <= blank; i++) {
    //                    blanks += " ";
    //                }
    //                String blank2 = " ";
    //                blank = 4 - amount[index].length();
    //                for (int i = 1; i <= blank; i++) {
    //                    blank2 += " ";
    //                }
    //                output += "|" + n + blanks + "|" + amount[index] + blank2 + "|\n";
    //            }
    //        }
    //        output += ".--------------------------.\n";
    //        return output;
    //    }
}
