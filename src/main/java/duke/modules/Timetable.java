package duke.modules;

import java.util.List;
import java.util.Scanner;

public class Timetable {
    private static List<String> Module;
    int numberOfModules;

    public static List<String> getTimetable() {
        return Module;
    }

    /**
     * Constructor for Timetable class.
     */
    public Timetable() {
        System.out.println("How many modules are you taking this semester?");
        Scanner myObj = new Scanner(System.in);
        int numberOfModules = Integer.getInteger(myObj.nextLine());
        for (int i = 0; i < numberOfModules; i++) {
            String tempModule = myObj.nextLine();
            //access the data to get the time of those modules.
            Module.add(tempModule);
        }
    }
}
