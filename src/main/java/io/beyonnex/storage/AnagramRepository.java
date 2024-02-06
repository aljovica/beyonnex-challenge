package io.beyonnex.storage;

import io.beyonnex.model.AnagramCheckingText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.union;
import static java.util.stream.Collectors.toSet;

public class AnagramRepository {

    private final List<Set<AnagramCheckingText>> anagramsList = new ArrayList<>();

    public AnagramRepository() {}

    public List<Set<AnagramCheckingText>> getAnagramsList() {
        return anagramsList;
    }

    public Set<AnagramCheckingText> findAnagrams(String content) {
        AnagramCheckingText text = AnagramCheckingText.of(content);
        return difference(find(text), Set.of(text));
    }

    private Set<AnagramCheckingText> find(AnagramCheckingText text) {
        return anagramsList.stream()
                .filter(anagramSet -> anagramSet.stream().allMatch(anagram -> text.isAnagram(anagram.content())))
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    public void store(String content) {
        var text = AnagramCheckingText.of(content);
        var matchedAnagrams = find(text);

        if (matchedAnagrams.isEmpty()) {
            anagramsList.add(Set.of(text));
        } else {
            int matchedAnagramIndex = anagramsList.indexOf(matchedAnagrams);
            anagramsList.set(matchedAnagramIndex, union(matchedAnagrams, Set.of(text)));
        }
    }
}
