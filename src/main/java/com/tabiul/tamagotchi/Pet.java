package com.tabiul.tamagotchi;

import com.tabiul.tamagotchi.event.Event;
import com.tabiul.tamagotchi.stat.Stat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author tabiul <tabiu@gmail.com>
 */
public class Pet {
    public enum State {
        AWAKE,
        SLEEPING

    }

    private final String name;
    private final String sex;
    private long age;
    private Map<Stat.StatType, Stat> statMap = new HashMap<>();
    private Map<Event.EventType, Long> eventTracker = new HashMap<>();
    private State state = State.AWAKE;

    public Pet(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public Pet(String name, String sex, long age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public String getSex() {

        return sex;
    }

    public Map<Stat.StatType, Stat> getStats() {

        return Collections.unmodifiableMap(statMap);
    }

    public Stat getStat(Stat.StatType statType) {

        return statMap.get(statType);
    }

    public void addStat(Stat.StatType statType, Stat stat) {

        statMap.put(statType, stat);
    }

    public void addEvent(Event.EventType eventType, long when) {
        eventTracker.put(eventType, when);
    }

    public void removeEvent(Event.EventType eventType) {
        eventTracker.remove(eventType);
    }

    public Optional<Long> whenEventHappen(Event.EventType eventType) {
        if (eventTracker.containsKey(eventType)) {
            return Optional.of(eventTracker.get(eventType));
        }
        return Optional.empty();
    }

    public Map<Event.EventType, Long> getEvents() {
        return Collections.unmodifiableMap(eventTracker);
    }

    public State getState() {

        return state;
    }

    public void setState(State state) {

        this.state = state;
    }

    public long getAge() {

        return age;
    }

    public void setAge(long age) {

        this.age = age;
    }
}
