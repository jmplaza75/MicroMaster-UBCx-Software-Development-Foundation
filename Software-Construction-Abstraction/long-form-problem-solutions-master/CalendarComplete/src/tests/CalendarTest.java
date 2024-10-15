package tests;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalendarTest {

    private Calendar calendar;
    Date d;

    @Before
    public void setUp(){
        d = new Date(15, 8, 2017);
        calendar = new Calendar(d);
    }

    @Test
    public void testConstructor(){
        List<Entry> entries = new ArrayList<>();
        assertEquals(entries, calendar.getEntries());
        assertEquals(d, calendar.getCurrentDate());
    }

    @Test
    public void testEmail(){
        calendar.setEmail("adev@hotmail.com");
        assertEquals("adev@hotmail.com", calendar.getEmail());
        calendar.setEmail("michela@ubcxsc.org");
        assertEquals("michela@ubcxsc.org", calendar.getEmail());
    }

    @Test
    public void testEntries(){
        Date nextWeek = new Date(21, 8, 2017);
        Time noon = new Time(12, 0);
        Time evening = new Time(20, 30);
        Entry e1 = new Reminder(nextWeek, noon,"Buy groceries");
        Entry e2 = new Event(nextWeek, evening, "Dinner at BurgerLand");
        Entry e3 = new Meeting(nextWeek, noon, "Lunch meeting with design team");
        calendar.addEntry(e1);
        calendar.addEntry(e2);
        calendar.addEntry(e3);
        assertEquals(3, calendar.getEntries().size());
        assertTrue(calendar.getEntries().contains(e2));
        calendar.removeEntry(e3);
        assertEquals(2, calendar.getEntries().size());
    }
}
