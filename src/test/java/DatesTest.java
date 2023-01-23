import static org.junit.jupiter.api.Assertions.*;

import com.example.lab23a.model.Dates;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
class DatesTest {

    @Test
    void testDateFormat() {
        LocalDateTime oldDate = Dates.fromDateFormat("2022-01-01 00:00:00");
        LocalDateTime currentDate = Dates.currentDate();
        String dateString = Dates.toDateFormat(currentDate);

        LocalDateTime fromString = Dates.fromDateFormat(dateString);

        assertTrue(Dates.equals(currentDate, fromString));
        assertTrue(Dates.compare(oldDate, currentDate) > 0);
    }
}
