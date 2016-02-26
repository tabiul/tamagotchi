package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.Stat.Stat;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * This is a user event to clean the poo.
 * If the poo cleans < 1 hr , +ve health
 * else nothing happens
 */
public class CleanEvent extends Event {
    public CleanEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        pet.addEvent(EventType.CLEAN_EVENT, currTick);
        long pooTime = pet.whenEventHappen(EventType.POOP_EVENT);
        double diff = timeUtils.hour(pooTime, currTick);
        Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
        long healthValue = configuration.getHealthValue();
        if (diff < 1) {
            healthStat.updateStat(healthStat.getStat() + healthValue);
            return Optional.of(new Notification("thanks for cleaning the poo"));
        }
        return Optional.empty();
    }
}
