package ui;

import model.*;

public class Main {

    public static void main (String[] args) {
        Calendar myCal = new Calendar(new Date(15, 8, 2017));
        myCal.setEmail("iamaperson@humans.net");

        Date tomorrow = new Date(16, 8, 2017);
        Date nextWeek = new Date(3, 9, 2017);
        Date nextMonth = new Date(20, 9, 2017);

        Reminder coffee = new Reminder(tomorrow, new Time(6, 0), "Buy coffee");
        coffee.setNote("Flat bottom filters, dark roast");
        myCal.addEntry(coffee);
        myCal.addEntry(new Event(nextWeek, new Time(15, 30), "Doctor's appt"));
        Meeting m = new Meeting(nextMonth, new Time(9, 0), "Data abstraction scrum");
        m.addAttendee("erikat@ubcxsc.com");
        m.addAttendee("amyz@ubcxsc.com");
        m.addAttendee("jamesy@ubcxsc.com");
        myCal.addEntry(m);

        m.sendInvites();
        System.out.println();

        myCal.printEntries();
    }
}
