package com.tabiul.tamagotchi;

import com.tabiul.tamagotchi.event.CleanEvent;
import com.tabiul.tamagotchi.event.FeedEvent;
import com.tabiul.tamagotchi.event.PlayEvent;
import com.tabiul.tamagotchi.event.SleepEvent;
import com.tabiul.tamagotchi.stat.HappinessStat;
import com.tabiul.tamagotchi.stat.HealthStat;
import com.tabiul.tamagotchi.stat.Stat;
import com.tabiul.tamagotchi.util.Configuration;
import com.tabiul.tamagotchi.util.Notification;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class Tamagotchi implements Observer {
    private static final String HELP_KEYWORD = "h";
    private static final String STAT_KEYWORD = "st";
    private static final String TIME_KEYWORD = "t";
    private static final String FEED_KEYWORD = "f";
    private static final String PLAY_KEYWORD = "p";
    private static final String SLEEP_KEYWORD = "s";
    private static final String CLEAN_KEYWORD = "c";

    private String buildHorizontalLine(char c, int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(c);
        }
        builder.append('\n');
        return builder.toString();
    }

    private String buildVerticalLine(char c, int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(c);
            builder.append('\n');
        }
        return builder.toString();
    }

    private String buildTextField(char c, String key, int keySize, String value,
                                  int valSize) {
        StringBuilder builder = new StringBuilder();
        builder.append(c);
        builder.append(' ');
        builder.append(StringUtils.rightPad(key, keySize, ' '));
        builder.append(':');
        builder.append(' ');
        builder.append(StringUtils.rightPad(value, valSize, ' '));
        builder.append(c);
        builder.append('\n');
        return builder.toString();
    }


    public String getAsciiFormatted(Map<String, String> keyValue) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildHorizontalLine('*', 50));
        keyValue.forEach((k, v) -> builder.append(buildTextField('*', k, 20, v, 25)));
        builder.append(buildHorizontalLine('*', 50));
        return builder.toString();

    }

    public String getPetName() throws IOException {
        System.out.print("please enter the name of pet : ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (!reader.ready()) {
                return reader.readLine();
            }
        }
    }

    public String getPetSex() throws IOException {
        System.out.print("please enter the sex of pet : ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (!reader.ready()) {
                return reader.readLine();
            }
        }
    }

    public void parseCommand(String command, Pet pet, Universe universe) {
        switch (command.trim().toLowerCase()) {
            case HELP_KEYWORD:
                showHelp();
                break;
            case STAT_KEYWORD:
                Map<String, String> m = new LinkedHashMap<>();
                m.put("name", pet.getName());
                m.put("sex", pet.getSex());
                m.put("age", Long.toString(pet.getAge()));
                for (Stat s : pet.getStats().values()) {
                    m.put(s.name(), Long.toString(s.getStat()));
                }
                System.out.println(getAsciiFormatted(m));
                break;
            case TIME_KEYWORD:
                System.out.println("universe tick : " + universe.getTick());
                break;
            case FEED_KEYWORD:
                System.out.println("sending feed command");
                universe.addEvent(FeedEvent.class);
                break;
            case PLAY_KEYWORD:
                System.out.println("sending play command");
                universe.addEvent(PlayEvent.class);
                break;
            case SLEEP_KEYWORD:
                System.out.println("sending sleep command");
                universe.addEvent(SleepEvent.class);
                break;
            case CLEAN_KEYWORD:
                System.out.println("sending clean command");
                universe.addEvent(CleanEvent.class);
                break;
            default:
                System.out.println("invalid command " + command);
        }
    }

    public void showHelp() {
        System.out.println(HELP_KEYWORD + " : display help");
        System.out.println(TIME_KEYWORD + " : display time");
        System.out.println(STAT_KEYWORD + " : display pet statistics");
        System.out.println(SLEEP_KEYWORD + " : sleep command");
        System.out.println(CLEAN_KEYWORD + " : clean command");
        System.out.println(FEED_KEYWORD + " : feed command");
        System.out.println(PLAY_KEYWORD + " : play command");
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("message from tamagotchi world:");
        System.out.println(((Notification) arg).getMessage());
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Tamagotchi tamagotchi = new Tamagotchi();
        String name = tamagotchi.getPetName();
        String sex = tamagotchi.getPetSex();
        Pet pet = new Pet(name, sex, 1);
        pet.addStat(Stat.StatType.HEALTH, new HealthStat());
        pet.addStat(Stat.StatType.HAPPINESS, new HappinessStat());
        //by default set all the stat to 50
        for (Stat stat : pet.getStats().values()) {
            stat.updateStat(50);
        }
        Configuration configuration = Configuration.newInstance();
        System.out.println("initializing tamagotchi world.....");
        try (Universe universe = new Universe(pet, configuration)) {
            universe.addObserver(tamagotchi);
            universe.bigBang();
            System.out.println("tamagotchi world initialized successfully!!");
            tamagotchi.showHelp();
            while (!universe.isUniverseDead()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader
                    (System.in));
                if (reader.ready()) {
                    tamagotchi.parseCommand(reader.readLine(), pet, universe);
                }
            }
        }
    }
}
