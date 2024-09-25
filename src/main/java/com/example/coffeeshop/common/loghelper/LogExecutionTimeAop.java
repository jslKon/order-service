package com.example.coffeeshop.common.loghelper;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.time-execution.enabled:false}")
public class LogExecutionTimeAop {

    @Around("@annotation(logExecutionTime))")
    public Object executionTime(ProceedingJoinPoint point, LogExecutionTime logExecutionTime) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();

        logMessage(point, logExecutionTime, startTime, endTime);

        return object;
    }

    private void logMessage(ProceedingJoinPoint point,
                            LogExecutionTime logExecutionTime,
                            long startTime,
                            long endTime) {
        String messageLog =
                "Class Name: " + point.getSignature().getDeclaringType().getSimpleName()
                        + ". Method Name: " + point.getSignature().getName()
                        + ". Time taken for Execution is : " + (endTime - startTime) + "ms";

        String[] params = logExecutionTime.params();
        try {
            Object[] args = point.getArgs();
            ExpressionParser elParser = new SpelExpressionParser();

            Object[] paramArray = Arrays.stream(params).map(
                    strExpression -> {
                        Expression expression = elParser.parseExpression(strExpression);
                        return expression.getValue(args);
                    }
            ).toArray();

            String messageDetail = String.format(logExecutionTime.messages(), paramArray);

            log.info("Message log: {}. Details: {}", messageLog, messageDetail);
        } catch (Exception e) {
            log.error("Error getting annotation details : {}. Exception : {}", params,
                    e.getMessage());
            log.info("Message log: {}", messageLog);
        }
    }
}
