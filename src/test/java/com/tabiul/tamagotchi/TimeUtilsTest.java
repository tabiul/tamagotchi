package com.tabiul.tamagotchi;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class TimeUtilsTest {

    @Test
    public void testSecond() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(10);
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(10, timeUtils.second(0, 1), 0.0);
        assertEquals(20, timeUtils.second(0, 2), 0.0);

    }

    @Test
    public void testMinute() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(10);
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(1, timeUtils.minute(0, 6), 0.0); // 60 second
        assertEquals(1.33, timeUtils.minute(0, 8), 0.1); // 80 second
    }

    @Test
    public void testHour() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(10);
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(1, timeUtils.hour(0, 360), 0.0); // 1 hr
        assertEquals(1.5, timeUtils.hour(0, 540), 0.1); // 1.5 hr
    }

    @Test
    public void testDay() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(10);
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(1, timeUtils.day(0, 8640), 0.0); // 1 day
        assertEquals(1.5, timeUtils.day(0, 12960), 0.1); // 1.5 day
    }

    @Test
    public void testMonth() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(86400); // 1  tick = 1 day
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(1, timeUtils.month(0, 30), 0.0); // 1 month
        assertEquals(1.5, timeUtils.month(0, 45), 0.1); // 1.5 month
    }

    @Test
    public void testWeek() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(86400); // 1  tick = 1 day
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(1, timeUtils.week(0, 7), 0.0); // 1 week
        assertEquals(1.5, timeUtils.week(0, 10), 0.1); // 1.5 month
    }

    @Test
    public void testYear() {
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(86400); // 1  tick = 1 day
        TimeUtils timeUtils = new TimeUtils(configuration);
        assertEquals(1, timeUtils.year(0, 365), 0.1); // 1 year
        assertEquals(1.5, timeUtils.year(0, 547), 0.1); // 1.5 year
    }

}
