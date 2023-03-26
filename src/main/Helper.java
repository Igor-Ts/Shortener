package main;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helper {
    public static String generateRandomString() {                           //random string generator
        final SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public static void printMessage(final String message) {                 //console out
        System.out.println(message);
    }

}