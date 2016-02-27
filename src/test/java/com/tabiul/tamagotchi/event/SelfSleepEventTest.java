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
public class SelfSleepEventTest {

    @Test
    public void testSleepWhenAlreadySleeping() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.setState(Pet.State.SLEEPING);
        SelfSleepEvent selfSleepEvent = new SelfSleepEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = selfSleepEvent.action(13);
        assertFalse(not.isPresent());
        not = selfSleepEvent.action(14);
        assertFalse(not.isPresent());
        not = selfSleepEvent.action(15);
        assertFalse(not.isPresent());
    }

    @Test
    public void testSleepAfter14Hr() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        SelfSleepEvent selfSleepEvent = new SelfSleepEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = selfSleepEvent.action(13);
        assertFalse(not.isPresent());
        not = selfSleepEvent.action(14);
        assertFalse(not.isPresent());
        not = selfSleepEvent.action(15);
        assertTrue(not.isPresent());
        assertEquals("pet falls sleep on its own", not.get().getMessage());

    }

    @Test
    public void testWithUserTriggeredSleep() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        SelfSleepEvent selfSleepEvent = new SelfSleepEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = selfSleepEvent.action(13);
        assertFalse(not.isPresent());
        not = selfSleepEvent.action(14);
        assertFalse(not.isPresent());
        SleepEvent sleepEvent = new SleepEvent(pet, configuration, consumer);
        sleepEvent.action(14);
        not = selfSleepEvent.action(15);
        assertFalse(not.isPresent());

    }
}
