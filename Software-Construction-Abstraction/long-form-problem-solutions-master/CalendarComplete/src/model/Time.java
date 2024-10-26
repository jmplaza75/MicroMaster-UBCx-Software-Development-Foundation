package model;

public class Time {
    private int hours;
    private int minutes;

    //REQUIRES: 0 <= hours < 24, 0 <= minutes < 60
    //Time is in 24 hour clock
    public Time(int hours, int minutes){
        this.hours = hours;
        this.minutes = minutes;
    }

    //EFFECTS: returns hours as string
    public String getHours(){
        return addPrefix(hours);
    }

    //EFFECTS: return minutes as string
    public String getMinutes(){
        return addPrefix(minutes);
    }

    //EFFECTS: returns time in format "hours:minutes"
    public String timeIn24Hours(){
        return addPrefix(hours) + ":" + getMinutes();
    }

    //EFFECTS: returns "hours:minutes" unless hours >12, then returns (hours-12):minutes
    public String timeIn12Hours(){
        if (hours > 12){
            return hours - 12 + ":" + getMinutes();
        } else if (hours == 0) {
            return 12 + ":" + getMinutes();
        } else {
            return hours + ":" + getMinutes();
        }
    }

    //EFFECTS: returns i as a string with a 0 prefix if i < 10
    private String addPrefix(int i){
        if (i < 10)
            return "0" + i;
        else
            return "" + i;
    }
}
