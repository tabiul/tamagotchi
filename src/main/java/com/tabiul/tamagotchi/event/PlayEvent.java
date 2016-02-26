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
 * This simulates the user playing with the pet
 * If the user plays with the pet every 2 hr or so when the pet is awake the + happiness
 * If the user tries to play when the pet is sleeping -happiness
 * if the user tries to play within 1 hr since last played then -health
 */
public class PlayEvent extends Event {

    public PlayEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        Stat happinessStat = pet.getStat(Stat.StatType.HAPPINESS);
        long happinessValue = configuration.getHappinessValue();
        Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
        long healthValue = configuration.getHealthValue();
        long lastPlayed = pet.whenEventHappen(EventType.PLAY_EVENT);

        if (pet.getState() == Pet.State.SLEEPING) {
            happinessStat.updateStat(happinessStat.getStat() - happinessValue);
            return Optional.of(new Notification("no playing when sleeping"));
        } else {
            double diff = timeUtils.hour(lastPlayed, currTick);
            pet.addEvent(EventType.PLAY_EVENT, currTick);
            if (diff < 2) {
                healthStat.updateStat(healthStat.getStat() - healthValue);
                return Optional.of(new Notification("too tiring"));
            } else {
                happinessStat.updateStat(happinessStat.getStat() + happinessValue);
                return Optional.of(new Notification("let's play"));
            }
        }
    }
}

