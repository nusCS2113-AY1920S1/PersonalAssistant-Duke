package reminder;

import java.text.SimpleDateFormat;
import java.util.*;

public class Reminder {
    Timer timer;

    public Reminder(Date date) {
        timer = new Timer();
        timer.schedule(new RemindTask(), date);
    }

    class RemindTask extends TimerTask {
        public void run() {
            //retrieve word list and their meanings here
            System.out.println("Time's up!");
            timer.cancel(); //Terminate the timer thread
        }
    }

    public static Date parseDate(String dateInput) {
        String pattern = "dd-MM-yyyy HHmm";
        SimpleDateFormat formattedDate = new SimpleDateFormat(pattern);
        Date date = new Date();

        try {
            date = formattedDate.parse(dateInput);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

//    public static void main(String args[]) {
//        while (true) {
//            ArrayList<String> words = new ArrayList<>();
//            Scanner in = new Scanner(System.in);
//            String s = in.nextLine();
//            if (s.equals("schedule")) {
//                System.out.println("Please enter the list of words.");
//                System.out.println("Enter an empty line to end input");
//                String word = in.nextLine();
//                while (!word.equals("")) {
//                    words.add(word);
//                    word = in.nextLine();
//                }
//                System.out.println("You have entered these words:");
//                for (String wordEntered : words) {
//                    System.out.println(wordEntered);
//                }
//                System.out.println("Please enter the date and time you wish to be reminded in the format:\n "
//                        + "DD-MM-YYYY MMHH");
//                String dateInput = in.nextLine();
//                Date date = parseDate(dateInput);
//                System.out.println(date);
//                new reminder.Reminder(date);
//            }
//        }
//    }
}
