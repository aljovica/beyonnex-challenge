package beyonnex.io.model;

import io.beyonnex.model.AnagramCheckingText;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AnagramCheckingTextTest {
private static Stream<Arguments> anagramExamples() {
        return Stream.of(
                Arguments.of("Anagrams",  "Ars magna"),
                Arguments.of("New York Times",  "monkeys write"),
                Arguments.of("Church of Scientology", "rich-chosen goofy cult"),
                Arguments.of("McDonald's restaurants", "Uncle Sam's standard rot"),
                Arguments.of("coronavirus", "carnivorous"),
                Arguments.of("She Sells Sanctuary", "Santa; shy, less cruel"),
                Arguments.of("Eleven plus two", "Twelve plus one"),
                Arguments.of("Tom Marvolo Riddle", "I am Lord Voldemort"),
                Arguments.of("Juan Maria Solare", "Jura ser anomalía")
        );
    }

    private static Stream<Arguments> accentAnagramExamples() {
        return Stream.of(
                Arguments.of("übä", "bäü"),
                Arguments.of("übě", "bęü",
                Arguments.of("ôâì", "iãǒ")
        ));
    }

    private static Stream<Arguments> nonAnagramExamples() {
        return Stream.of(
                Arguments.of("New York Times",  "monkeys write it"),
                Arguments.of("Church of Scientology", "rich-chosen goofy cult is"),
                Arguments.of("McDonald's restaurants", "Uncle Sam's standard rot is not"),
                Arguments.of("a coronavirus", "carnivorous"),
                Arguments.of("She Sells Sanctuary", "Santa; shy, less bad")
        );
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldProveAnagrams(String firstText, String secondText) {
        assertThat(AnagramCheckingText.of(firstText).isAnagram(secondText)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("accentAnagramExamples")
    public void shouldProveAccentAnagrams(String firstText, String secondText) {
        assertThat(AnagramCheckingText.of(firstText).isAnagram(secondText)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonAnagramExamples")
    public void shouldDisproveAnagrams(AnagramCheckingText firstText, String secondText) {
        assertThat(firstText.isAnagram(secondText)).isFalse();
    }
}
