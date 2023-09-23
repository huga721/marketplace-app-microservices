package huberts.spring.basket.adapter.out.feign.product;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import huberts.spring.basket.common.exception.FeignAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@RequiredArgsConstructor
public class ProductClientConfiguration implements RequestInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductClientConfiguration.class);

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            template.header("Authorization", "Bearer " + jwt.getTokenValue());
            LOGGER.info("Added authorization header to Feign requestTemplate");
            LOGGER.info("{}", jwt);
        } else {
            String errorMessage = "Unable to add Authoriation header to Feign requestTemplate";
            LOGGER.error("An exception occurred!", new FeignAuthorizationException(errorMessage));
            throw new FeignAuthorizationException(errorMessage);
        }
    }
}
