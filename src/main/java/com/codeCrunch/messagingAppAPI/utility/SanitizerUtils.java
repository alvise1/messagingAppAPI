package com.codeCrunch.messagingAppAPI.utility;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class SanitizerUtils {

    /**
     * Sanitizes HTML content to plain text by removing all tags and other malicious content.
     */
    public static String sanitizeToPlainText(String rawContent) {
        if (rawContent == null) {
            return null;
        }
        // Jsoup.clean with Safelist.none() removes all HTML tags,
        // returning only the plain text content.
        return Jsoup.clean(rawContent, Safelist.none()).trim();
    }
}
