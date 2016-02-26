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
 * time diff between poo must be 1 hr
 * this event checks every 1 hr to see if the poo has been cleaned or not
 */
public class PoopCheckEvent extends Event {
    private long lastCheck = 0;

    public PoopCheckEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        double diff = timeUtils.hour(lastCheck, currTick);
        Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
        long healthValue = configuration.getHealthValue();
        if (diff > 1) {
            long lastPoo = pet.whenEventHappen(EventType.POOP_EVENT);
            long lastClean = pet.whenEventHappen(EventType.CLEAN_EVENT);
            if (lastPoo > lastClean) { // pet has pooed
                double notCleanDiff = timeUtils.hour(lastPoo, currTick);
                if (notCleanDiff > 1) {
                    healthStat.updateStat(healthStat.getStat() - healthValue);
                    return Optional.of(new Notification("please clean my poo"));
                }
            }
        }
        return Optional.empty();
    }
}
