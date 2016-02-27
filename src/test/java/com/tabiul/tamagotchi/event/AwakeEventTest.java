package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class AwakeEventTest {

    @Test
    public void testActionAwakeWhenNotSleeping() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        AwakeEvent awakeEvent = new AwakeEvent(pet, configuration, consumer);
        Optional<Notification> not;
        pet.setState(Pet.State.AWAKE);
        not = awakeEvent.action(8); // should awake up after 8 hrs
        assertTrue(!not.isPresent());
    }

    @Test
    public void testActionAwakeWhenSleeping() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        AwakeEvent awakeEvent = new AwakeEvent(pet, configuration, consumer);
        Optional<Notification> not;
        pet.setState(Pet.State.SLEEPING);
        pet.addEvent(Event.EventType.AWAKE_EVENT,0);
        not = awakeEvent.action(8); // should awake up after 8 hrs
        assertTrue(not.isPresent());
        assertEquals("waky, waky.......", not.get().getMessage());
        assertEquals(Pet.State.AWAKE, pet.getState());
    }
}
