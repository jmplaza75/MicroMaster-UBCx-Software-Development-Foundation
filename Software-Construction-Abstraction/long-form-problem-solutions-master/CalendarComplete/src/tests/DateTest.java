package tests;

import model.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DateTest {

    private Date testDate;

    @Before
    public void setUp(){
        testDate = new Date(1, 1, 2018);
    }

    @Test
    public void testConstructor(){
        assertEquals(1, testDate.getDate(), testDate.getMonth());
        assertEquals(2018, testDate.getYear());
        testDate = new Date (3, 6, 2017);
        assertEquals(3, testDate.getDate());
        assertEquals(6, testDate.getMonth());
        assertEquals(2017, testDate.getYear());
    }

    @Test
    public void testGetDateShort(){
        assertEquals("1/1/2018", testDate.getDateShort());
        testDate = new Date(20, 12, 2017);
        assertEquals("20/12/2017", testDate.getDateShort());
    }

    @Test
    public void testGetDatePretty(){
        assertEquals("January 1st, 2018", testDate.getDatePretty());
        testDate = new Date(2, 2, 2018);
        assertEquals("February 2nd, 2018", testDate.getDatePretty());
        testDate = new Date (3, 3, 2017);
        assertEquals("March 3rd, 2017", testDate.getDatePretty());
        testDate = new Date(13, 4, 2020);
        assertEquals("April 13th, 2020", testDate.getDatePretty());
        testDate = new Date(4, 5, 2018);
        assertEquals("May 4th, 2018", testDate.getDatePretty());
        testDate = new Date(16, 6, 1952);
        assertEquals("June 16th, 1952", testDate.getDatePretty());
        testDate = new Date(27, 7, 2012);
        assertEquals("July 27th, 2012", testDate.getDatePretty());
        testDate = new Date(12, 8, 2017);
        assertEquals("August 12th, 2017", testDate.getDatePretty());
        testDate = new Date(13, 9, 2017);
        assertEquals("September 13th, 2017", testDate.getDatePretty());
        testDate = new Date(2, 10, 2019);
        assertEquals("October 2nd, 2019", testDate.getDatePretty());
        testDate = new Date(29, 11, 2018);
        assertEquals("November 29th, 2018", testDate.getDatePretty());
        testDate = new Date(20, 12, 2017);
        assertEquals("December 20th, 2017", testDate.getDatePretty());
    }

}
