package Task;


public class item {

    private Boolean status;
    private String info;
    private String type;
    private String date = "";


    public item (String info, Boolean status) {
        this.status = status; //true or false
        this.info = info;
    }

    public Boolean changeStatus () {
        status = true;
        return true;
    }

    public String setType (String t) {
        this.type = t;
        return this.type;
    }

    public String getType () {
        return type;
    }

    public String setDate (String date) {

        this.date = date;
        return this.date;
    }

    public String getDate () {
        return date;
    }

    public Integer checkStatus () {
        return status ? 1 : 0;
    }

//    public int getIndex() {
//        return this.index;
//    }

    public String getInfo() {
        return this.info;
    }

    public String getStatusIcon() {
        return (status ? "\u2713" : "\u2718"); //return tick or X symbols
    }
    public String toString() {
        String s =   "[" + this.getStatusIcon() + "] " + this.getInfo();
        return s;
    }
}