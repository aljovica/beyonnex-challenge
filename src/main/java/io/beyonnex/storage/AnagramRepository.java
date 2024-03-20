package io.beyonnex.storage;

import io.beyonnex.model.AnagramCheckingText;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AnagramRepository {

    private final Map<String, Set<AnagramCheckingText>> anagramsMap = new ConcurrentHashMap<>();

    public List<Set<AnagramCheckingText>> getAnagrams() {
        return new ArrayList<>(anagramsMap.values());
    }

    public Set<AnagramCheckingText> findAnagrams(String content) {
        AnagramCheckingText text = AnagramCheckingText.of(content);
        Set<AnagramCheckingText> found = new HashSet<>(find(text));
        found.removeAll(Set.of(text));
        return found;
    }

    private Set<AnagramCheckingText> find(AnagramCheckingText text) {
        String sortedText = sortCharacters(text.content());
        return anagramsMap.getOrDefault(sortedText, Collections.emptySet());
    }

    public synchronized void store(String content) {
        var text = AnagramCheckingText.of(content);
        var sortedText = sortCharacters(text.content());
        var matchedAnagrams = find(text);

        if (matchedAnagrams.isEmpty()) {
            Set<AnagramCheckingText> newSet = new HashSet<>();
            newSet.add(text);
            anagramsMap.put(sortedText, newSet);
        } else {
            matchedAnagrams.add(text);
        }
    }

    private String sortCharacters(String text) {
        char[] chars = text.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}