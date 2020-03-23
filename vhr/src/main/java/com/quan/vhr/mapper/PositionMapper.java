package com.quan.vhr.mapper;

import com.quan.vhr.bean.Position;
import com.quan.vhr.bean.PositionExample;
import java.util.List;

import com.quan.vhr.bean.RespBean;
import org.apache.ibatis.annotations.Param;

public interface PositionMapper {
    long countByExample(PositionExample example);

    int deleteByExample(PositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    List<Position> selectByExample(PositionExample example);

    Position selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Position record, @Param("example") PositionExample example);

    int updateByExample(@Param("record") Position record, @Param("example") PositionExample example);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> getAllPositions();

    int deletePositionByIds(@Param("ids") Integer[] ids);
}