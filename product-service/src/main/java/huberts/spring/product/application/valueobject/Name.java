package huberts.spring.product.application.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "value")
public class Name implements Serializable {

    @NotEmpty(message = "{name.value.empty}")
    @JsonValue
    private final String value;

    private Name(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Name of(@NotEmpty(message = "{input.value.empty}") String value) {
        return new Name(value);
    }
}
