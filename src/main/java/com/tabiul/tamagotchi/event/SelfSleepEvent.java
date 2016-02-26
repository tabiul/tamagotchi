
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
 * this is event happens if the pet has been awake more than 14 hr
 * and falls sleep on its own
 */
public class SelfSleepEvent extends Event {

    public SelfSleepEvent(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        super(pet, configuration, generateEvent);
    }

    @Override
    public Optional<Notification> action(long currTick) {
        if (Pet.State.SLEEPING != pet.getState()) {
            long awakeEvent = pet.whenEventHappen(EventType.AWAKE_EVENT);
            double diff = timeUtils.hour(awakeEvent, currTick);
            if (diff > 14) {
                pet.setState(Pet.State.SLEEPING);
                pet.addEvent(EventType.SLEEP_EVENT, currTick);
                return Optional.of(new Notification("pet falls sleep on its own"));
            }
        }
        return Optional.empty();
    }
}
