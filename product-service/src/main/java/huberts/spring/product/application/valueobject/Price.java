package huberts.spring.product.application.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "value")
public class Price implements Serializable {

    @DecimalMin(value = "0.0", inclusive = false, message = "{price.minus.value}")
    @NotEmpty(message = "{price.value.empty}")
    @JsonValue
    private final BigDecimal value;

    private Price(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.UP);
    }

    @JsonCreator
    public static Price of(@NotEmpty(message = "{input.value.empty}") BigDecimal value) {
        return new Price(value);
    }
}