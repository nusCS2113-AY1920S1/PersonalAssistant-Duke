//package Execution;
//
//import Commands.*;
//
//import java.util.ArrayList;
//import java.util.TreeMap;
//
//public class Command {
//    private COMMAND_KEYS RootCommand;
//    private COMMAND_KEYS subRootCommand;
//    private TreeMap<String , ArrayList<String>> flagMap = new TreeMap<String, ArrayList<String>>();
//    private String Payload;
//
//    public Command(COMMAND_KEYS rootCommand, COMMAND_KEYS subRootCommand, TreeMap<String, ArrayList<String>> flagMap, String payload) {
//        RootCommand = rootCommand;
//        this.subRootCommand = subRootCommand;
//        this.flagMap = flagMap;
//        Payload = payload;
//    }
//
//    public COMMAND_KEYS getRootCommand() {
//        return RootCommand;
//    }
//
//    public COMMAND_KEYS getSubRootCommand() {
//        return subRootCommand;
//    }
//
//    public TreeMap<String, ArrayList<String>> getFlagMap() {
//        return flagMap;
//    }
//
//    public String getPayload() {
//        return Payload;
//    }
//}
