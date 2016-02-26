package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.Stat.Stat;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */

/**
 * This event takes care of increasing the age of the pet
 */
public class AgeEvent extends Event {
    private long lastTick = 0;

    public AgeEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        if (timeUtils.year(lastTick, currTick) > 1) {
            lastTick = currTick;
            pet.setAge(pet.getAge() + 1);
            return Optional.of(new Notification("happy birthday " + pet.getName() + ", " +
                "you are " + pet.getAge() + " years old today"));
        }
        return Optional.empty();
    }
}
