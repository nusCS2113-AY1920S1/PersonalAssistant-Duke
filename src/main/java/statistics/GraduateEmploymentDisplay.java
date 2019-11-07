package statistics;

import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class GraduateEmploymentDisplay {
    private static ArrayList<MyClass> Stats = new ArrayList<>();
    public static ArrayList<MyClass> getStats(){
        return Stats;
    }

    public void print(String input) {
        for (int i = 0; i < Stats.size(); i++) {
            if (Stats.get(i).getA().equals(input)) {
                System.out.println(Stats.get(i).getB() + " " + Stats.get(i).getC());
            }
        }
        BarChart_AWT chart = new BarChart_AWT("Employment Statistics",
                "Employment Percentage", input);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }
    
    }


