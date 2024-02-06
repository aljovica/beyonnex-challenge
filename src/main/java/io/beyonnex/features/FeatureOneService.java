package io.beyonnex.features;

import io.beyonnex.model.AnagramCheckingText;
import io.beyonnex.storage.AnagramRepository;

public class FeatureOneService {
    private final AnagramRepository anagramRepository;

    public FeatureOneService(AnagramRepository anagramRepository) {
        this.anagramRepository = anagramRepository;
    }
    public boolean execute(String firstTextString, String secondTextString) {
        var firstText = AnagramCheckingText.of(firstTextString);
        var isAnagram = firstText.isAnagram(secondTextString);

        if (isAnagram) {
            anagramRepository.store(firstText.content());
            anagramRepository.store(secondTextString);
        }

        return isAnagram;
    }
}