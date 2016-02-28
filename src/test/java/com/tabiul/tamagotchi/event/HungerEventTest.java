package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
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
public class HungerEventTest {

    @Test
    public void testPetSleeping() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        HungerEvent hungerEvent = new HungerEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        pet.setState(Pet.State.SLEEPING);
        Optional<Notification> not;
        not = hungerEvent.action(4);
        assertFalse(not.isPresent());
        assertEquals(configuration.getHealthValue(), pet.getStat(Stat.StatType.HEALTH)
            .getStat());
    }

    @Test
    public void testPetAwake() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = Configuration.newInstance();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        configuration.setHealthValue(10);
        configuration.setHappinessValue(10);
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        HungerEvent hungerEvent = new HungerEvent(pet, configuration, consumer);
        Stat healthStat = new HealthStat();
        healthStat.updateStat(configuration.getHealthValue());
        pet.addStat(Stat.StatType.HEALTH, healthStat);
        Optional<Notification> not;
        not = hungerEvent.action(5);
        assertTrue(not.isPresent());
        assertEquals("I am hungry!!!!!!!!", not.get().getMessage());
        assertEquals(0, pet.getStat(Stat.StatType.HEALTH).getStat());
    }
}
