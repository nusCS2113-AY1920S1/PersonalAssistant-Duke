package gazeeebo.commands.studyassist;

import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StudyPlannerCommand {
    public static ArrayList<ArrayList<String>> StudyPlan;
    public static Map<String,Integer> MCMap;
    public static Map<String,String> CoreModules;

    public StudyPlannerCommand(Storage storage) throws IOException {
        this.StudyPlan = storage.Read_StudyPlan();
        if(StudyPlan.isEmpty()) {
            StudyPlan = new ArrayList<ArrayList<String>>();
        }
        this.MCMap = (Map<String, Integer>) Stream.of(new Object[][] {
                {"CG2271",4}, {"CG1111",6}, {"CS1010E",4},{"MA1511",2},{"MA1512",2},{"CS1231",4},{"ES1103",4},
                {"EE2026",4},{"CG1112",6},{"MA1508E",4},{"CS2040C",4},{"GEQ1000",4},
                {"CS2101",4},{"CS2113T",4},{"GER1000",4},
                {"CG2023",4},{"ST2334",4},{"CG2027",2}, {"CG2028",2},{"GEH1036",4},
                {"CP3880",12}, {"EG2401",3},{"EG2401A",2},
                {"CG4002",8},{"EE4204",4}, {"GET1013",4}, {"CG3207",4}
        }).collect(Collectors.toMap(data->(String)data[0],data->(Integer)data[1]));

        this.CoreModules = (Map<String, String>) Stream.of(new String[][] {
                {"CG2271","Real-time Operating Systems"}, {"CG1111","Engineering Principles and Practice I"},
                {"CS1010","Programming Methodology"},{"MA1511","Engineering Calculus"},{"MA1512","Differential Equations for Engineering"},
                {"CS1231","Discrete Structures"}, {"EE2026","Digital Design"},{"CG1112","Engineering Principles and Practice II"},
                {"MA1508E","Linear Algebra for Engineering"},{"CS2040C","Data Structures and Algorithms"},
                {"CS2101","Effective Communication for Computing Professionals"},{"CS2113T","Software Engineering & Object-Oriented Programming"},
                {"CG2023","Signals & Systems"},{"ST2334","Probability & Statistics"},{"CG2027","Transistor-level Digital Circuits"},
                {"CG2028","Computer Organization"}, {"CP3880","Advanced Technology Attachment Programme"},{"EG2401A","Engineering Professionalism"},
                {"CG4002"," Computer Engineering Capstone Project"},{"EE4204","Computer Networks"},  {"CG3207","Computer Architecture"},
                {"CS3230","Design and Analysis of Algorithms"},{"EG3611A","Industrial Attachment"}
        }).collect(Collectors.toMap(data->(String)data[0],data->(String)data[1]));
    }

    public void showPlan() throws IOException, DukeException, ParseException {

        System.out.println(" +-----------------------------------------------------------------------+");
        System.out.println(" | Sem 1  | Sem 2  | Sem 3  | Sem 4  | Sem 5  | Sem 6  | Sem 7  | Sem 8  |");
        System.out.println(" +-----------------------------------------------------------------------+");
        int biggestsize = 0;
        for(int i=0;i<StudyPlan.size();i++){
           if(biggestsize < StudyPlan.get(i).size()) {
               biggestsize = StudyPlan.get(i).size();
           }
        }
//        System.out.println(biggestsize);
        ArrayList<StringBuilder> printplan = new ArrayList<StringBuilder>();
        for(int i = 0;i < biggestsize;i++){
            StringBuilder temp = new StringBuilder();
            temp.append(" ");
            for(int j = 0;j < StudyPlan.size();j++) {
                if(StudyPlan.get(j).size() > i){
                    if(StudyPlan.get(j).get(i).getBytes().length == 8){
                        temp.append("|"+StudyPlan.get(j).get(i));
                    }else if(StudyPlan.get(j).get(i).getBytes().length == 5){
                        temp.append("| "+StudyPlan.get(j).get(i)+"  ");
                    }else if(StudyPlan.get(j).get(i).getBytes().length == 6){
                        temp.append("| "+StudyPlan.get(j).get(i)+" ");
                    }else if(StudyPlan.get(j).get(i).getBytes().length == 7){
                        temp.append("|"+StudyPlan.get(j).get(i)+" ");
                    }
                }else{
                    temp.append("|        ");
                }
            }
            temp.append("|");
            printplan.add(temp);
        }
        StringBuilder temp2 = new StringBuilder();
        temp2.append(" ");
        for(int i=0;i<8;i++){
            int mc = calculateSemMC(i);
            if(String.valueOf(mc).length() == 2) {
                temp2.append("| MCs:" + mc + " ");
            }else if(String.valueOf(mc).length() == 1){
                temp2.append("| MCs:" + mc + "  ");
            }
        }
        temp2.append("|");
        printplan.add(temp2);
        for(int i =0;i<printplan.size();i++){
            System.out.println(printplan.get(i));
            System.out.println(" +-----------------------------------------------------------------------+");
        }
    }
    public int calculateSemMC(int semester){
        int count = 0;
        for (int i = 0; i < StudyPlan.get(semester).size(); i++) {
            try {
                String key = StudyPlan.get(semester).get(i);
                if(MCMap.get(key) == null){
                    throw new DukeException("We cannot find the MC of this module: "+ key);
                }
                count += MCMap.get(key);
            } catch (DukeException e){
                System.out.println(e);
            }
        }
        return count;
    }
    public boolean checkGraduation(){
        int count = 21;
        for(int i=0;i<StudyPlan.size();i++){
            List<String> temp= StudyPlan.get(i).stream().filter(data->CoreModules.get(data) != null).collect(Collectors.toList());
            count -= temp.size();
        }
        if(count == 0){
            return true;
        }else{
            return false;
        }
    }
}
