package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.util.Time;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public abstract class Event {
    public enum EventType {
        FEED_EVENT,
        AWAKE_EVENT,
        SLEEP_EVENT,
        PLAY_EVENT,
        POOP_EVENT,
        CLEAN_EVENT,
        AGE_EVENT
    }

    protected final Configuration configuration;
    protected final Consumer<Class<? extends Event>> generateEvent;
    protected final Pet pet;
    protected Time time;

    public Event(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        this.pet = pet;
        this.configuration = configuration;
        this.generateEvent = generateEvent;
        this.time = new Time(configuration);
    }

    public abstract Optional<Notification> action(long currTick);

}
