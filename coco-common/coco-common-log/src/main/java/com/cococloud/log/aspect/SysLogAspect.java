package com.cococloud.log.aspect;

import com.cococloud.log.event.EventPublisher;
import com.cococloud.log.event.SysLogEvent;
import com.cococloud.log.util.LogTypeEnum;
import com.cococloud.log.util.SysLogUtils;
import com.cococloud.upms.common.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@AllArgsConstructor
public class SysLogAspect {

    private final EventPublisher publisher;

    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint pjp, com.cococloud.log.annotation.SysLog sysLog) {
        SysLog log = SysLogUtils.getSysLog();
        log.setTitle(sysLog.value());

        long startTime = System.currentTimeMillis();
        Object obj = null;

        try {
            obj = pjp.proceed();
        } catch (Exception e) {
            log.setType(LogTypeEnum.ERROR.getType());
            log.setException(e.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();
            log.setTime(endTime - startTime);
            publisher.publishEvent(new SysLogEvent(log));
        }

        return obj;
    }

}
