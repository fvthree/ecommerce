package com.fvthree.ecommerce.product.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import exceptions.HTTP400Exception;
import exceptions.HTTP404Exception;
import exceptions.RestAPIExceptionInfo;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ProductAbstractController implements ApplicationEventPublisherAware {

	protected ApplicationEventPublisher eventPublisher;
	protected static final String DEFAULT_PAGE_SIZE = "20";
	protected static final String DEFAULT_PAGE_NUMBER= "0";
	
    @Autowired
    private Counter http400ExceptionCounter;

    @Autowired
    private Counter http404ExceptionCounter;
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HTTP400Exception.class)
    public @ResponseBody RestAPIExceptionInfo handleBadRequestException(HTTP400Exception ex,
    		WebRequest request, HttpServletResponse response) {
    	log.info("Received Bad Request Exception " + ex.getLocalizedMessage());
    	http400ExceptionCounter.increment();
    	return new RestAPIExceptionInfo(ex.getLocalizedMessage(), "The request did not have correct parameters.");
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HTTP404Exception.class)
    public @ResponseBody RestAPIExceptionInfo handleResourceNotFoundException(HTTP404Exception ex,
    		WebRequest request, HttpServletResponse response) {
    	log.info("Received Resource Not Found Exception" + ex.getLocalizedMessage());
    	http404ExceptionCounter.increment();
    	return new RestAPIExceptionInfo(ex.getLocalizedMessage(), "The requested resource was not found.");
    }
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
    	this.eventPublisher = eventPublisher;
    }
    
    public static <T> T checkResourceFound(final T resource) {
    	if (resource == null) {
    		throw new HTTP404Exception("Resource not found");
    	}
    	return resource;
    }
 }
