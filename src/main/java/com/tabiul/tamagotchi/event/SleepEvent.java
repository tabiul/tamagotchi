package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.stat.Stat;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * This is a user generated event, when the user tries to put the pet to bed
 * If the pet is awake for more than 12 hrs and the user put it to sleep then health
 * goes up
 * If the pet is awake and it is less than 12hr then happiness goes down
 * If the pet is already sleeping then nothing happens
 */
public class SleepEvent extends Event {

    public SleepEvent(Pet pet, Configuration configuration, Consumer<Class<? extends
        Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        Stat healthStat = pet.getStat(Stat.StatType.HEALTH);
        Stat happinessStat = pet.getStat(Stat.StatType.HAPPINESS);
        long healthValue = configuration.getHealthValue();
        long happinessValue = configuration.getHappinessValue();
        if (Pet.State.SLEEPING != pet.getState()) {
            long awakeEvent = 0;
            Optional<Long> optional = pet.whenEventHappen(EventType.AWAKE_EVENT);
            if (optional.isPresent()) {
                awakeEvent = optional.get();
            }
            double diff = timeUtils.hour(awakeEvent, currTick);
            if (diff > 12) {
                healthStat.updateStat(healthStat.getStat() + healthValue);
                pet.setState(Pet.State.SLEEPING);
                pet.addEvent(EventType.SLEEP_EVENT, currTick);
                return Optional.of(new Notification("pet is going to bed, good night, " +
                    "sweet dreams"));
            } else {
                happinessStat.updateStat(happinessStat.getStat() - happinessValue);
                return Optional.of(new Notification("too early to go to bed, not happy"));
            }
        }
        return Optional.empty();
    }
}
