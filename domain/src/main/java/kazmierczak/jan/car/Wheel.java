package kazmierczak.jan.car;

import kazmierczak.jan.types.TyreType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Wheel {
    String model;
    Integer size;
    TyreType type;
}
