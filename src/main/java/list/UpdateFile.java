package list;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UpdateFile {
    private static final String filename = "../data/savedegree.txt"; //text file that stores all the information
    File file = new File(filename);
    private static List<String> lines;

    private static List<String> changeValueOf(String degree){
        List<String> newLines = new ArrayList<String>();
        for(String line: lines){
            String [] vals = line.split("-");
            if(vals[1].equals(degree)){
                int newVal = Integer.parseInt(vals[2]) - 1;
                newLines.add(vals[0] + "-" + vals[1] + "-" + newVal);
            }
            else {
                newLines.add(line);
            }
        }
        return newLines;
    }


    public void reduce_index(String degree) throws IOException {

            lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
            changeValueOf(degree);
            Files.write(file.toPath(), changeValueOf(degree), Charset.defaultCharset());
    }

}