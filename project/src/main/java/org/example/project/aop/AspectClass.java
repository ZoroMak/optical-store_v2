package org.example.project.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.project.database.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AspectClass {

    public final EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(AspectClass.class);

    @Pointcut("within(org.example.project.aop.*Service)")
    public void checkAllLogs(){}

    @Pointcut("execution(public void org.example.project.aop.*Service.createOrder(..))")
    public void sendEmailFromService(){}

    @After("checkAllLogs()")
    public void infoFromCheckAllLogs(){
        log.info("Конец выполнения кода");
    }

    @After("sendEmailFromService()")
    public void reportSendingEmail(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 1 && args[0] instanceof Customer customer) {
            String emailContent = args[1].toString();

            emailService.sendEmail(customer.getMail(), emailContent);
        }
    }
}
