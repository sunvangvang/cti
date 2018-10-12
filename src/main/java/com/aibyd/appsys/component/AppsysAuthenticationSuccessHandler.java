package com.aibyd.appsys.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aibyd.appsys.bean.AppsysResponseBody;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.aibyd.appsys.bean.AppsysUserDetails;
import com.aibyd.appsys.utils.JWTUtils;
import com.alibaba.fastjson.JSON;

/**
 * 
 */

@Component
public class AppsysAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AppsysResponseBody responseBody = new AppsysResponseBody();

        responseBody.setStatus("200");
        responseBody.setMsg("Login Success!");

        AppsysUserDetails userDetails = (AppsysUserDetails) authentication.getPrincipal();

        String jwtToken = JWTUtils.generateToken(userDetails.getUsername(), 300, "1qazse4@#W");
        responseBody.setJwtToken(jwtToken);

        response.getWriter().write(JSON.toJSONString(responseBody));
    } 

}