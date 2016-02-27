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
public class AgeEventTest {

    @Test
    public void testAction() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(2592000); // 1 tick = 1 month
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        AgeEvent ageEvent = new AgeEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = ageEvent.action(0);
        assertTrue(!not.isPresent());
        assertEquals(1, pet.getAge());
        not = ageEvent.action(11);
        assertTrue(!not.isPresent());
        assertEquals(1, pet.getAge());
        not = ageEvent.action(12);
        assertEquals(2, pet.getAge());
        assertTrue(not.isPresent());
        assertEquals("happy birthday test, you are 2 years old today", not.get()
            .getMessage());
        not = ageEvent.action(13);
        assertTrue(!not.isPresent());
        assertEquals(2, pet.getAge());
        not = ageEvent.action(24);
        assertEquals(3, pet.getAge());
        assertTrue(not.isPresent());
        assertEquals("happy birthday test, you are 3 years old today", not.get()
            .getMessage());
    }
}
