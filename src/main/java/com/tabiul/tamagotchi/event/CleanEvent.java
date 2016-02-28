package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
import com.tabiul.tamagotchi.Pet;

import java.util.Optional;
import java.util.function.Consumer;

import com.tabiul.tamagotchi.stat.Stat;
/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * This is a user event to clean the poo.
 * If the poo cleans < 1 hr , +ve health
 * else nothing happens
 */
public class CleanEvent extends Event {
    public CleanEvent(Pet pet, Configuration configuration, Consumer<Class<? extends
        Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        pet.addEvent(EventType.CLEAN_EVENT, currTick);
        Optional<Long> optional = pet.whenEventHappen(EventType.POOP_EVENT);
        if (optional.isPresent()) { // means there is poo
            long pooTime = optional.get();
            pet.removeEvent(EventType.POOP_EVENT);
            double diff = time.hour(pooTime, currTick);
            Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
            long healthValue = configuration.getHealthValue();
            if (diff <= configuration.getCleanPooWithinHour()) {
                healthStat.updateStat(healthStat.getStat() + healthValue);
                return Optional.of(new Notification("thanks for cleaning the poo so " +
                    "fast"));
            }
            healthStat.updateStat(healthStat.getStat() - healthValue);
            return Optional.of(new Notification("thanks for cleaning the poo so " +
                "but sad that it took so long"));
        }
        return Optional.empty();
    }
}
