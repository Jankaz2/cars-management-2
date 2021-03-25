package kazmierczak.jan.car;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class HasPriceInRangeTest {
    @Test
    @DisplayName("when hasPriceInRange method works correct")
    public void test1() {
        var priceFrom = new BigDecimal(100);
        var priceTo = new BigDecimal(120);
        var car = Car.builder().price(new BigDecimal(110)).build();
        var result = car.hasPriceInRange(priceFrom, priceTo);
        assertThat(result).isTrue();
    }
}
