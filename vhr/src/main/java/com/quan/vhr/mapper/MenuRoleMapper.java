package com.quan.vhr.mapper;

import com.quan.vhr.bean.MenuRole;
import com.quan.vhr.bean.MenuRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuRoleMapper {
    long countByExample(MenuRoleExample example);

    int deleteByExample(MenuRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    List<MenuRole> selectByExample(MenuRoleExample example);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MenuRole record, @Param("example") MenuRoleExample example);

    int updateByExample(@Param("record") MenuRole record, @Param("example") MenuRoleExample example);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);
}