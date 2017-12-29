package com.cbank.utils;

import lombok.val;

import java.util.Random;

public class RandomUtils {

    private static final Random random = new Random();

    public static String generateAccountNum(){
        val accountNum = "" + ((long) (random.nextDouble() * 1000_0000_0000_0000L));
        return accountNum + (long) Math.pow(10, 15 - accountNum.length());
    }

}
