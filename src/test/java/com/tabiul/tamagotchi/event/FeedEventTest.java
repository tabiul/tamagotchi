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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class FeedEventTest {

    @Test
    public void testTryToFeedWhenSleeping() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        FeedEvent feedEvent = new FeedEvent(pet, configuration, consumer);
        pet.setState(Pet.State.SLEEPING);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        Stat happinessStat = new HealthStat();
        happinessStat.updateStat(configuration.getHappinessValue());
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);
        Optional<Notification> not;
        not = feedEvent.action(2);
        assertTrue(not.isPresent());
        assertEquals("pet is not happy as you tried to feed it when it was sleeping",
            not.get().getMessage());
        assertEquals(0, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(20, pet.getStat(Stat.StatType.HEALTH).getStat());
        assertEquals(2, pet.whenEventHappen(Event.EventType.FEED_EVENT).get().longValue
            ());
    }

    @Test
    public void testOverFeeding() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        FeedEvent feedEvent = new FeedEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        Stat happinessStat = new HealthStat();
        happinessStat.updateStat(configuration.getHappinessValue());
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);
        Optional<Notification> not;
        not = feedEvent.action(2);
        assertTrue(not.isPresent());
        assertEquals("pet have been feed", not.get().getMessage());
        assertEquals(10, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(20, pet.getStat(Stat.StatType.HEALTH).getStat());
        assertEquals(2, pet.whenEventHappen(Event.EventType.FEED_EVENT).get().longValue
            ());
        not = feedEvent.action(4);
        assertTrue(not.isPresent());
        assertEquals("pet is being over feed thus reducing health", not.get()
            .getMessage());
        assertEquals(10, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(10, pet.getStat(Stat.StatType.HEALTH).getStat());
        assertEquals(4, pet.whenEventHappen(Event.EventType.FEED_EVENT).get().longValue
            ());
    }

    @Test
    public void testNormalFeeding() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        FeedEvent feedEvent = new FeedEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        Stat happinessStat = new HealthStat();
        happinessStat.updateStat(configuration.getHappinessValue());
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);
        Optional<Notification> not;
        not = feedEvent.action(2);
        assertTrue(not.isPresent());
        assertEquals("pet have been feed", not.get().getMessage());
        assertEquals(10, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(20, pet.getStat(Stat.StatType.HEALTH).getStat());
        assertEquals(2, pet.whenEventHappen(Event.EventType.FEED_EVENT).get().longValue
            ());
        not = feedEvent.action(5);
        assertTrue(not.isPresent());
        assertEquals("pet have been feed", not.get().getMessage());
        assertEquals(10, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(30, pet.getStat(Stat.StatType.HEALTH).getStat());
        assertEquals(5, pet.whenEventHappen(Event.EventType.FEED_EVENT).get().longValue
            ());
    }
}
