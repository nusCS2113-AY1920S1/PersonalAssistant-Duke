
class Time {
    private String representation;
    public Time(String time){
        int t = Integer.parseInt(time);
        String period;
        int h = t / 100;
        int m = t % 100;
        String minute;
        if (h <= 12){
            period = "am";
        }
        else{
            h-= 12;
            period = "pm";
        }
        minute = (m <= 9)? "0" + Integer.toString(m) : Integer.toString(m);
        representation = h + "." + minute + period;
    }

    @Override
    public String toString(){
        return representation;
    }

}
