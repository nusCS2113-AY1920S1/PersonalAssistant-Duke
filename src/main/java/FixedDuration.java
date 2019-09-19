import java.util.Timer;
import java.util.TimerTask;

public class FixedDuration extends Task {
    Timer timer;
    private String duration;
//    RemindTask rt = new RemindTask();

    public FixedDuration(String name, String duration) {
        super(name);
        this.duration = duration;
//        int seconds = Integer.parseInt(duration);
//        timer = new Timer();
//        timer.schedule(rt, seconds * 1000);
    }

//    class RemindTask extends TimerTask {
//        public void run() {
//            System.out.println(getName() + " is completed");
//            timer.cancel(); //Terminate the timer thread
//        }
//    }

    @Override
    public String toString(){
        return "[TT] " + super.toString() + " (done in: " + duration + " minutes )";
    }

    @Override
    public String saveForm(){
        return "[TT]:"+ super.tick() + ":" + super.getName() + ":" +  duration;
    }
}
