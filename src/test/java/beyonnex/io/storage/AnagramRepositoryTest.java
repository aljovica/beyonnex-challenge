package beyonnex.io.storage;

import io.beyonnex.config.DependencyConfig;
import io.beyonnex.model.AnagramCheckingText;
import io.beyonnex.storage.AnagramRepository;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnagramRepositoryTest {
    private final AnagramRepository anagramRepository = new DependencyConfig().getAnagramRepository();

    private static final AnagramCheckingText ABC = AnagramCheckingText.of("abc");
    private static final AnagramCheckingText ACB = AnagramCheckingText.of("acb");
    private static final AnagramCheckingText CDA = AnagramCheckingText.of("cda");
    private static final AnagramCheckingText CBA = AnagramCheckingText.of("cba");
    private static final AnagramCheckingText CAB = AnagramCheckingText.of("cab");
    private static final AnagramCheckingText BCA = AnagramCheckingText.of("bca");
    private static final AnagramCheckingText XYZ = AnagramCheckingText.of("xyz");
    private static final AnagramCheckingText FGH = AnagramCheckingText.of("fgh");
    private static final AnagramCheckingText RST = AnagramCheckingText.of("rst");

    @Test
    public void shouldStoreTwoStrings() {
        anagramRepository.store(ABC.content());
        anagramRepository.store(CDA.content());

        assertThat(anagramRepository.getAnagrams()).containsExactly(
                Set.of(ABC),
                Set.of(CDA));
    }

    @Test
    public void shouldStoreMultipleSetsOfStrings() {
        anagramRepository.store(ABC.content());
        anagramRepository.store(CBA.content());
        anagramRepository.store(ABC.content());
        anagramRepository.store(XYZ.content());
        anagramRepository.store(FGH.content());
        anagramRepository.store(RST.content());
        anagramRepository.store(BCA.content());

        assertThat(anagramRepository.getAnagrams()).containsExactlyInAnyOrder(
                Set.of(RST),
                Set.of(ABC, CBA, BCA),
                Set.of(FGH),
                Set.of(XYZ));
    }

    @Test
    public void shouldFindAnagramsForInputString() {
        anagramRepository.store(ABC.content());
        anagramRepository.store(CBA.content());
        anagramRepository.store(CAB.content());
        anagramRepository.store(BCA.content());

        assertThat(anagramRepository.findAnagrams(ABC.content())).containsExactlyInAnyOrder(CBA, BCA, CAB);
        assertThat(anagramRepository.findAnagrams(ACB.content())).containsExactlyInAnyOrder(ABC, CBA, BCA, CAB);
    }
}
