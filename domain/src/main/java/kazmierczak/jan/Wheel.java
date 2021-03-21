package kazmierczak.jan;

import kazmierczak.jan.types.TyreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Wheel {
    String model;
    Integer size;
    TyreType type;
}
