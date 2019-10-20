package list;

import main.Duke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DegreeListStorage {
    private static final String filename = "../data/savedegree.txt"; //text file that stores all the information
    File file = new File(filename);
    ArrayList<String> list = DegreeList.getDegrees();
    private static List<String> lines;
    UpdateFile upd = new UpdateFile();

    public void ReadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split("-");
                if (data[0].equals("degree")) {
                    if(data.length < 4) {
                        list.add(data[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> AddRemoved(String imp) {
        List<String> newLines = new ArrayList<String>();
        for(String line: lines){
            String [] vals = line.split("-");
            if(vals[1].equals(imp)){
                    newLines.add(vals[0] + "-" + vals[1] +  "-" + vals[2] + "-" + "REMOVED");
            } else {
                newLines.add(line);
            }
        }
        return newLines;
    }

    public void processing(String imp) throws IOException {
        lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        AddRemoved(imp); //the description of the task is passed onto the AddRemoved method which has been described above.
        Files.write(file.toPath(), AddRemoved(imp), Charset.defaultCharset());
        for(int i = 0; i < list.size(); i++) {
            upd.reduce_index(list.get(i));
        }
    }

}