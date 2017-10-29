package pl.krystianminta.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* pl.krystianminta.springdemo.service.*.*(..))")
    public void forServicePackage() {}

    @Pointcut("execution(* pl.krystianminta.springdemo.dao.*.*(..))")
    public void forDaoPackege() {}

    @Pointcut("execution(* pl.krystianminta.springdemo.controller.*.*(..))")
    public void forControllerPackage() {}

    @Pointcut("forControllerPackage() || forDaoPackege() || forServicePackage()")
    public void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().toShortString();

        logger.info("\n=====>> in @Before: calling method: " + method);

        Object[] args = joinPoint.getArgs();

        for(Object obj : args) {
            logger.info(obj.toString());
        }

    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "ret"
    )
    public void afterReturning(JoinPoint joinPoint, Object ret) {

        String method = joinPoint.getSignature().toShortString();

        logger.info("\n=====>> in @AfterReturning: calling method: " + method);

        logger.info("=====>> result: " + ret);

    }

}
