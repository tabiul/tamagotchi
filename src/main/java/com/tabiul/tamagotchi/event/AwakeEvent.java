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
 * This is an event that moves the pet from sleep to awake after 8 hr of sleep
 */
public class AwakeEvent extends Event {
    public AwakeEvent(Pet pet, Configuration configuration, Consumer<Class<? extends
        Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        if (pet.getState() == Pet.State.SLEEPING) {
            Optional<Long> optional = pet.whenEventHappen(EventType.AWAKE_EVENT);
            if (!optional.isPresent()) {
                throw new RuntimeException("expected a awake event before a sleep event");
            }
            long awakeTick = optional.get();
            double diff = timeUtils.hour(awakeTick, currTick);
            if (diff >= 8) {
                pet.setState(Pet.State.AWAKE);
                pet.addEvent(EventType.AWAKE_EVENT, currTick);
                return Optional.of(new Notification("waky, waky......."));
            }
        }
        return Optional.empty();
    }
}
