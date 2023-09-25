package com.valerehealth.brightree.util;

import java.util.Random;

public class StringsUtil {

    private static Random random = new Random();
    private static char[] characters = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

    public static String randomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char character = characters[random.nextInt(characters.length)];
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

}
