package beyonnex.io.feature;

import io.beyonnex.config.DependencyConfig;
import io.beyonnex.features.FeatureOneService;
import io.beyonnex.model.AnagramCheckingText;
import io.beyonnex.storage.AnagramRepository;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureOneServiceTest {
    private final DependencyConfig dependencyConfig = new DependencyConfig();
    private final AnagramRepository anagramRepository = dependencyConfig.getAnagramRepository();
    private final FeatureOneService featureOneService = dependencyConfig.getFeatureOneService();

    @Test
    public void shouldProveAnagramsAndStoreThem() {
        assertThat(featureOneService.execute("abc", "cba")).isTrue();
        assertThat(featureOneService.execute("xyz", "zxy")).isTrue();
        assertThat(featureOneService.execute("fgh", "gfh")).isTrue();

        assertThat(anagramRepository.getAnagrams()).containsExactlyInAnyOrder(
                Set.of(AnagramCheckingText.of("abc"), AnagramCheckingText.of("cba")),
                Set.of(AnagramCheckingText.of("xyz"), AnagramCheckingText.of("zxy")),
                Set.of(AnagramCheckingText.of("fgh"), AnagramCheckingText.of("gfh"))
        );
    }

    @Test
    public void shouldDisproveAnagramsAndNotStoreThem() {
        assertThat(featureOneService.execute("abc", "abb")).isFalse();
        assertThat(featureOneService.execute("xyz", "zyk")).isFalse();
        assertThat(featureOneService.execute("fgh", "ffh")).isFalse();

        assertThat(anagramRepository.getAnagrams()).isEmpty();
    }
}