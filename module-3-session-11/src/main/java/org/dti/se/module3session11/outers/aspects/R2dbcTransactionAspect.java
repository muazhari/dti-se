package org.dti.se.module3session11.outers.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Component
public class R2dbcTransactionAspect {

    @Autowired
    private TransactionalOperator transactionalOperator;

    @Around("@annotation(org.dti.se.module3session11.outers.aspects.R2dbcTransactional) || @within(org.dti.se.module3session11.outers.aspects.R2dbcTransactional)")
    public Object unblocking(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object result = proceedingJoinPoint.proceed();
            if (result instanceof reactor.core.publisher.Mono) {
                return transactionalOperator.transactional((Mono<?>) result);
            } else if (result instanceof reactor.core.publisher.Flux) {
                return transactionalOperator.transactional((Flux<?>) result);
            } else {
                throw new IllegalArgumentException("Method annotated with @R2dbcTransactional must return a Mono or a Flux");
            }
        } catch (Throwable throwable) {
            return Mono.error(throwable);
        }
    }

}
