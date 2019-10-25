package entertainment.pro.model;

public class PastCommandStructure {
    String date;
    String query;

    public PastCommandStructure(String date, String query) {
        this.date = date;
        this.query = query;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
