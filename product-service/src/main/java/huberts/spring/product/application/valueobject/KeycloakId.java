package huberts.spring.product.application.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import huberts.spring.product.application.exception.KeycloakParsingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "value")
public class KeycloakId implements Serializable {

    @JsonValue
    private final String value;

    private KeycloakId(String value) {
        validateKeycloakId(value);
        this.value = value;
    }

    @JsonCreator
    public static KeycloakId of(String value) {
        return new KeycloakId(value);
    }

    private static void validateKeycloakId(String value) {
        final Pattern pattern = Pattern.compile("[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new KeycloakParsingException("Input value of Keycloak id does not match correct pattern");
        }
    }
}
