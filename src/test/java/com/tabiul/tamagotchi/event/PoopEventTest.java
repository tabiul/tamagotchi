/*
 * Copyright (c) 2016 by Advanced Micro Devices, Inc.
 *
 * This file is protected by Federal Copyright Law,
 * with all rights reserved. No part of this file may
 * be reproduced, stored in a retrieval system,
 * translated, transcribed, or transmitted, in any
 * form, or by any means manual, electric, electronic,
 * mechanical, electro-magnetic, chemical, optical, or
 * otherwise, without prior explicit written permission
 * from Advanced Micro Devices, Inc.
 */

package com.tabiul.tamagotchi.event;

import com.tabiul.tamagotchi.Configuration;
import com.tabiul.tamagotchi.Notification;
import com.tabiul.tamagotchi.Pet;
import com.tabiul.tamagotchi.stat.HealthStat;
import com.tabiul.tamagotchi.stat.Stat;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author tabiul <tabiul.mahmood@amd.com>
 */
public class PoopEventTest {

    @Test
    public void testNoPopEvent() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        PoopEvent poopEvent = new PoopEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = poopEvent.action(23);
        assertFalse(not.isPresent());

    }

    @Test
    public void tesPoopEvent() {
        Pet pet = new Pet("test", "male", 1);
        Configuration configuration = new Configuration();
        configuration.setTickPerSecond(3600); // 1 tick = 1 hr
        Consumer<Class<? extends Event>> consumer = (e) -> {
        }; // do nothing
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        PoopEvent poopEvent = new PoopEvent(pet, configuration, consumer);
        Optional<Notification> not;
        not = poopEvent.action(25);
        assertTrue(not.isPresent());
        assertEquals("I have done po po", not.get().getMessage());
    }
}
