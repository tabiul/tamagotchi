package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.stat.HappinessStat;
import com.tabiul.tamagotchi.stat.HealthStat;
import com.tabiul.tamagotchi.stat.Stat;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class SleepEventTest {

    @Test
    public void testSleepWhenNotTimeToSleep() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        pet.addStat(Stat.StatType.HAPPINESS, new HappinessStat());
        SleepEvent sleepEvent = new SleepEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = sleepEvent.action(12);
        assertTrue(not.isPresent());
        assertEquals("too early to go to bed, not happy", not.get().getMessage());
        assertTrue(pet.getStat(Stat.StatType.HAPPINESS).getStat() < 0);
    }

    @Test
    public void testSleepWhenTimeToSleep() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        pet.addStat(Stat.StatType.HAPPINESS, new HappinessStat());
        SleepEvent sleepEvent = new SleepEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = sleepEvent.action(13);
        assertTrue(not.isPresent());
        assertEquals("pet is going to bed, good night, sweet dreams", not.get().getMessage
            ());
        assertEquals(0, pet.getStat(Stat.StatType.HAPPINESS).getStat());
    }
}

