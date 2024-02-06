package io.beyonnex.features;


import io.beyonnex.model.AnagramCheckingText;
import io.beyonnex.storage.AnagramRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class FeatureTwoService {
    private final AnagramRepository anagramRepository;

    public FeatureTwoService(AnagramRepository anagramRepository) {
        this.anagramRepository = anagramRepository;
    }
    public Set<String> execute(String text) {
        return anagramRepository.findAnagrams(text).stream()
                .map(AnagramCheckingText::content)
                .collect(Collectors.toSet());
    }
}
