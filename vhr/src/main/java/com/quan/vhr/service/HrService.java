package com.quan.vhr.service;

import com.quan.vhr.bean.Hr;
import com.quan.vhr.mapper.HrMapper;
import com.quan.vhr.mapper.HrRoleMapper;
import com.quan.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrRoleMapper hrRoleMapper;
    @Autowired
    HrMapper hrMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(s);
        if(hr == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 登录成功，设置角色
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {

        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    public int updateHr(Hr hr) {

        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        // 先根据hrid 把hr_role表的删除相关数据删除，在添加进去
        hrRoleMapper.deleteByHridKey(hrid);
        return hrRoleMapper.addRole(hrid,rids)==rids.length;
    }

    public int deleteHrById(Integer id) {

        return hrMapper.deleteByPrimaryKey(id);
    }
}
