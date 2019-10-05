package EPstorage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProfileStorage {
    private Scanner scan;
    private File data;

    public ProfileStorage() {
        data = new File("EPdata/profile.txt");
    };

    public void openFile() throws IOException{
        scan = new Scanner(data);
    }

    public UserProfile load() throws IOException {
        UserProfile userProfile = new UserProfile();
        openFile();
        readFile(userProfile);
        closeFile();
        return userProfile;
    }

    public void readFile(UserProfile userProfile){
        while (scan.hasNextLine()){
            String dataType = scan.nextLine();
            if (dataType.equals("//name")){
                userProfile.setUserName(scan.nextLine());
            } else if (dataType.equals("//age")){
                userProfile.setUserAge(Integer.parseInt(scan.nextLine()));
            } else if (dataType.equals("//genre")){
                String[] tokens = scan.nextLine().split(Pattern.quote(" , "));
                ArrayList<Integer> inputGenre = new ArrayList<>(50);
                for (String log : tokens){
                    inputGenre.add(Integer.parseInt(log));
                }
                userProfile.setGenreId(inputGenre);
            }
        }
    }

    public void closeFile(){
        scan.close();
    }

    public void changeName(String name) throws IOException {
        File oldFile = data;
        File newFile = new File("EPdata/tempProfile.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        openFile();

        while (scan.hasNextLine()){
            String dataType = scan.nextLine();
            pw.println(dataType);
            if (dataType.equals("//name")){
                pw.println(name);
                scan.nextLine();
            }
        }

        closeFile();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }

    public void changeAge(String age) throws IOException {
        File oldFile = data;
        File newFile = new File("EPdata/tempProfile.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        openFile();

        while (scan.hasNextLine()){
            String dataType = scan.nextLine();
            pw.println(dataType);
            if (dataType.equals("//age")){
                pw.println(age);
                scan.nextLine();
            }
        }

        closeFile();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }

//    public void addGenre(ArrayList<Integer> genreList) throws IOException {
//        File oldFile = data;
//        File newFile = new File("EPdata/tempProfile.txt");
//
//        FileWriter fw = new FileWriter(newFile, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//        PrintWriter pw = new PrintWriter(bw);
//        openFile();
//
//        while (scan.hasNextLine()){
//            String dataType = scan.next();
//
//            if (dataType.equals("//genre")){
//                pw.println("//genre ");
//                pw.print(scan.nextLine());
////                for (Integer genre : genreList){
////                    pw.println(" , " + genre);
////                }
//            } else{
//                pw.println(dataType);
//                pw.println(scan.nextLine());
//            }
//        }
//        scan.close();
//        pw.flush();
//        pw.close();
//        oldFile.delete();
//        newFile.renameTo(new File(data.getAbsolutePath()));
//    }
//
//    public void removeGenre(ArrayList<Integer> genreList) throws IOException {
//        File oldFile = data;
//        File newFile = new File("EPdata/tempProfile.txt");
//
//        FileWriter fw = new FileWriter(newFile, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//        PrintWriter pw = new PrintWriter(bw);
//        openFile();
//
//        while (scan.hasNextLine()){
//            String dataType = scan.next();
//            if (dataType.equals("//genre")){
//                String tokens[] = scan.nextLine().split(Pattern.quote(" , "));
//                pw.print("//genre ");
//                for (Integer genre : genreList) {
//                    boolean flag = true;
//                    for (String log : tokens) {
//                        if (!log.equals(genre)) {
//                            if (flag) {
//                                pw.print(log);
//                                flag = false;
//                            }
//                            pw.print(" , " + log);
//                        }
//                    }
//                }
//            } else{
//                pw.println(dataType + " " + scan.nextLine());
//            }
//        }
//        scan.close();
//        pw.flush();
//        pw.close();
//        oldFile.delete();
//        newFile.renameTo(new File(data.getAbsolutePath()));
//    }

    public void setGenre(ArrayList<Integer> genreList) throws IOException {
        File oldFile = data;
        File newFile = new File("EPdata/tempProfile.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        openFile();

        while (scan.hasNextLine()) {
            String dataType = scan.nextLine();
            pw.println(dataType);
            if (dataType.equals("//genre")) {
                boolean flag = true;
                for (Integer log : genreList) {
                    if (flag) {
                        pw.print(log);
                        flag = false;
                    } else {
                        pw.print(" , " + log);
                    }
                }
                scan.nextLine();
            }
        }

        closeFile();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }

    public void addGenre(ArrayList<Integer> genreList) throws IOException {
        File oldFile = data;
        File newFile = new File("EPdata/tempProfile.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        openFile();

        while (scan.hasNextLine()) {
            String dataType = scan.nextLine();
            pw.println(dataType);
            if (dataType.equals("//genre")) {
                String toPrint = scan.nextLine();
                for (Integer log : genreList) {
                    toPrint += " , " + log;
                }
                pw.println(toPrint);
            }
        }

        closeFile();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }

    public void removeGenre(ArrayList<Integer> genreList) throws IOException {
        File oldFile = data;
        File newFile = new File("EPdata/tempProfile.txt");

        FileWriter fw = new FileWriter(newFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        openFile();

        while (scan.hasNextLine()) {
            String dataType = scan.nextLine();
            pw.println(dataType);
            if (dataType.equals("//genre")) {
                String toPrint = "";
                String[] tokens = scan.nextLine().split(" , ");
                for (String log : tokens){
                    boolean flag = true;
                    for (Integer newLog : genreList){
                        if (!Integer.toString(newLog).equals(log)){
                            if (flag) {
                                toPrint += log;
                                flag = false;
                            } else {
                                toPrint += " , " + log;
                            }
                        }
                    }
                }
                pw.println(toPrint);
            }
        }

        closeFile();
        pw.flush();
        pw.close();
        oldFile.delete();
        newFile.renameTo(new File(data.getAbsolutePath()));
    }
}