package com.tabiul.tamagotchi.util;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class Time {
    private final Configuration configuration;

    public Time(Configuration configuration) {

        this.configuration = configuration;
    }

    public double second(long from, long to) {
        long diff = to - from;
        return diff * configuration.getTickPerSecond();
    }

    public double minute(long from, long to) {

        return second(from, to) / 60;
    }

    public double hour(long from, long to) {

        return minute(from, to) / 60;
    }

    public double day(long from, long to) {

        return hour(from, to) / 24;
    }

    public double week(long from, long to) {

        return day(from, to) / 7;
    }

    public double month(long from, long to) {

        return day(from, to) / 30;
    }

    public double year(long from, long to) {

        return month(from, to) / 12;
    }
}
