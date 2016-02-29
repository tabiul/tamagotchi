package com.tabiul.tamagotchi.util;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Time;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class TimeTest {

    @Test
    public void testSecond() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(10);
        Time time = new Time(configuration);
        assertEquals(10, time.second(0, 1), 0.0);
        assertEquals(20, time.second(0, 2), 0.0);
    }

    @Test
    public void testMinute() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(10);
        Time time = new Time(configuration);
        assertEquals(1, time.minute(0, 6), 0.0); // 60 second
        assertEquals(1.33, time.minute(0, 8), 0.1); // 80 second
    }

    @Test
    public void testHour() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(10);
        Time time = new Time(configuration);
        assertEquals(1, time.hour(0, 360), 0.0); // 1 hr
        assertEquals(1.5, time.hour(0, 540), 0.1); // 1.5 hr
    }

    @Test
    public void testDay() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(10);
        Time time = new Time(configuration);
        assertEquals(1, time.day(0, 8640), 0.0); // 1 day
        assertEquals(1.5, time.day(0, 12960), 0.1); // 1.5 day
    }

    @Test
    public void testMonth() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(86400); // 1  tick = 1 day
        Time time = new Time(configuration);
        assertEquals(1, time.month(0, 30), 0.0); // 1 month
        assertEquals(1.5, time.month(0, 45), 0.1); // 1.5 month
    }

    @Test
    public void testWeek() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(86400); // 1  tick = 1 day
        Time time = new Time(configuration);
        assertEquals(1, time.week(0, 7), 0.0); // 1 week
        assertEquals(1.5, time.week(0, 10), 0.1); // 1.5 month
    }

    @Test
    public void testYear() {
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(86400); // 1  tick = 1 day
        Time time = new Time(configuration);
        assertEquals(1, time.year(0, 365), 0.1); // 1 year
        assertEquals(1.5, time.year(0, 547), 0.1); // 1.5 year
    }

}
