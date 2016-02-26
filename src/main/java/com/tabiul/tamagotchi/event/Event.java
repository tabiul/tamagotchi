package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.TimeUtils;

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
        CLEAN_EVENT
    }

    protected final Configuration configuration;
    protected final Consumer<Class<? extends Event>> generateEvent;
    protected final Pet pet;
    protected TimeUtils timeUtils;

    public Event(Pet pet, Configuration configuration, Consumer<Class<? extends Event>>
        generateEvent) {
        this.pet = pet;
        this.configuration = configuration;
        this.generateEvent = generateEvent;
        this.timeUtils = new TimeUtils(configuration);
    }

    public abstract Optional<Notification> action(long currTick);

}
