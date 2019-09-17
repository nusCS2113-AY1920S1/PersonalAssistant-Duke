enum Months{
    January, February, March, April, May, June, July, August, September, October, November, December;
}
public class Date {
    private String representation;
    public Date(String date){
        String arr[] = date.split("/",3);
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        Months list[] = Months.values();
        if (day % 10 == 1){
            arr[0] += "st";
        }
        else if (day % 10 == 2){
            arr[0] += "nd";
        }
        else if (day % 10 == 3){
            arr[0] += "rd";
        }
        else{
            arr[0] += "th";
        }
        arr[1] = list[month - 1].name();
        representation = arr[0] + " of " + arr[1] + " " + arr[2];
    }

    @Override
    public String toString(){
        return representation;
    }
}
