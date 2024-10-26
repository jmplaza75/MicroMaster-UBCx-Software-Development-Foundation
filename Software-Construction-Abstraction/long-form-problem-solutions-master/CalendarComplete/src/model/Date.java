package model;

public class Date {

    public int month;
    public int date;
    public int year;

    //REQUIRES: 0 < date < 32, 0 < month < 13, 0 < year
    public Date(int date, int month, int year){
        this.date = date;
        this.month = month;
        this.year = year;
    }

    //getters
    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getYear() {
        return year;
    }

    //EFFECTS: returns date in format "DD/MM/YYYY"
    public String getDateShort(){
        return date + "/" + month + "/" + year;
    }

    //EFFECTS: returns date in format "MonthName DD, YYYY"
    public String getDatePretty(){
        String monthName = "";
        switch(month) {
            case (1):
                monthName = "January";
                break;
            case (2):
                monthName = "February";
                break;
            case (3):
                monthName = "March";
                break;
            case (4):
                monthName = "April";
                break;
            case (5):
                monthName = "May";
                break;
            case (6):
                monthName = "June";
                break;
            case (7):
                monthName = "July";
                break;
            case (8):
                monthName = "August";
                break;
            case (9):
                monthName = "September";
                break;
            case (10):
                monthName = "October";
                break;
            case (11):
                monthName = "November";
                break;
            case (12):
                monthName = "December";
                break;
        }
        return monthName + " " + date + ordinalIndicator() + ", " + year;
    }

    //EFFECTS: returns the correct ordinal indicator for the date
    private String ordinalIndicator(){
        if (date >= 10 && date <= 20)
            return "th";

        int determinant = date % 10;
        switch(determinant){
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
