package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
import com.tabiul.tamagotchi.Pet;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * This event takes care of increasing the age of the pet
 */
public class AgeEvent extends Event {

    public AgeEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        long lastAgeEvent = 0;
        Optional<Long> optional = pet.whenEventHappen(EventType.AGE_EVENT);
        if (optional.isPresent()) {
            lastAgeEvent = optional.get();
        }
        if (time.year(lastAgeEvent, currTick) >= 1) {
            pet.addEvent(EventType.AGE_EVENT, currTick);
            pet.setAge(pet.getAge() + 1);
            return Optional.of(new Notification("happy birthday " + pet.getName() + ", " +
                "you are " + pet.getAge() + " years old today"));
        }
        return Optional.empty();
    }
}
