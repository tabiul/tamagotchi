package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.stat.HealthStat;
import com.tabiul.tamagotchi.stat.Stat;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class PoopCheckEventTest {

    @Test
    public void testCheckEvery30Mins() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(1800); // 1 tick = 30 min
        configuration.setHealthValue(10);
        configuration.setCleanPooWithinHour(1);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.addEvent(Event.EventType.POOP_EVENT, 0);
        HealthStat stat = new HealthStat();
        pet.addStat(Stat.StatType.HEALTH, stat);
        stat.updateStat(10);
        PoopCheckEvent poopCheckEvent = new PoopCheckEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = poopCheckEvent.action(1);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(2);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(3);
        assertTrue(not.isPresent());
        not = poopCheckEvent.action(4);
        assertFalse(not.isPresent());
        assertEquals(0, pet.getStat(Stat.StatType.HEALTH).getStat());
    }

    @Test
    public void testNoPoo() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(1800); // 1 tick = 30 min
        configuration.setHealthValue(10);
        configuration.setCleanPooWithinHour(1);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        HealthStat stat = new HealthStat();
        pet.addStat(Stat.StatType.HEALTH, stat);
        stat.updateStat(10);
        PoopCheckEvent poopCheckEvent = new PoopCheckEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = poopCheckEvent.action(1);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(2);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(3);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(4);
        assertFalse(not.isPresent());

    }

    @Test
    public void testPooWithClean() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(1800); // 1 tick = 30 min
        configuration.setHealthValue(10);
        configuration.setCleanPooWithinHour(1);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.addEvent(Event.EventType.POOP_EVENT, 0);
        HealthStat stat = new HealthStat();
        pet.addStat(Stat.StatType.HEALTH, stat);
        stat.updateStat(10);
        PoopCheckEvent poopCheckEvent = new PoopCheckEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = poopCheckEvent.action(1);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(2);
        assertFalse(not.isPresent());
        CleanEvent cleanEvent = new CleanEvent(pet, configuration, consumer);
        cleanEvent.action(2);
        not = poopCheckEvent.action(3);
        assertFalse(not.isPresent());
        not = poopCheckEvent.action(4);
        assertFalse(not.isPresent());
        assertEquals(20, pet.getStat(Stat.StatType.HEALTH).getStat());

    }
}
