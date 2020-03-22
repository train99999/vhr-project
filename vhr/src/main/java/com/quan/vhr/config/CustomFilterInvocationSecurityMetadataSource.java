package com.quan.vhr.config;

import com.quan.vhr.bean.Menu;
import com.quan.vhr.bean.Role;
import com.quan.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

// 接口权限设置第一步，主要根据用户的请求地址，分析出请求需要的角色
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        // 获取到地址后，跟数据库的地址进行匹配，得到 mid ,在根据这个 mid 来查看需要的角色(rid)
        // 获取所有菜单数据
        List<Menu> allMenusWithRole = menuService.getAllMenusWithRole();
        for (Menu menu : allMenusWithRole){
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                // 匹配上，获取请求地址需要的角色
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i=0;i<roles.size();i++){
                    str[i] = roles.get(i).getName();
                }
                // 返回角色数组
                return SecurityConfig.createList(str);
            }
        }
        // 没有匹配上，统统需要登录后才可以访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
