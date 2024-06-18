package utilities;

import jakarta.ejb.Singleton;

import java.time.LocalDateTime;

@Singleton
public class DatesGenerator {

    public DatesGenerator() {
    }
    public LocalDateTime generateRandomDate() {
        return LocalDateTime.now().minusDays((long) (Math.random() * 30));
    }
}
