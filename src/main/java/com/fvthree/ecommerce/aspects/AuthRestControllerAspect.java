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
public class AuthRestControllerAspect {

    @Autowired
    private final Counter loginCounter;
    
    @Autowired
    private final Counter registerCounter;
    
    public AuthRestControllerAspect(Counter loginCounter, Counter registerCounter) {
		this.loginCounter = loginCounter;
		this.registerCounter = registerCounter;
	}

	@Before("execution(public * com.fvthree.ecommerce.auth.*Controller.*(..))")
    public void generalAllMethodASpect() {
        log.info("All Method Calls invoke this general aspect method");
    }

    @AfterReturning("execution(public * com.fvthree.ecommerce.auth.*Controller.authenticateUser(..))")
    public void getsCalledOnLogin() {
        log.info("This aspect is fired when the login method of the controller is called");
        loginCounter.increment();
        log.info("login count: " + loginCounter.count());
    }
    
    @AfterReturning("execution(public * com.fvthree.ecommerce.auth.*Controller.registerUser(..))")
    public void getsCalledOnRegister() {
        log.info("This aspect is fired when the register method of the controller is called");
        registerCounter.increment();
    }
}
