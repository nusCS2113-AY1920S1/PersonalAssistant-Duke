package duke.data;

import java.util.ArrayList;

public class BusService {
    private String ServiceNo;
    private ArrayList<String> Direction1;
    private ArrayList<String> Direction2;

    public BusService (String ServiceNo) {
        this.ServiceNo = ServiceNo;
        this.Direction1 = new ArrayList<>();
        this.Direction2 = new ArrayList<>();
    }

    public void addRoute (String buscode, int direction) {
        if (direction == 1) {
            this.Direction1.add(buscode);
        } else {
            this.Direction2.add(buscode);
        }
    }

}
