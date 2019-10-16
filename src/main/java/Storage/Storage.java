package Storage;

import java.io.File;

import Tasks.Task;
import Tasks.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Storage {

    private String absolutePath = "Save.txt";
    private String absolutePathPassword = "Password.txt";
    private String absolutePathContact = "Contact.txt";

    public void storages(final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public ArrayList<Task> readFile() throws IOException {
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
                } else if (details[0].equals("P")) {
                    Timebound tb = new Timebound(details[2].trim(), details[3].substring(8).trim());
                    if (details[1].equals("D")) {
                        tb.isDone = true;
                    } else {
                        tb.isDone = false;
                    }
                    tList.add(tb);
                } else if (details[0].equals("FD")) {
                    FixedDuration fd = new FixedDuration(details[2].trim(), details[3].trim());
                    if (details[1].equals("D")) {
                        fd.isDone = true;
                    } else {
                        fd.isDone = false;
                    }
                    tList.add(fd);
                } else if (details[0].equals("DA")) {
                    DoAfter da = new DoAfter(details[3].trim(), details[3].trim(), details[2].trim());
                    if (details[1].equals("D")) {
                        da.isDone = true;
                    } else {
                        da.isDone = false;
                    }
                    tList.add(da);
                } else if (details[0].equals("TE")) {
                    ArrayList<String> timeslots = new ArrayList<String>();
                    for (int i = 3; i < details.length; i++) {
                        timeslots.add(details[i]);
                    }
                    TentativeEvent te = new TentativeEvent(details[2].trim(), timeslots);
                    if (details[1].equals("D")) {
                        te.isDone = true;
                    } else {
                        te.isDone = false;
                    }
                    tList.add(te);
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
                    }
                }
            }
        }
        return tList;
    }

    /**
     * This method write the password to the file.
     * @param fileContent contains the password.
     * @throws IOException
     */

    public void storagesPassword(final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePathPassword);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public ArrayList<String> password() throws IOException {
        ArrayList<String> passwordList = new ArrayList<>();
        if (new File(absolutePathPassword).exists()) {
            File file = new File(absolutePathPassword);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                passwordList.add(sc.nextLine());
            }
        }
        return passwordList;
    }

    /**
     * THis method writes to the file Contact.txt.
     *
     * @param fileContent save the contact information into this file
     * @throws IOException
     */
    public void storagesContact(final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePathContact);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method read from the file Contact.txt and put the details into a HashMap
     *
     * @return Returns the HashMap of contacts, key is the contact name and the value is the phone number
     * @throws IOException
     */
    public HashMap<String, String> Contact() throws IOException {
        HashMap<String, String> contactList = new HashMap<String, String>();
        if (new File(absolutePathContact).exists()) {
            File file = new File(absolutePathContact);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] split = sc.nextLine().split("\\|");
                contactList.put(split[0], split[1]);
            }
        }
        return contactList;
    }
}