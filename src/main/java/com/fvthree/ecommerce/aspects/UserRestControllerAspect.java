package com.fvthree.ecommerce.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserRestControllerAspect {

    @Autowired
    private Counter createdUserCreationCounter;
    
    @Autowired
    private Counter updateUserCreationCounter;
    
    @Before("execution(public * com.fvthree.ecommerce.user.controller.*Controller.*(..))")
    public void generalAllMethodASpect() {
        log.info("All Method Calls invoke this general aspect method");
    }

    @AfterReturning("execution(public * com.fvthree.ecommerce.user.controller.*Controller.createUser(..))")
    public void getsCalledOnUserSave() {
        log.info("This aspect is fired when the createUser method of the controller is called");
        createdUserCreationCounter.increment();
    }
    
    @AfterReturning("execution(public * com.fvthree.ecommerce.user.controller.*Controller.updateUser(..))")
    public void getsCalledOnUserUpdate() {
        log.info("This aspect is fired when the updateUser method of the controller is called");
        updateUserCreationCounter.increment();
    }
}
