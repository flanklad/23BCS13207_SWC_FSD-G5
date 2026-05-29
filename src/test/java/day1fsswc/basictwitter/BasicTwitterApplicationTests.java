package day1fsswc.basictwitter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BasicTwitterApplicationTests {

    @Test
    void applicationClassLoads() {
        assertDoesNotThrow(() -> Class.forName("day1fsswc.basictwitter.BasicTwitterApplication"));
    }
}
