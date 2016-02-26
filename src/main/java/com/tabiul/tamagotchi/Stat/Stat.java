package com.tabiul.tamagotchi.Stat;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public interface Stat {
    enum StatType {
        HEALTH,
        HAPPINESS
    }

    /**
     * name of the stat to display to user
     *
     * @return user friendly stat name
     */
    public String name();

    public void updateStat(long val);

    public long getStat();


}

