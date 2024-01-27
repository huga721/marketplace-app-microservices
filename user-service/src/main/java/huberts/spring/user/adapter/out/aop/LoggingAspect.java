package huberts.spring.user.adapter.out.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class LoggingAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* huberts.spring.user.adapter.in.web.*.*(..))")
    private void pointcut() {}

//    @Around("pointcut()")
//    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String methodName = joinPoint.getSignature().getName();
//        String className = joinPoint.getTarget().getClass().toString();
//        Object[] array = joinPoint.getArgs();
//        LOGGER.info("Method invoked {} : {}() arguments: {}", className, methodName, objectMapper.writeValueAsString(array));
//        Object object = joinPoint.proceed();
//        LOGGER.info("{}: Method:{} Response: {}", className, methodName, objectMapper.writeValueAsString(object));
//        return object;
//    }
}
