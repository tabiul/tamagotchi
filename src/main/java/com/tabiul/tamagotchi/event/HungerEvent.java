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
 * This class simulates the event of hunger. For simplicity sake, we assume that
 * the pet gets hungry every 4 hr if the pet is not sleeping
 */
public class HungerEvent extends Event {
    private static final int feedDuration = 4; // 4 hr

    public HungerEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        long lastFeedTick = pet.whenEventHappen(EventType.FEED_EVENT);
        double diffHour = timeUtils.hour(lastFeedTick, currTick);
        if (pet.getState() == Pet.State.AWAKE) {
            if (diffHour > feedDuration) { // it has been more than 4 hr since last feed
                // the pet is awake
                Stat hungerStat = pet.getStat(Stat.StatType.HEALTH);
                long healthValue = configuration.getHealthValue();
                hungerStat.updateStat(hungerStat.getStat() - healthValue);
                return Optional.of(new Notification("I am hungry!!!!!!!!"));
            }
        }
        return Optional.empty();
    }
}
