package list;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DegreeListStorage {
    private static final String filename = "../data/savedegree.txt"; //text file that stores all the information
    File file = new File(filename);
    ArrayList<String> list = DegreeList.getDegrees();
    private static List<String> lines;
    UpdateFile upd = new UpdateFile();
    List<Pair<String, Integer>> store = new ArrayList<Pair<String, Integer>>();


    public void ReadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split("-");
                if (data[0].equals("degree")) {
                    if(data.length < 4) {
                        store.add(new Pair<String, Integer>(data[1], Integer.parseInt(data[2])));
                    }
                }
            }
            store.sort(new Comparator<Pair<String, Integer>>() {
                @Override
                public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                    if (o1.getValue() < o2.getValue()) {
                        return -1;
                    } else if (o1.getValue().equals(o2.getValue())) {
                        return 0; // You can change this to make it then look at the
                        //words alphabetical order
                    } else {
                        return 1;
                    }
                }
            });

            for(int i = 0; i < store.size(); i++) {
                list.add(store.get(i).getKey());
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
        int index = list.indexOf(imp);
        for(int i = index + 1; i < list.size(); i++) {
            upd.reduce_index(list.get(i));
      }
        //System.out.print(index);
    }


    public static List<String> Swap(String degree, String index) {
        List<String> newLines = new ArrayList<String>();
        for(String line: lines){
            String [] vals = line.split("-");
            if(vals[1].equals(degree)){
                newLines.add(vals[0] + "-" + vals[1] +  "-" + index);
            } else {
                newLines.add(line);
            }
        }
        return newLines;
    }

    public void work(String degree, String index) throws IOException {
        lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        Swap(degree, index);
        Files.write(file.toPath(), Swap(degree, index), Charset.defaultCharset());
    }


}