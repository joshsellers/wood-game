package com.jahbz.wood.core;

public class Utility {

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min + 1)) + min;
    }
}
