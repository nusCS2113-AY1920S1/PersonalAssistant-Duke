package parser;

public enum Flag {
    BY("/by"),
    AT("/at"),
    BETWEEN("/between"), FOR("/for");

    private String flag;

    Flag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}



