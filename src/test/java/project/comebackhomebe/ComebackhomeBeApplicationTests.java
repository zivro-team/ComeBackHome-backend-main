package project.comebackhomebe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"dev", "secret"})
class ComebackhomeBeApplicationTests {

    @Test
    void contextLoads() {
    }

}
