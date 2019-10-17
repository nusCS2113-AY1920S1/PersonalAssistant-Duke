package list;

import main.Duke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DegreeListStorage {
    private static final String filename = "../data/savedegree.txt"; //text file that stores all the information
    File file = new File(filename);
    Duke dj = new Duke(); //new instance of the main class Duke
    ArrayList<String> mydegrees1 = Duke.getTasks(); //gets the dynamic array from Duke to add tasks to it

    public void ReadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split("-");
                if (data[0].equals("degree")) {
                   mydegrees1.add(data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}