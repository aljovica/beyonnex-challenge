package beyonnex.io.feature;

import io.beyonnex.config.DependencyConfig;
import io.beyonnex.features.FeatureOneService;
import io.beyonnex.features.FeatureTwoService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureTwoServiceTest {
    private final DependencyConfig dependencyConfig = new DependencyConfig();
    private final FeatureOneService featureOneService = dependencyConfig.getFeatureOneService();
    private final FeatureTwoService featureTwoService = dependencyConfig.getFeatureTwoService();

    @Test
    public void shouldFindAnagramsOfPreviouslyStoredTexts() {
        featureOneService.execute("abc", "bca");
        featureOneService.execute("abc", "xyz");
        featureOneService.execute("abc", "acb");
        featureOneService.execute("xyz", "zxy");

        assertThat(featureTwoService.execute("abc")).containsExactlyInAnyOrder("bca", "acb");
        assertThat(featureTwoService.execute("bca")).containsExactlyInAnyOrder("abc", "acb");
        assertThat(featureTwoService.execute("xyz")).containsExactly("zxy");
        assertThat(featureTwoService.execute("xxx")).isEmpty();
    }

    @Test
    public void shouldNotFindAnagramsOfPreviouslyStoredTexts() {
        featureOneService.execute("abc", "bca");
        featureOneService.execute("abc", "xyz");
        featureOneService.execute("abc", "acb");
        featureOneService.execute("xyz", "zxy");

        assertThat(featureTwoService.execute("xxx")).isEmpty();
    }

    @Test
    public void shouldFindAnagramOfTextPreviouslyNotKnown() {
        featureOneService.execute("abc", "bca");

        assertThat(featureTwoService.execute("abc")).containsExactly("bca");
        assertThat(featureTwoService.execute("acb")).containsExactlyInAnyOrder("abc", "bca");
    }
}
