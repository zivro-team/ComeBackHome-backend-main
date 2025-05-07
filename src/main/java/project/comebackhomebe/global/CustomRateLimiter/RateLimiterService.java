package project.comebackhomebe.global.CustomRateLimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {
    private final Bucket bucket;

    public RateLimiterService() {
        Refill refill = Refill.intervally(2, Duration.ofSeconds(10));
        Bandwidth limit = Bandwidth.classic(3, refill);
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    public boolean tryConsume(int tokens) {
        return bucket.tryConsume(tokens);
    }
}

