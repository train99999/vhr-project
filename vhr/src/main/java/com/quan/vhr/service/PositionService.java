package com.quan.vhr.service;

import com.quan.vhr.bean.Position;
import com.quan.vhr.bean.RespBean;
import com.quan.vhr.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PositionService {

    @Autowired
    PositionMapper positionMapper;
    public List<Position> getAllPositions() {
        return positionMapper.getAllPositions();
    }

    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreatedate(new Date());
        return positionMapper.insertSelective(position);
    }

    public int updatePositions(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }


    public int deletePositionById(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }
}
