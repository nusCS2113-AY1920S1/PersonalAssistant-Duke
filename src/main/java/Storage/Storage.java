package Storage;

import java.io.File;

import Tasks.Task;
import Tasks.*;
import commands.FixDurationCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Storage {

    private String absolutePath = "Save.txt";
    private String absolutePath_password = "Password.txt";

    public void Storages(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public void Storages_password(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath_password);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public ArrayList<String> Password() throws IOException {
        ArrayList<String> passwordList = new ArrayList<>();
        if (new File(absolutePath_password).exists()) {
            File file = new File(absolutePath_password);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                passwordList.add(sc.nextLine());
            }
        }
        return passwordList;
    }

    public ArrayList<Task> ReadFile() throws IOException {
        ArrayList<Task> tList = new ArrayList<Task>();
        if (new File(absolutePath).exists()) {
            File file = new File(absolutePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] details = sc.nextLine().split("\\|");
                if (details[0].equals("T")) {
                    Todo t = new Todo(details[2].trim());
                    if (details[1].equals("D")) {
                        t.isDone = true;
                    } else {
                        t.isDone = false;
                    }
                    tList.add(t);
                } else if (details[0].equals("D")) {
                    Deadline d = new Deadline(details[2].trim(), details[3].substring(3).trim());
                    if (details[1].equals("D")) {
                        d.isDone = true;
                    } else {
                        d.isDone = false;
                    }
                    tList.add(d);
                } else if (details[0].equals("E)")) {
                    Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                    if (details[1].equals("D")) {
                        e.isDone = true;
                    } else {
                        e.isDone = false;
                    }
                    tList.add(e);
                }
                else if (details[0].equals("P")) {
                    Timebound tb = new Timebound(details[2].trim(), details[3].substring(8).trim());
                    if (details[1].equals("D")) {
                        tb.isDone = true;
                    } else {
                        tb.isDone = false;
                    }
                    tList.add(tb);
                } else if (details[0].equals("FD")) {
                    FixedDuration FD = new FixedDuration(details[2].trim(), details[3].trim());
                    if (details[1].equals("D")) {
                        FD.isDone = true;
                    } else {
                        FD.isDone = false;
                    }
                    tList.add(FD);
                } else if (details[0].equals("DA")) {
                    DoAfter DA = new DoAfter(details[3].trim(), details[3].trim(), details[2].trim());
                    if (details[1].equals("D")) {
                        DA.isDone = true;
                    } else {
                        DA.isDone = false;
                    }
                    tList.add(DA);
                } else if (details[0].equals("TE")) {
                    ArrayList<String> timeslots = new ArrayList<String>();
                   for(int i=3;i<details.length;i++){
                       timeslots.add(details[i]);
                   }
                   TentativeEvent TE = new TentativeEvent(details[2].trim(),timeslots);
                    if (details[1].equals("D")) {
                        TE.isDone = true;
                    } else {
                        TE.isDone = false;
                    }
                    tList.add(TE);
                } else {
                    if (details[3].contains("at:") || details[3].contains("by:")) {
                        Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                        if (details[1].equals("D")) {
                            e.isDone = true;
                        } else {
                            e.isDone = false;
                        }
                        tList.add(e);
                    } else if (details[0].contains("P")) {
                        Timebound tb = new Timebound(details[2].trim(), details[3].trim());
                        if (details[1].equals("D")) {
                            tb.isDone = true;
                        } else {
                            tb.isDone = false;
                        }
                        tList.add(tb);
                    } else {
                        FixedDuration FD = new FixedDuration(details[2].trim(), details[3].trim());
                        if (details[1].equals("D")) {
                            FD.isDone = true;
                        } else {
                            FD.isDone = false;
                        }
                        tList.add(FD);
                    }
                }
            }
        }
        return tList;
    }
}