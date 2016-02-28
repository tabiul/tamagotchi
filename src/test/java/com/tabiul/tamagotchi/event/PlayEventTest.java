package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
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
public class PlayEventTest {

    @Test
    public void playWhenSleeping() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        PlayEvent playEvent = new PlayEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);

        Stat happinessStat = new HappinessStat();
        happinessStat.updateStat(configuration.getHappinessValue());
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);

        pet.setState(Pet.State.SLEEPING);

        Optional<Notification> not;
        not = playEvent.action(1);
        assertTrue(not.isPresent());
        assertEquals("no playing when sleeping", not.get().getMessage());
        assertEquals(0, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH)
            .getStat());
    }

    @Test
    public void playTooOften() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        PlayEvent playEvent = new PlayEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);

        Stat happinessStat = new HappinessStat();
        happinessStat.updateStat(configuration.getHappinessValue());
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);

        Optional<Notification> not;
        not = playEvent.action(1);
        assertTrue(not.isPresent());
        assertEquals("let's play", not.get().getMessage());
        assertEquals(20, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH)
            .getStat());

        not = playEvent.action(2);
        assertTrue(not.isPresent());
        assertEquals("too tiring", not.get().getMessage());
        assertEquals(20, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(0, pet.getStat(Stat.StatType.HEALTH)
            .getStat());
    }

    @Test
    public void playNormal() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        PlayEvent playEvent = new PlayEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);

        Stat happinessStat = new HappinessStat();
        happinessStat.updateStat(configuration.getHappinessValue());
        pet.addStat(Stat.StatType.HAPPINESS, happinessStat);

        Optional<Notification> not;
        not = playEvent.action(1);
        assertTrue(not.isPresent());
        assertEquals("let's play", not.get().getMessage());
        assertEquals(20, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH)
            .getStat());

        not = playEvent.action(3);
        assertTrue(not.isPresent());
        assertEquals("let's play", not.get().getMessage());
        assertEquals(30, pet.getStat(Stat.StatType.HAPPINESS).getStat());
        assertEquals(configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH)
            .getStat());
    }
}
