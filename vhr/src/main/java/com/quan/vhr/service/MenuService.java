package com.quan.vhr.service;

import com.quan.vhr.bean.Hr;
import com.quan.vhr.bean.Menu;
import com.quan.vhr.bean.MenuRole;
import com.quan.vhr.mapper.MenuMapper;
import com.quan.vhr.mapper.MenuRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuRoleMapper menuRoleMapper;
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

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        Integer result = menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
    }
}
