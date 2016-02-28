package com.tabiul.tamagotchi.util;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class Configuration {
    private long tickPerSecond = 1;
    private long howOftenGenerateTick = 1000;
    private long maxAge = 100;
    private long healthValue = 10;
    private long happinessValue = 10;
    private long cleanPooWithinHour = 1;

    private Configuration() {

    }

    public static Configuration newInstance() {
        return new Configuration();
    }

    public long getTickPerSecond() {

        return tickPerSecond;
    }

    public void setTickPerSecond(long tickPerSecond) {
        this.tickPerSecond = tickPerSecond;
    }

    public long getHowOftenGenerateTick() {

        return howOftenGenerateTick;
    }

    public void setHowOftenGenerateTick(long howOftenGenerateTick) {
        this.howOftenGenerateTick = howOftenGenerateTick;
    }

    public long getMaxAge() {

        return maxAge;
    }

    public void setMaxAge(long maxAge) {

        this.maxAge = maxAge;
    }

    public long getHealthValue() {

        return healthValue;
    }

    public void setHealthValue(long healthValue) {
        this.healthValue = healthValue;
    }

    public long getHappinessValue() {

        return happinessValue;
    }

    public void setHappinessValue(long happinessValue) {
        this.happinessValue = happinessValue;
    }

    public long getCleanPooWithinHour() {
        return cleanPooWithinHour;
    }

    public void setCleanPooWithinHour(long cleanPooWithinHour) {
        this.cleanPooWithinHour = cleanPooWithinHour;
    }
}
