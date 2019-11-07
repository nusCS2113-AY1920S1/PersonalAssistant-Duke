package statistics;

import exception.DukeException;
import java.util.ArrayList;
import java.util.List;

public class GraduateEmployment {

    ArrayList<MyClass> Stats = GraduateEmploymentDisplay.getStats();

    public void loadDegreeEmploymentStats(List<String> st) throws DukeException {
        for (int i = 1; i < 4; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("bme", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 5; i < 8; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("che", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 9; i < 12; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("cive", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 13; i < 16; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("ee", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 17; i < 20; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("enve", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 21; i < 24; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("ise", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 25; i < 28; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("mse", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 29; i < 32; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("me", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }
        for (int i = 33; i < 36; i++) {
            String[] data = st.get(i).split("-");
            if (data.length > 2) {
                Stats.add(new MyClass("ceg", Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        }


    }
}



