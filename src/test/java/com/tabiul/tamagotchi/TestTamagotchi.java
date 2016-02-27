package com.tabiul.tamagotchi;

import com.tabiul.tamagotchi.stat.HappinessStat;
import com.tabiul.tamagotchi.stat.HealthStat;
import com.tabiul.tamagotchi.stat.Stat;
import com.tabiul.tamagotchi.event.FeedEvent;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class TestTamagotchi {
    @Test
    public void testDieOfHunger() {
        Configuration configuration = new Configuration();
        configuration.setHowOftenGenerateTick(1);
        configuration.setTickPerSecond(60 * 60); // 1 tick = 1 hr
        Pet pet = new Pet("tabiul", "male");
        HappinessStat happinessStat = new HappinessStat();
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);
        HealthStat healthStat = new HealthStat();
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        Observer observer = (obs, arg) -> {
            System.out.println(((Notification) arg).getMessage());
        };
        try (Universe universe = new Universe(pet, configuration)) {
            universe.addObserver(observer);
            universe.bigBang();
            //wait till the death of universe
            while (!universe.isUniverseDead()) {
                //do nothing
            }
        }
        // the value for hunger stat should go < 0;
        assertTrue(pet.getStat(Stat.StatType.HEALTH).getStat() < 0);
    }

    @Test
    public void testDieOfOldAge() throws InvocationTargetException,
        NoSuchMethodException, InstantiationException, IllegalAccessException {
        Configuration configuration = new Configuration();
        configuration.setHowOftenGenerateTick(1);
        configuration.setTickPerSecond(60 * 60 * 24 * 30); // 1 tick = 1 month
        TimeUtils timeUtils = new TimeUtils(configuration);
        Pet pet = new Pet("tabiul", "male", 100);
        HappinessStat happinessStat = new HappinessStat();
        happinessStat.updateStat(100);
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);
        HealthStat healthStat = new HealthStat();
        healthStat.updateStat(100);
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        List<String> messages = new ArrayList<>();
        Observer observer = (obs, arg) -> {
            messages.add(((Notification) arg).getMessage());
        };
        try (Universe universe = new Universe(pet, configuration)) {
            universe.addObserver(observer);
            universe.bigBang();
            //wait till the death of universe
            long lastTick = 0;
            while (!universe.isUniverseDead()) {
                long tick = universe.getTick();
                double diff = timeUtils.month(lastTick, tick);
                if (diff > 1) {
                    universe.addEvent(FeedEvent.class);
                    lastTick = tick;
                }

            }
        }
        assertTrue(!messages.isEmpty());
        assertEquals(101, pet.getAge());
        assertTrue(messages.get(messages.size() - 1).contains("age"));
    }

}

