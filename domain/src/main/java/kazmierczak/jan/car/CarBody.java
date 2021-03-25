package kazmierczak.jan.car;

import kazmierczak.jan.types.CarBodyColor;
import kazmierczak.jan.types.CarBodyType;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class CarBody {
    CarBodyColor color;
    CarBodyType type;
    List<String> components;

}
