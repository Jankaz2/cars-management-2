package kazmierczak.jan.car;

import kazmierczak.jan.types.EngineType;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Engine {
    EngineType type;
    BigDecimal power;
}
