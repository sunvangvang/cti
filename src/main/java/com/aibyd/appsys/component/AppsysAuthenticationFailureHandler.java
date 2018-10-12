package com.aibyd.appsys.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aibyd.appsys.bean.AppsysResponseBody;
import com.alibaba.fastjson.JSON;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 
 */

@Component
public class AppsysAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        AppsysResponseBody responseBody = new AppsysResponseBody();

        responseBody.setStatus("400");
        responseBody.setMsg("Login Failure!");

        response.getWriter().write(JSON.toJSONString(responseBody));
    }

}