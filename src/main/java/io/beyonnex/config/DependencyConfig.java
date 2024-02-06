package io.beyonnex.config;

import io.beyonnex.features.FeatureOneService;
import io.beyonnex.features.FeatureTwoService;
import io.beyonnex.storage.AnagramRepository;

public class DependencyConfig {
    private final AnagramRepository anagramRepository = new AnagramRepository();
    private final FeatureOneService featureOneService = new FeatureOneService(anagramRepository);
    private final FeatureTwoService featureTwoService = new FeatureTwoService(anagramRepository);

    public AnagramRepository getAnagramRepository() {
        return anagramRepository;
    }

    public FeatureOneService getFeatureOneService() {
        return featureOneService;
    }

    public FeatureTwoService getFeatureTwoService() {
        return featureTwoService;
    }
}
