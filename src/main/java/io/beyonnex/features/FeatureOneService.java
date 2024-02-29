package io.beyonnex.features;

import io.beyonnex.model.AnagramCheckingText;
import io.beyonnex.storage.AnagramRepository;

public class FeatureOneService {
    private final AnagramRepository anagramRepository;

    public FeatureOneService(AnagramRepository anagramRepository) {
        this.anagramRepository = anagramRepository;
    }
    public boolean execute(String firstTextString, String secondTextString) {
        var isAnagram = AnagramCheckingText.of(firstTextString).isAnagram(secondTextString);

        if (isAnagram) {
            anagramRepository.store(firstTextString);
            anagramRepository.store(secondTextString);
        }

        return isAnagram;
    }
}