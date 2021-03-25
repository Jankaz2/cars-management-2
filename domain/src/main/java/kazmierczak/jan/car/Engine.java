package kazmierczak.jan.car;

import kazmierczak.jan.types.EngineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Engine {
    EngineType type;
    BigDecimal power;
}
