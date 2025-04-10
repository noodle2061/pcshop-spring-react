package com.id.akn.util;

public class NormalizeString {
    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        String normalized = input.trim().replaceAll("\\s+", " ").toLowerCase();
        return normalized.substring(0, 1).toUpperCase() + normalized.substring(1);
    }
}
