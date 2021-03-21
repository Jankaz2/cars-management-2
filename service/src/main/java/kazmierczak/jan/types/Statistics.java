package kazmierczak.jan.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;

import java.math.BigDecimal;
import java.util.IntSummaryStatistics;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistics {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal avg;

    /**
     *
     * @param stats
     * @return statistics with BigDecimal values
     */
    public static Statistics fromBigDecimalSummaryStatistics(BigDecimalSummaryStatistics stats) {
        return Statistics
                .builder()
                .min(stats.getMin())
                .avg(stats.getAverage())
                .max(stats.getMax())
                .build();
    }

    /**
     *
     * @param stats
     * @return statistics with integer valeus converted to BigDecimal
     */
    public static Statistics fromIntSummaryStatistics(IntSummaryStatistics stats) {
        return Statistics
                .builder()
                .min(new BigDecimal(stats.getMin()))
                .avg(new BigDecimal(String.valueOf(stats.getAverage())))
                .max(new BigDecimal(stats.getMax()))
                .build();
    }
}
