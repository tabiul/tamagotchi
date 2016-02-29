package com.tabiul.tamagotchi.stat;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class HappinessStat implements Stat {
    private long val = 0;

    @Override
    public String name() {
        return "happiness";
    }

    @Override
    public void updateStat(long val) {
        this.val = val;
    }

    @Override
    public long getStat() {
        return val;
    }

    @Override
    public String toString() {
        return "HappinessStat{" +
            "val=" + val +
            '}';
    }
}
