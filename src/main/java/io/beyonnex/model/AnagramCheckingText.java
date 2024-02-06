package io.beyonnex.model;


import java.text.Normalizer;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public record AnagramCheckingText(String content) {
    public static AnagramCheckingText of(String content) {
        return new AnagramCheckingText(content);
    }

    public boolean isAnagram(String textToCompare) {
        var characterCount = characterCount(cleanText(content));
        var characterCountToCompare = characterCount(cleanText(textToCompare));

        return characterCount.equals(characterCountToCompare);
    }

    private Map<Character, Long> characterCount(String text) {
        return text.chars()
                .mapToObj(ch -> (char) ch)
                .collect(groupingBy(identity(), counting()));
    }

    private String cleanText(String text) {
        return removeAccents(text)
                .chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(letter -> String.valueOf((char) letter))
                .collect(joining())
                .toLowerCase();
    }

    private static String removeAccents(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }
}
