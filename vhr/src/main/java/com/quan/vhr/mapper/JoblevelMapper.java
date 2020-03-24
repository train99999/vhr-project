package com.quan.vhr.mapper;

import com.quan.vhr.bean.Joblevel;
import com.quan.vhr.bean.JoblevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JoblevelMapper {
    long countByExample(JoblevelExample example);

    int deleteByExample(JoblevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Joblevel record);

    int insertSelective(Joblevel record);

    List<Joblevel> selectByExample(JoblevelExample example);

    Joblevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Joblevel record, @Param("example") JoblevelExample example);

    int updateByExample(@Param("record") Joblevel record, @Param("example") JoblevelExample example);

    int updateByPrimaryKeySelective(Joblevel record);

    int updateByPrimaryKey(Joblevel record);

    List<Joblevel> getAllJobLevels();
}