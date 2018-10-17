package com.aibyd.appsys.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.aibyd.appsys.bean.AppsysResponseBody;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 
 */

@Component
public class AppsysAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        AppsysResponseBody responseBody = new AppsysResponseBody();

        responseBody.setStatus("403");
        responseBody.setMsg("Need Authorities!");

        response.getWriter().write(JSON.toJSONString(responseBody));
    }

}