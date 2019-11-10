package statistics;

import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;

public class GraduateEmploymentDisplay {
    private static ArrayList<MyClass> Stats = new ArrayList<>();

    public static ArrayList<MyClass> getStats() {
        return Stats;
    }

    public void print(String input) {
        for (int i = 0; i < Stats.size(); i++) {
            if (Stats.get(i).getA().equals(input)) {
                System.out.println(Stats.get(i).getB() + " " + Stats.get(i).getC());
            }
        }
        final Employment_BarChart demo = new Employment_BarChart("Basic Mean Salary and Employment rates", input);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}

