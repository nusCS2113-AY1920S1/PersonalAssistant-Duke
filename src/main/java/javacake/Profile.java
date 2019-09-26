package javacake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Profile {
    private String filepath;
    private String username;
    private ArrayList<Integer> topicsDone = new ArrayList<>();

    public Profile(String filename, Ui ui) throws DukeException {
        filepath = filename;
        try {
            File file = new File(filepath);
            try {
                if (!file.exists()) {
                    file.getParentFile().getParentFile().mkdir();
                    file.getParentFile().mkdir();
                    file.createNewFile();
                    initialiseUser();
                }


            } catch (IOException e) {
                throw new DukeException("Failed to create new file");
            }

            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            int count = -1;
            while ((line = reader.readLine()) != null) {
                //ui.showMessage(line);
                if (count == -1) {
                    username = line;
                } else {
                    topicsDone.add(Integer.parseInt(line));
                }
                ++count;
            }
            reader.close();
        } catch (IOException e) {
            throw new DukeException("Failed to close reader");
        }
    }

    private void initialiseUser() throws DukeException {
        username = "NEW_USER_!@#";
        for (int i = 0; i < 3; ++i) {
            topicsDone.add(0);
        }
        try {
            PrintWriter out = new PrintWriter(filepath);
            out.println(username);
            for (int i = 0; i < 3; ++i) {
                out.println("0");
            }
            out.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Cannot initialise file");
        }
    }

    private void writeProgress() throws DukeException {
        try {
            PrintWriter out = new PrintWriter(filepath);
            out.println(username);
            for (int i : topicsDone) {
                out.println("" + i);
            }
            out.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Cannot initialise file");
        }
    }

    public String getUsername() {
        return username;
    }

    public void overwriteName(String oldname) throws DukeException {
        username = oldname;
        writeProgress();
    }

    public void setAsDone(int contentIdx) throws DukeException {
        topicsDone.set(contentIdx, 1);
        writeProgress();
    }

    public int getTotalProgress() {
        int count = 0;
        for (int i : topicsDone) {
            if (i == 1) {
                ++count;
            }
        }
        return count;
    }
}
