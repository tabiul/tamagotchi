package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * The pet poos every day once
 */
public class PoopEvent extends Event {

    public PoopEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        long lastPoo = pet.whenEventHappen(EventType.POOP_EVENT);
        double day = timeUtils.day(lastPoo, currTick);
        if (day > 1) {
            pet.addEvent(EventType.POOP_EVENT, currTick);
            return Optional.of(new Notification("I have done po po"));
        }
        return Optional.empty();
    }
}
