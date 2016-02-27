package com.tabiul.tamagotchi.stat;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class HealthStat implements Stat {
    private long val = 0;

    @Override
    public String name() {

        return "health";
    }

    @Override
    public void updateStat(long val) {
        this.val = val;
    }

    @Override
    public long getStat() {

        return this.val;
    }
}
