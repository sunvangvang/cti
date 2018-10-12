package com.aibyd.appsys.component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.aibyd.appsys.bean.AppsysResponseBody;
import com.alibaba.fastjson.JSON;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * 
 */

@Component
public class AppsysAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        AppsysResponseBody responseBody = new AppsysResponseBody();

        responseBody.setStatus("403");
        responseBody.setMsg("Need Authorities!");

        response.getWriter().write(JSON.toJSONString(responseBody));
    }

}