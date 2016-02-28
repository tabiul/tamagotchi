package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.stat.Stat;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * This is a user generated event, when the user decides to feed the pet
 * If the duration from last feed is less than 2 hr then the health decreases
 * due to over feeding else health increases
 * If tries to feed when the pet is sleeping then happiness goes down
 */
public class FeedEvent extends Event {


    public FeedEvent(Pet pet, Configuration configuration, Consumer<Class<? extends
        Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {

        if (pet.getState() == Pet.State.SLEEPING) {
            pet.addEvent(EventType.FEED_EVENT, currTick);
            Stat happinessStat = pet.getStat(Stat.StatType.HAPPINESS);
            long happinessValue = configuration.getHappinessValue();
            happinessStat.updateStat(happinessStat.getStat() - happinessValue);
            Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
            long healthValue = configuration.getHealthValue();
            healthStat.updateStat(healthStat.getStat() + healthValue);
            return Optional.of(new Notification("pet is not happy as you tried to feed " +
                "it when it was sleeping"));
        } else {
            Optional<Long> lastFeedOptional = pet.whenEventHappen(EventType.FEED_EVENT);
            Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
            long healthValue = configuration.getHealthValue();
            if (lastFeedOptional.isPresent()) {
                long lastFeedTick = lastFeedOptional.get();
                double diffHour = time.hour(lastFeedTick, currTick);
                if (diffHour <= 2) { // last time feed is less than 2 hr
                    healthStat.updateStat(healthStat.getStat() - healthValue);
                    pet.addEvent(EventType.FEED_EVENT, currTick);
                    return Optional.of(new Notification("pet is being over feed thus " +
                        "reducing health"));
                }
            }
            healthStat.updateStat(healthStat.getStat() + healthValue);
            pet.addEvent(EventType.FEED_EVENT, currTick);
            return Optional.of(new Notification("pet have been feed"));
        }
    }
}
