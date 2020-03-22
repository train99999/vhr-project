package com.quan.vhr.service;

import com.quan.vhr.bean.Hr;
import com.quan.vhr.bean.Menu;
import com.quan.vhr.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;
    public List<Menu> getMenusByHrId(){
        // 获取当前登录的用户对象SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        Hr hr = (Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuMapper.getMenusByHrId(hr.getId());
    }

    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }
}
