package duke;
class DatePattern {
    enum TimePatternType { 
        DAY_OF_WEEK,DATE_TIME,DATE, TIME
    } 
    private String pattern; 
    private ParseTime.TimePatternType type; 
    DatePattern(String Pattern, ParseTime.TimePatternType type_date )  { 
        pattern = Pattern; 
        type  = type_date; 
    }
    public ParseTime.TimePatternType get_type(){ 
        return type; 
    }
    public String get_pattern(){ 
        return pattern; 
    }
}