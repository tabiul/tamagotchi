package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.stat.HealthStat;
import com.tabiul.tamagotchi.stat.Stat;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class CleanEventTest {

    @Test
    public void testCleanEventWhenThereisNoPoo(){
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setCleanPooWithinHour(1);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        CleanEvent cleanEvent = new CleanEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = cleanEvent.action(2);
        assertTrue(!not.isPresent());
    }

    @Test
    public void testCleanEventWhenThereisPoo(){
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setCleanPooWithinHour(1);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        CleanEvent cleanEvent = new CleanEvent(pet, configuration, consumer);
        pet.addEvent(Event.EventType.POOP_EVENT, 1);
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        Optional<Notification> not;
        not = cleanEvent.action(2);
        assertTrue(not.isPresent());
        assertEquals("thanks for cleaning the poo so fast", not.get().getMessage());
        assertEquals(configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH).getStat());
        assertFalse(pet.whenEventHappen(Event.EventType.POOP_EVENT).isPresent());
    }

    @Test
    public void testCleanEventWhenThereisPooButTooLate(){
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setCleanPooWithinHour(1);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        CleanEvent cleanEvent = new CleanEvent(pet, configuration, consumer);
        pet.addEvent(Event.EventType.POOP_EVENT, 1);
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        Optional<Notification> not;
        not = cleanEvent.action(3);
        assertTrue(not.isPresent());
        assertEquals("thanks for cleaning the poo so but sad that it took so long", not.get().getMessage());
        assertEquals(-configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH).getStat());
        assertFalse(pet.whenEventHappen(Event.EventType.POOP_EVENT).isPresent());
    }
}
