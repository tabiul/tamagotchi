package com.tabiul.tamagotchi;

import com.tabiul.tamagotchi.event.AgeEvent;
import com.tabiul.tamagotchi.event.AwakeEvent;
import com.tabiul.tamagotchi.event.Event;
import com.tabiul.tamagotchi.event.HungerEvent;
import com.tabiul.tamagotchi.event.PoopCheckEvent;
import com.tabiul.tamagotchi.event.SelfSleepEvent;
import com.tabiul.tamagotchi.stat.Stat;
import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class Universe extends Observable implements Closeable {
    private final Configuration configuration;
    private ExecutorService executorService;
    private final Pet pet;
    private ConcurrentLinkedQueue<Event> queue = new ConcurrentLinkedQueue<>();
    private volatile boolean isDead = false;
    private final Consumer<Class<? extends Event>> consumer = this::addEvent;
    private final AtomicLong tick = new AtomicLong(0);

    public Universe(Pet pet, Configuration configuration) {
        this.pet = pet;
        this.configuration = configuration;
    }

    private List<Event> createUniverseEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new HungerEvent(pet, configuration, consumer));
        events.add(new AgeEvent(pet, configuration, consumer));
        events.add(new AwakeEvent(pet, configuration, consumer));
        events.add(new PoopCheckEvent(pet, configuration, consumer));
        events.add(new SelfSleepEvent(pet, configuration, consumer));
        return events;
    }

    private void alertObserver(Notification notification) {
        setChanged();
        notifyObservers(notification);
    }

    /**
     * A pet can pass way for two reason
     * <ul>
     * <li>any of the stat value is < 0 </li>
     * <li>age of the pet > configured max age</li>
     * </ul>
     *
     * @return true or false if the pet has passed away
     */
    private boolean hasPetDied() {
        for (Stat stat : pet.getStats().values()) {
            if (stat.getStat() < 0) {
                alertObserver(new Notification(MessageFormat.format("pet died due to " +
                        "the stat " +
                        "{0} fell " +
                        "below 0",
                    stat.name())));
                return true;
            }
        }
        if (pet.getAge() > configuration.getMaxAge()) {
            alertObserver(new Notification("pet died due to exceeding the maximum " +
                "age of " + configuration.getMaxAge()));
            return true;
        }
        return false;
    }

    public void bigBang() {
        executorService = Executors.newSingleThreadExecutor();
        List<Event> universeEvents = createUniverseEvents();
        pet.addEvent(Event.EventType.AWAKE_EVENT, 0);
        executorService.execute(() -> {

            while (!hasPetDied()) {
                while (!queue.isEmpty()) {
                    Event event = queue.remove();
                    event.action(tick.get()).ifPresent(this::alertObserver);
                }
                for (Event event : universeEvents) {
                    event.action(tick.get()).ifPresent(this::alertObserver);
                }
                try {
                    Thread.sleep(configuration.getHowOftenGenerateTick());
                } catch (InterruptedException e) {
                    break;
                }
                tick.getAndIncrement();
            }
            isDead = true;
        });

    }

    public <T extends Event> void addEvent(Class<T> clazz) {
        try {
            Constructor<T> c = clazz.getConstructor(Pet.class, Configuration.class,
                Consumer.class);
            queue.add(c.newInstance(pet, configuration, consumer));
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());

        }

    }

    public boolean isUniverseDead() {

        return isDead;
    }

    public long getTick() {

        return tick.get();
    }

    @Override
    public void close() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}
