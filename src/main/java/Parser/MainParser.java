//package Parser;
//import Commands.*;
//
//public class MainParser {
//    public static Command parse(String fullCommand) throws Exception {
//        String [] stringSplit = fullCommand.split(" ");
//        String command = stringSplit[0];
//        switch (command) {
//            case "add/e":
//            case "add/d":
//                return new AddParse(fullCommand).execute();
//                break;
//            case "delete/e":
//            case "delete/d":
//                return new DeleteParse(fullCommand).execute();
//                break;
//            case "recur/e":
//                return new RecurParse(fullCommand).execute();
//                break;
//            case "remind/set":
//                break;
//            case "remind/rm":
//                break;
//            case "/show":
//                return new WorkloadParse(fullCommand).execute();
//                break;
//            case "filter":
//                return new FilterCommand(fullCommand.trim().substring(7));
//                break;
//            case "help":
//                return new HelpCommand();
//                break;
//            case "list":
//                break;
//            case "done/e":
//            case "done/d":
//                return new DoneParse(fullCommand).execute();
//                break;
//            case "Find":
//                return new FindFreeTimesParse(fullCommand).execute();
//                break;
//            case "bye":
//                return new ByeCommand();
//                break;
//        }
//    }
//}