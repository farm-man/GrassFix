package me.farmman.grassfix.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientParser {
    private static final Pattern HEX_PATTERN = Pattern.compile("(&#|#)([A-Fa-f0-9]{6})");
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("(&)([0-9a-fk-or])");

    public static String parseGradients(String text) {
        if (text == null) return null;

        Matcher hexMatcher = HEX_PATTERN.matcher(text);
        while (hexMatcher.find()) {
            String color = hexMatcher.group(2);
            text = text.replace(hexMatcher.group(), ChatColor.of("#" + color).toString());
        }

        Matcher codeMatcher = COLOR_CODE_PATTERN.matcher(text);
        while (codeMatcher.find()) {
            String code = codeMatcher.group(2);
            text = text.replace(codeMatcher.group(), ChatColor.getByChar(code.charAt(0)).toString());
        }

        return text;
    }
}