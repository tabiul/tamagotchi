package com.tabiul.tamagotchi;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author tabiul <tabiul@gmail.com>
 */
public class Main {
    private static String buildHorizontalLine(char c, int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(c);
        }
        builder.append('\n');
        return builder.toString();
    }

    private static String buildVerticalLine(char c, int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(c);
            builder.append('\n');
        }
        return builder.toString();
    }

    private static String buildTextField(char c, String key, int keySize, String value,
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


    public static String getAsciiFormatted(Map<String, String> keyValue) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildHorizontalLine('*', 20));
        keyValue.forEach((k, v) -> builder.append(buildTextField('*', k, 5, v, 10)));
        builder.append(buildHorizontalLine('*', 20));
        return builder.toString();

    }


}
