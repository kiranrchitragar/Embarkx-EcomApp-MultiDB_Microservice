package com.ecommerce.order.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {
/*
While both filters and interceptors can intercept requests, they operate at different levels.

Filters are part of the Servlet API and intercept requests before they reach the Spring
DispatcherServlet, providing a lower-level interception point.

Interceptors, on the other hand, are part of the Spring MVC framework and intercept requests after
the DispatcherServlet but before the controller method is invoked, offering more fine-grained
control within the Spring MVC context.

 */
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // Called at the time of API Call for each API
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        logger.info("Active Profile Set is :: Filter :: {}", activeProfile);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        logger.info(
                "Logging Request  :: Filter :: {} : {}", req.getMethod(),
                req.getRequestURI());
        chain.doFilter(request, response);
        logger.info(
                "Logging Response :: Filter :: {}",
                res.getContentType());
    }

}
