package kazmierczak.jan;

import kazmierczak.jan.types.CarBodyColor;
import kazmierczak.jan.types.CarBodyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CarBody {
    CarBodyColor color;
    CarBodyType type;
    List<String> components;

}
