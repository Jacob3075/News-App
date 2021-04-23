package com.jacob.newsapp.utilities;

import org.jetbrains.annotations.NotNull;

public class Utils {
    private Utils() {}

    @NotNull
    public static String capitalize(@NotNull final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
