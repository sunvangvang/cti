package com.aibyd.appsys.component;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.aibyd.appsys.service.AppsysMenuService;
import com.aibyd.appsys.service.AppsysMenuRoleService;

/**
 * 
 */

@Component("appsysRbacAuthorityService")
public class AppsysRbacAuthorityService {

    @Autowired
    AppsysMenuService menuService;

    @Autowired
    AppsysMenuRoleService menuRoleService;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userDetails = authentication.getPrincipal();

        boolean hasPermission = false;

        String uri = request.getRequestURI();
        long menuId = menuService.findMenuIdByUri(uri);
        Set<Long> roleIdSet = menuRoleService.findRoleIdsByMenuId(menuId);

        if(userDetails instanceof UserDetails) {

            // 获取资源
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if ("ROLE_ANONYMOUS".equals(ga.getAuthority())) {
                    continue;
				}
                long roleId = Long.parseLong(ga.getAuthority());
                if(roleIdSet.contains(roleId)) {
                    hasPermission = true;
                }
            }
        }
        return hasPermission;
    }
}