package gazeeebo.storage;

import java.io.BufferedWriter;
import java.io.File;

import gazeeebo.commands.gpacalculator.GPACommand;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.DoAfter;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.*;
import gazeeebo.TriviaManager.TriviaManager;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.*;


public class Storage {

    private String absolutePath = "Save.txt";
    private String absolutePath_password = "Password.txt";
    private String absolutePath_Contact = "Contact.txt";
    private String absolutePath_Expenses = "Expenses.txt";
    private String absolutePath_Places = "Places.txt";
    private String absolutePath_Trivia = "Trivia.txt";
    private String absolutePath_GPA = "Gpa.txt";
    private String absolutePath_StudyPlanner = "Study_Plan.txt";

    public void Storages(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
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
                } else if (details[0].equals("P")) {
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
                    for (int i = 3; i < details.length; i++) {
                        timeslots.add(details[i]);
                    }
                    TentativeEvent TE = new TentativeEvent(details[2].trim(), timeslots);
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

    /**
     * THis method writes to the file Contact.txt
     *
     * @param fileContent save the contact information into this file
     * @throws IOException
     */
    public void Storages_Contact(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath_Contact);
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
        if (new File(absolutePath_Contact).exists()) {
            File file = new File(absolutePath_Contact);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] split = sc.nextLine().split("\\|");
                contactList.put(split[0], split[1]);
            }
        }
        return contactList;
    }

    public void Storages_Expenses(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath_Expenses);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();

    }
        public void Storages_Places (String fileContent) throws IOException {
            FileWriter fileWriter = new FileWriter(absolutePath_Places);
            fileWriter.write(fileContent);
            fileWriter.flush();
            fileWriter.close();
        }

        public HashMap<LocalDate, ArrayList<String>> Expenses () throws IOException {
            HashMap<LocalDate, ArrayList<String>> expenses = new HashMap<LocalDate, ArrayList<String>>();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (new File(absolutePath_Expenses).exists()) {
                File file = new File(absolutePath_Expenses);
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    ArrayList<String> itemAndPriceList = new ArrayList<>();
                    String[] split = sc.nextLine().split("\\|");
                    LocalDate dateOfPurchase = LocalDate.parse(split[0], fmt);
                    boolean isEqual = false;
                    for (LocalDate key : expenses.keySet()) {
                        if (dateOfPurchase.equals(key)) { //if date equal
                            expenses.get(key).add(split[1]);
                            isEqual = true;
                        }
                    }

                    if (isEqual == false) {
                        itemAndPriceList.add(split[1]);
                        expenses.put(dateOfPurchase, itemAndPriceList);
                    }
                }
            }
            return expenses;
        }
        public HashMap<String, String> Read_Places () throws IOException {
            HashMap<String, String> placesList = new HashMap<String, String>();
            if (new File(absolutePath_Places).exists()) {
                File file = new File(absolutePath_Places);
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    String[] split = sc.nextLine().split("\\|");
                    placesList.put(split[0], split[1]);
                }
            }
            return placesList;
        }

    public void Read_Trivia(TriviaManager triviamanager) throws IOException {
        if (new File(absolutePath_Trivia).exists()) {
            File file = new File(absolutePath_Trivia);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String InputCommand= sc.nextLine();
                if(triviamanager.CommandMemory.containsKey(InputCommand.split(" ")[0])) {
                    ArrayList<String> oldlist = new ArrayList<String>(triviamanager.CommandMemory.get(InputCommand.split(" ")[0]));
                    oldlist.add(InputCommand);
                    triviamanager.CommandMemory.put(InputCommand.split(" ")[0], oldlist);
                }else{
                    ArrayList<String> newlist = new ArrayList<String>();
                    newlist.add(InputCommand);
                    triviamanager.CommandMemory.put(InputCommand.split(" ")[0],newlist);
                }
            }
        }
    }
    public void Storage_Trivia(String fileContent) throws IOException{
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(absolutePath_Trivia,true));
        fileWriter.newLine();
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public void Storages_gpa(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(absolutePath_GPA);
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
    public HashMap<String, GPACommand> gpa() throws IOException {
        HashMap<String, GPACommand> gpaList = new HashMap<String, GPACommand>();
        if (new File(absolutePath_GPA).exists()) {
            File file = new File(absolutePath_GPA);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] split = sc.nextLine().split("\\|");
                int firstNo = Integer.parseInt(split[1]);
                double secondNo = Double.parseDouble(split[2]);
                GPACommand gpa = new GPACommand(firstNo,secondNo);
                gpaList.put(split[0], gpa);
            }
        }
        return gpaList;
    }

    public ArrayList<ArrayList<String>> Read_StudyPlan() throws IOException{
        ArrayList<ArrayList<String>> studyplan = new ArrayList<ArrayList<String>>();
        if (new File(absolutePath_StudyPlanner).exists()) {
            File file = new File(absolutePath_StudyPlanner);
            Scanner sc = new Scanner(file);
            for(int i =0;i<5;i++) {
                if(sc.hasNext()) {
                    String[] split = sc.nextLine().split(" ");
                    ArrayList<String> temp = new ArrayList<String>();
                    temp = Arrays.stream(split).collect(Collectors.toCollection(ArrayList::new));
                    studyplan.add(temp);
                }
            }
        }
        return studyplan;
    }

    public void Storage_StudyPlan(String fileContent) throws IOException{
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(absolutePath_Trivia,true));
        fileWriter.newLine();
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }
}