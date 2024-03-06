package huberts.spring.product.application.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
//@Embeddable
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "value")
public class Description implements Serializable {

    @NotEmpty(message = "{description.value.empty}")
    @JsonValue
    public final String value;

    private Description(String value) {
        validateDescription();
        this.value = value;
    }

    @JsonCreator
    public static Description of(@NotEmpty(message = "{input.value.empty}") String value) {
        return new Description(value);
    }

    private static void validateDescription() {
        // TODO: Implement validate login
    }
}
