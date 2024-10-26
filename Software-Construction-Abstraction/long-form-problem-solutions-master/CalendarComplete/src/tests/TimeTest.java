package tests;

import model.Time;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeTest {

    private Time time1;
    private Time time2;
    private Time time3;
    private Time time4;

    @Before
    public void setUp(){
        time1 = new Time(0, 0);
        time2 = new Time(10, 13);
        time3 = new Time(20, 45);
        time4 = new Time(13, 2);
    }

    @Test
    public void testConstructor(){
        assertEquals("00", time1.getHours());
        assertEquals("00", time1.getMinutes());

        assertEquals("10", time2.getHours());
        assertEquals("13", time2.getMinutes());

        assertEquals("20", time3.getHours());
        assertEquals("45", time3.getMinutes());
    }

    @Test
    public void  testTimeIn24Hours(){
        assertEquals("00:00", time1.timeIn24Hours());
        assertEquals("10:13", time2.timeIn24Hours());
        assertEquals("20:45", time3.timeIn24Hours());
        assertEquals("13:02", time4.timeIn24Hours());
    }

    @Test
    public void testTimeIn12Hours(){
        assertEquals("12:00", time1.timeIn12Hours());
        assertEquals("10:13", time2.timeIn12Hours());
        assertEquals("8:45", time3.timeIn12Hours());
        assertEquals("1:02", time4.timeIn12Hours());
        time4 = new Time(12, 5);
        assertEquals("12:05", time4.timeIn12Hours());
    }
}
